package fun.jaiser.sqlapiweb.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @DESCRIPTION: sql配置api对象
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@ApiModel("sql配置api对象")
@Data
@TableName(value = "common_data_source_conf_d")
public class CommonDataSourceConfVo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "type")
    private String type;

    @TableField(value = "url")
    private String url;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

}