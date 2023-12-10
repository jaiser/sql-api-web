package fun.jaiser.sqlapiweb.enums;

/**
 * @DESCRIPTION: 数据库类型枚举类
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
public enum DatabaseTypeEnum {

    PG("PG", "org.postgresql.Driver"),
    MYSQL("MYSQL", "com.mysql.jdbc.Driver"),
    ORACLE("ORACLE", "oracle.jdbc.driver.OracleDriver")
    ;

    private String code;

    private String driveName;

    DatabaseTypeEnum(String code, String driveName) {
        this.code = code;
        this.driveName = driveName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDriveName() {
        return driveName;
    }

    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }
}