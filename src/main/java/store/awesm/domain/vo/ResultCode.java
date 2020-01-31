package store.awesm.domain.vo;

public enum ResultCode {
    SUCCESS(200, "SUCCESS"),
    FAILURE(444, "some wrong happened"),
    PLEASE_LOGIN(999, "please login"),
    LOGIN_FAIL(1000, "login fail"),
    LOGIN_MISS_USERNAME(1001, "miss username"),
    LOGIN_MISS_PASSWORD(1002, "miss password"),
    REGISTER_FAILURE(800, "register failed"),
    PLS_GET_VERIFY(298, "please pass the verification first"),
    PLS_GET_CAPTCHA(299, "please get captcha first"),
    SEND_CAPTCHA_FAILED(300, "send captcha failed");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ResultCode setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
