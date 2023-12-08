package fun.jaiser.sqlapiweb.domain;

import fun.jaiser.sqlapiweb.enums.RespStateEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @DESCRIPTION: 自定义响应对象
 * @AUTHOR 许家军
 * @DATE: 2023/12/7
 */
@Data
public class RespResult<T> implements Serializable {

    private String msg;

    private Integer state;

    private T data;

    public RespResult() {
        this.state = RespStateEnum.SUCCESS.getCode();
        this.msg = RespStateEnum.SUCCESS.getName();
    }

    public RespResult(String msg, Integer state, T data) {
        this.msg = msg;
        this.state = state;
        this.data = data;
    }

    public static RespResult getInstance() {
        return new RespResult();
    }

    public static <T> RespResult<T> getInstance(Class<T> responseType) {
        return new RespResult<>();
    }

    public RespResult<T> success() {
        this.state = RespStateEnum.SUCCESS.getCode();
        this.msg = RespStateEnum.SUCCESS.getName();
        this.data = null;
        return this;
    }
    public RespResult<T> success(String msg) {
        this.state = RespStateEnum.SUCCESS.getCode();
        this.msg = msg;
        this.data = null;
        return this;
    }

    public RespResult<T> success(T data) {
        this.state = RespStateEnum.SUCCESS.getCode();
        this.msg = RespStateEnum.SUCCESS.getName();
        this.data = data;
        return this;
    }

    public RespResult<T> success(T data, String msg) {
        this.state = RespStateEnum.SUCCESS.getCode();
        this.msg = msg;
        this.data = data;
        return this;
    }

    public RespResult<T> error() {
        this.state = RespStateEnum.FAILT.getCode();
        this.msg = RespStateEnum.FAILT.getName();
        this.data = null;
        return this;
    }
    public RespResult<T> error(String msg) {
        this.state = RespStateEnum.FAILT.getCode();
        this.msg = msg;
        this.data = null;
        return this;
    }
}