package fun.jaiser.sqlapiweb.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import fun.jaiser.sqlapiweb.enums.OperateTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @DESCRIPTION: sql配置api对象
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@ApiModel("sql配置api对象")
@Data
@TableName(value = "common_database_conf_d")
public class CommonDatabaseConfVo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "type")
    private String type = OperateTypeEnum.QUERY.getCode();

    @TableField(value = "value")
    private String value;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "database_id")
    private Integer databaseId;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

}