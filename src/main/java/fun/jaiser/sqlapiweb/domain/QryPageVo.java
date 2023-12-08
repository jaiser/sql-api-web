package fun.jaiser.sqlapiweb.domain;

import fun.jaiser.sqlapiweb.evt.QryPageEvt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @DESCRIPTION: 分页查询对象
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@Data
@ApiModel("分页查询对象")
public class QryPageVo<T> implements Serializable {

    @ApiModelProperty("是否统计总数，默认为false")
    private Boolean countTotal = false;
    @ApiModelProperty("当前页，默认为1")
    private Integer pageNum = 1;
    @ApiModelProperty("页数，默认为10")
    private Integer pageSize = 10;

    private Long total;
    private List<T> infoList;

    protected QryPageVo() {
    }

    public static <T> QryPageVo<T> getInstance(QryPageEvt queryPageEvt, List<T> infoList, Long total) {
        QryPageVo<T> queryPageVm = new QryPageVo<T>();
        queryPageVm.setPageNum(queryPageEvt.getPageNum());
        queryPageVm.setPageSize(queryPageEvt.getPageSize());
        queryPageVm.setInfoList(infoList);
        queryPageVm.setTotal(total);
        return queryPageVm;
    }
}