package store.awesm.domain.vo;

/**
 *
 * @author zhishangli
 * @time 18:44 2020-01-19
 */
public class HttpResult<T> {
    private Integer code;
    private String msg;
    private T data;

    private HttpResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static HttpResult build(ResultCode var1) {
        return build(var1, null);
    }

    public static <T> HttpResult<T> build(ResultCode var1, T data) {
        return new HttpResult<>(var1.getCode(), var1.getMsg(), data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
