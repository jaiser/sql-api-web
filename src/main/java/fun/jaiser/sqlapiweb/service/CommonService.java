package fun.jaiser.sqlapiweb.service;

import fun.jaiser.sqlapiweb.domain.QryPageVo;
import fun.jaiser.sqlapiweb.evt.CommonEvt;
import fun.jaiser.sqlapiweb.evt.QryPageEvt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @DESCRIPTION:
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
public interface CommonService {


    /**
     * 查询
     * @param code
     * @param qryPageEvt
     * @return
     */
    QryPageVo query(String code, QryPageEvt qryPageEvt);

    /**
     * 新增
     * @param code
     * @param evt
     * @return
     */
    boolean insert(String code, CommonEvt<List<Map<String, Object>>> evt);

    /**
     * 修改
     * @param code
     * @param evt
     * @return
     */
    boolean update(String code, CommonEvt<Map<String, Object>> evt);

    /**
     * 删除
     * @param code
     * @param evt
     * @return
     */
    boolean delete(String code, CommonEvt<Map<String, Object>> evt);

    /**
     * 更新数据库信息
     */
    boolean updateDatabase();

}