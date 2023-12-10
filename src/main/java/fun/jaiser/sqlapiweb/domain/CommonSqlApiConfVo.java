package fun.jaiser.sqlapiweb.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import fun.jaiser.sqlapiweb.enums.OperateTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.util.Date;

/**
 * @DESCRIPTION: sql配置api对象
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@ApiModel("sql配置api对象")
@Data
@TableName(value = "common_sql_api_conf_d")
public class CommonSqlApiConfVo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "code")
    private String code;

    @TableField(value = "operate_type")
    private String operateType = OperateTypeEnum.QUERY.getCode();

    @TableField(value = "value")
    private String value;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "data_source_id")
    private Integer dataSourceId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}