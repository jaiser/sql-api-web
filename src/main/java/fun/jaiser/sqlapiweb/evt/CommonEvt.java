package fun.jaiser.sqlapiweb.evt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @DESCRIPTION: 通用操作入参对象
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@ApiModel("通用操作入参")
@Data
public class CommonEvt<T> implements Serializable {

    @ApiModelProperty("通用查询入参")
    private T param;

}
