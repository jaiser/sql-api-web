package fun.jaiser.sqlapiweb.enums;

/**
 * @DESCRIPTION: 操作类型枚举类
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
public enum OperateTypeEnum {
    QUERY("QUERY", "查询"),
    INSERT("INSERT", "新增"),
    UPDATE("UPDATE", "修改"),
    DELETE("DELETE", "删除"),
    ;


    private String code;
    private String name;

    OperateTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}