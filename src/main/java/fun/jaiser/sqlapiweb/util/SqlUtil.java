package fun.jaiser.sqlapiweb.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库工具
 *
 * @author Jaiser on 2022/6/1
 */
public class SqlUtil {

    private static final Logger log = LoggerFactory.getLogger(SqlUtil.class);



    /**
     * 生成可执行sql
     *
     * @param orgSql 配置对象
     * @param paramMap  动态参数
     * @return
     */
    public static String getMybatisExecuteSql(String orgSql, Map<String, Object> paramMap) {
        LanguageDriver languageDriver = new XMLLanguageDriver();
        Configuration configuration = new Configuration();
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, orgSql.trim(), Map.class);
        BoundSql boundSql = sqlSource.getBoundSql(paramMap);

        List<ParameterMapping> paramValues = boundSql.getParameterMappings();
        String sql = boundSql.getSql();
        while (sql.contains("?") && paramValues.size() > 0) {
            String paramName = paramValues.get(0).getProperty();
            Object paramValue = paramMap.get(paramName);
            String value = "";
            if (paramValue instanceof String) {
                value = "'" + paramValue + "'";
            } else if (paramValue instanceof List) {
                // 数组拼接，数字直接逗号分隔，字符要用单引号框起来再用逗号分隔
                StringBuffer listStr = new StringBuffer();
                for (int i = 0; i < ((List<?>) paramValue).size(); i++) {
                    Object data = ((List<?>) paramValue).get(i);
                    if (data instanceof String) {
                        listStr.append("'").append(data).append("'");
                    } else if (data instanceof Number) {
                        listStr.append(data);
                    }
                    if ((i + 1) != ((List<?>) paramValue).size()) {
                        listStr.append(",");
                    }
                }
                value = listStr.toString();
            }else {
                value = String.valueOf(paramValue);
            }
            sql = sql.replaceFirst("\\?", value);
            paramValues.remove(0);
        }
        log.info("查询配置sql入参为:《{}》,解析后执行sql为:《{}》", JSONObject.toJSONString(paramMap), sql);

        return sql;
    }


    /**
     * 是否含有sql注入，返回true表示含有
     *
     * @param obj
     * @return
     */
    public static boolean ckSqlInj(String obj) {
        Pattern pattern = Pattern.compile("\\b(and|exec|insert|select|drop|grant|alter|delete|update|count|chr|mid|master|truncate|char|declare|or|execute|create|union|merge)\\b|(\\*|;)");
        Matcher matcher = pattern.matcher(obj.toLowerCase());
        return matcher.find();
    }

    /**
     * 转义字符转换
     * */
    public static String changeEscapeChar(Object obj) {
        if (obj == null) {
            return null;
        }
        String ss = Objects.toString(obj);
        Pattern pattern = Pattern.compile("(\\\\|_|%)");
        Matcher matcher = pattern.matcher(ss);
        if (!matcher.find()) {
            return ss;
        }
        StringBuffer sb = new StringBuffer();
        for (char ch : ss.toCharArray()) {
            switch (ch) {
                case '\\':
                case '_':
                case '%':
                    sb.append("\\").append(ch);
                    break;
                default:
                    sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        String ss = "{\"中文 = %测试_%\\\"}";
//        System.out.println("结果2：" + ckSqlInj(ss));
//        System.out.println(changeEscapeChar(ss));

        String sql = "select * from user\n" +
                "<where>\n" +
                "\tid in\n" +
                "    <foreach collection=\"ids\" item=\"id\" open=\"(\" close=\")\" separator=\",\">\n" +
                "        #{id}\n" +
                "    </foreach>\n" +
                "    and tel_id in\n" +
                "    <foreach collection=\"idList\" item=\"id\" open=\"(\" close=\")\" separator=\",\">\n" +
                "        #{id}\n" +
                "    </foreach>\n" +
                "\t<if test=\"name != null and name != ''\">\n" +
                "\t\tand name = #{name}\n" +
                "\t</if>\n" +
                "</where>";
        List<Integer> ids = Arrays.asList(1,2,3,4);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jaiser");
        map.put("ids", ids);
        map.put("idList", ids);
        getMybatisExecuteSql(sql, map);
    }
}
