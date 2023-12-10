package fun.jaiser.sqlapiweb.util;

import fun.jaiser.sqlapiweb.domain.CommonDataSourceConfVo;
import fun.jaiser.sqlapiweb.domain.QryPageVo;
import fun.jaiser.sqlapiweb.enums.DatabaseTypeEnum;
import fun.jaiser.sqlapiweb.evt.CommonEvt;
import fun.jaiser.sqlapiweb.evt.QryPageEvt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * 数据库通用类
 *
 * @author Jaiser on 2022/5/15
 */
public class DataSourceUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    private Connection connection = null;

    private QryPageEvt qryPageEvt;

    private CommonEvt commonEvt;

    private CommonDataSourceConfVo vm;

    public static DataSourceUtil INSTANCE(CommonDataSourceConfVo vm, QryPageEvt evt){
        return new DataSourceUtil(vm, evt);
    }

    public static DataSourceUtil INSTANCE(CommonDataSourceConfVo vm, CommonEvt evt){
        return new DataSourceUtil(vm, evt);
    }

    public DataSourceUtil(CommonDataSourceConfVo vm, QryPageEvt evt){
        if (vm == null) {
            throw new RuntimeException("数据源配置为空");
        }
        this.qryPageEvt = evt;
        this.vm = vm;
        connect();
    }

    public DataSourceUtil(CommonDataSourceConfVo vm, CommonEvt evt) {
        if (vm == null) {
            throw new RuntimeException("数据源配置为空");
        }
        this.commonEvt = evt;
        this.vm = vm;
        connect();
    }

    /**
     * 连接数据库
     *
     * @throws Exception
     */
    public void connect() {
        try {
            connection = DriverManager.getConnection(vm.getUrl(), vm.getUsername(), vm.getPassword());
        } catch (SQLException e) {
            log.error("获取数据库连接异常======>\n数据源id:《{}》\nurl:《{}》\nusername:《{}》\npassword:《{}》", vm.getId(), vm.getUrl(), vm.getUsername(), vm.getPassword(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取业务数据列表
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> listData(String sql) throws Exception {
        PreparedStatement statement = connection.prepareStatement(getPageSql(sql),
            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return toListMap(statement);
    }

    /**
     * 操作数据
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public int optionData(String sql) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            return statement.executeUpdate();
        } catch (Exception e) {
            log.error("操作数据异常", e);
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("关闭数据库连接异常", e);
                }
            }
        }
    }

    /**
     * 获取总数
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public long listCount(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(getCountSql(sql));
        ResultSet rs = statement.executeQuery();
        long total = -1;
        if (rs == null) {
            return total;
        }
        if (rs.next()) {
            total = rs.getLong(1);
        }
        rs.close();
        statement.close();
        return total;
    }

    /**
     * jdbc查询
     *
     * @param sql
     * @return
     */
    public QryPageVo listInfo(String sql) {
        try {
            long total = -1;
            List<Map<String, Object>> list = listData(sql);
            if (qryPageEvt.isCountTotal() && qryPageEvt.getPageSize() > 0 && qryPageEvt.getPageNum() > 0) {
                return QryPageVo.getInstance(qryPageEvt, list, listCount(sql));
            } else {
                return QryPageVo.getInstance(qryPageEvt, list, -1L);
            }
        } catch (Exception e) {
            log.error("通过jdbc获取数据异常", e);
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error("关闭数据库连接异常", e);
                }
            }
        }
    }

    /**
     * 结果集转List<Map></Map>
     *
     * @param statement
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> toListMap(PreparedStatement statement) throws SQLException {
        List<Map<String, Object>> list = new ArrayList();
        ResultSet ret = statement.executeQuery();
        if (ret == null) {
            return list;
        }
        ResultSetMetaData meta = ret.getMetaData();
        int cot = meta.getColumnCount();

        while (ret.next()) {
            Map<String, Object> map = new HashMap();
            for (int i = 0; i < cot; i++) {
                map.put(meta.getColumnName(i + 1), ret.getObject(i + 1));
            }
            list.add(map);
        }
        ret.close();
        statement.close();
        return list;
    }

    /**
     * 获取分页sql
     *
     * @param sql
     * @return
     */
    private String getPageSql(String sql) {
        if (!qryPageEvt.isCountTotal() || qryPageEvt.getPageNum() < 0 || qryPageEvt.getPageSize() < 0) {
            return sql;
        }
        StringBuilder sb = new StringBuilder();
        if (Objects.equals(vm.getType(), DatabaseTypeEnum.MYSQL.getCode())) {
            sb.append("select * from (");
            sb.append(sql);
            sb.append(" ) ss limit ").append((qryPageEvt.getPageNum() - 1) * qryPageEvt.getPageSize()).append(",").append(qryPageEvt.getPageSize());
        } else if (Objects.equals(vm.getType(), DatabaseTypeEnum.PG.getCode())) {
            sb.append("select * from (");
            sb.append(sql);
            sb.append(" ) ss limit ").append(qryPageEvt.getPageSize()).append(" offset ").append((qryPageEvt.getPageNum() - 1) * qryPageEvt.getPageSize());
        } else if (Objects.equals(vm.getType(), DatabaseTypeEnum.ORACLE.getCode())) {
            sb.append("select * from (select a.*,rownum rn from (");
            sb.append(sql);
            sb.append(") a where rownum <= ").append((qryPageEvt.getPageNum() - 1) * qryPageEvt.getPageSize() + qryPageEvt.getPageSize());
            sb.append(") where rn > ").append((qryPageEvt.getPageNum() - 1) * qryPageEvt.getPageSize());
        }
        log.info("生成的分页sql为《{}》", sb.toString());
        return sb.toString();
    }

    /**
     * 获取总数sql
     *
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(1) from (");
        sb.append(sql);
        sb.append(") a");
        return sb.toString();
    }

}
