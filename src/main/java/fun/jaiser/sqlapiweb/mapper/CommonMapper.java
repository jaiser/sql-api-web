package fun.jaiser.sqlapiweb.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @DESCRIPTION: 数据接入层
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@Repository
public interface CommonMapper {

    /**
     * 查询操作
     * @param sql
     * @return
     */
    List<Map<String, Object>> query(@Param("sql")String sql);

    /**
     * 新增操作
     * @param sql
     * @return
     */
    int insert(@Param("sql")String sql);


    /**
     * 修改操作
     * @param sql
     * @return
     */
    int update(@Param("sql")String sql);


    /**
     * 删除操作
     * @param sql
     * @return
     */
    int delete(@Param("sql")String sql);

}