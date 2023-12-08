package fun.jaiser.sqlapiweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.jaiser.sqlapiweb.domain.CommonSqlApiConfVo;
import org.springframework.stereotype.Repository;

/**
 * @DESCRIPTION: 数据接入层
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@Repository
public interface CommonSqlApiConfMapper extends BaseMapper<CommonSqlApiConfVo> {
}