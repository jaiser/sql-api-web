package fun.jaiser.sqlapiweb.enums;

/**
 * @DESCRIPTION: 响应状态枚举类
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
public enum RespStateEnum {
    SUCCESS(200, "成功"),
    FAILT(500, "失败"),
    EXEC(501, "异常"),
    ;


    private Integer code;

    private String name;

    RespStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}