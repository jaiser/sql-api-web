package fun.jaiser.sqlapiweb.evt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @DESCRIPTION: 分页查询对象
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@Data
@ApiModel("分页查询对象")
public class QryPageEvt implements Serializable {
    @ApiModelProperty("是否统计总数，默认为false")
    private boolean countTotal = false;
    @ApiModelProperty("当前页，默认为1")
    private Integer pageNum = 1;
    @ApiModelProperty("页数，默认为10")
    private Integer pageSize = 10;

    @ApiModelProperty("通用入参")
    private Map<String, Object> param;

    public boolean isCountTotal() {
        return countTotal;
    }
}