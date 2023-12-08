package fun.jaiser.sqlapiweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import fun.jaiser.sqlapiweb.domain.CommonDatabaseConfVo;
import fun.jaiser.sqlapiweb.domain.CommonSqlApiConfVo;
import fun.jaiser.sqlapiweb.domain.QryPageVo;
import fun.jaiser.sqlapiweb.enums.OperateTypeEnum;
import fun.jaiser.sqlapiweb.evt.CommonEvt;
import fun.jaiser.sqlapiweb.evt.QryPageEvt;
import fun.jaiser.sqlapiweb.mapper.CommonDatabaseConfMapper;
import fun.jaiser.sqlapiweb.mapper.CommonMapper;
import fun.jaiser.sqlapiweb.mapper.CommonSqlApiConfMapper;
import fun.jaiser.sqlapiweb.service.CommonService;
import fun.jaiser.sqlapiweb.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @DESCRIPTION:
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonDatabaseConfMapper commonDatabaseConfMapper;

    @Autowired
    private CommonSqlApiConfMapper commonSqlApiConfMapper;

    @Autowired
    private CommonMapper commonMapper;

    private static List<CommonDatabaseConfVo> DATABASE_CONF_VO_LIST = new ArrayList<>();

    @PostConstruct
    public void init() {
        // 初始化
        this.updateDatabase();
    }

    @Override
    public QryPageVo query(String code, QryPageEvt qryPageEvt) {

        CommonSqlApiConfVo vo = this.getConfVo(code, OperateTypeEnum.QUERY);
        String sql = SqlUtil.getMybatisExecuteSql(vo.getValue(), qryPageEvt.getParam());

        List<Map<String, Object>> list = null;
        if (qryPageEvt.isCountTotal() && qryPageEvt.getPageSize() > 0 && qryPageEvt.getPageNum() > 0) {
            Page page = PageHelper.startPage(qryPageEvt.getPageNum(), qryPageEvt.getPageSize(), qryPageEvt.isCountTotal());
            list = commonMapper.query(sql);
            return QryPageVo.getInstance(qryPageEvt, list, page.getTotal());
        } else {
            list = commonMapper.query(sql);
            return QryPageVo.getInstance(qryPageEvt, list, -1L);
        }
    }

    @Override
    public boolean insert(String code, CommonEvt<List<Map<String, Object>>> evt) {
        CommonSqlApiConfVo vo = this.getConfVo(code, OperateTypeEnum.INSERT);
        try {
            List<Map<String, Object>> mapList = evt.getParam();
            for (Map<String, Object> map :
                    mapList) {
                String sql = SqlUtil.getMybatisExecuteSql(vo.getValue(), map);
                commonMapper.insert(sql);
            }
            return true;
        }catch (Exception e) {
            log.error("插入信息异常", e);
            return false;
        }
    }

    @Override
    public boolean update(String code, CommonEvt<Map<String, Object>> evt) {
        CommonSqlApiConfVo vo = this.getConfVo(code, OperateTypeEnum.UPDATE);
        try {
            String sql = SqlUtil.getMybatisExecuteSql(vo.getValue(), evt.getParam());
            commonMapper.update(sql);
            return true;
        }catch (Exception e) {
            log.error("修改信息异常", e);
            return false;
        }
    }

    @Override
    public boolean delete(String code, CommonEvt<Map<String, Object>> evt) {
        CommonSqlApiConfVo vo = this.getConfVo(code, OperateTypeEnum.DELETE);
        try {
            String sql = SqlUtil.getMybatisExecuteSql(vo.getValue(), evt.getParam());
            commonMapper.delete(sql);
            return true;
        }catch (Exception e) {
            log.error("删除信息异常", e);
            return false;
        }
    }

    @Override
    public boolean updateDatabase() {
        try {
            DATABASE_CONF_VO_LIST = commonDatabaseConfMapper.selectList(null);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private CommonSqlApiConfVo getConfVo(String code, OperateTypeEnum typeEnum) {
        LambdaQueryWrapper<CommonSqlApiConfVo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonSqlApiConfVo::getCode, code);
        lambdaQueryWrapper.eq(CommonSqlApiConfVo::getOperateType, typeEnum.getCode());
        CommonSqlApiConfVo vo = commonSqlApiConfMapper.selectOne(lambdaQueryWrapper);
        if (vo == null) {
            throw new RuntimeException("未查询到配置信息，请确认是否存在对应配置！");
        }
        return vo;
    }
}