package cn.voriya.framework.entity.enums;

/**
 * 返回状态码
 */
public enum ResultCode {

    /**
     * 成功状态码
     */
    SUCCESS(200, "成功"),

    /**
     * 失败返回码
     */
    ERROR(400, "服务器繁忙，请稍后重试"),
    /**
     * 接口不存在
     */
    NOT_FOUND(404, "接口不存在"),
    /**
     * 系统异常
     */
    LIMIT_ERROR(1001, "访问过于频繁，请稍后再试"),
    /**
     * 参数异常
     */
    PARAMS_ERROR(4001, "参数异常"),
    UUID_NOT_FIND(4002, "设备码获取失败"),
    /**
     * 用户
     */
    USER_EDIT_SUCCESS(20001, "用户修改成功"),
    USER_NOT_EXIST(20002, "用户不存在"),
    USER_NOT_LOGIN(20003, "用户未登录"),
    USER_AUTH_EXPIRED(20004, "用户已退出，请重新登录"),
    USER_AUTHORITY_ERROR(20005, "权限不足"),
    USER_NAME_EXIST(20006, "该用户名已被注册"),
    USER_EMAIL_EXIST(20007, "该邮箱已被注册"),
    USER_EMAIL_NOT_EXIST(20008, "邮箱不存在"),
    USER_PASSWORD_ERROR(20009, "密码不正确"),
    USER_EDIT_ERROR(20010, "用户修改失败"),
    USER_OLD_PASSWORD_ERROR(20011, "旧密码不正确"),
    USER_COLLECTION_EXIST(20012, "无法重复收藏"),
    OTHER_PLACE_LOGIN(20013, "账号在他处登录"),

    /**
     * OSS
     */
    OSS_EXCEPTION_ERROR(30001, "文件上传失败，请稍后重试"),
    IMAGE_FILE_EXT_ERROR(30002, "不支持图片格式"),
    FILE_TYPE_NOT_SUPPORT(30003, "不支持上传的文件类型！"),

    /**
     * 验证码
     */
    VERIFICATION_EMAIL_CHECKED_ERROR(40001, "邮箱验证码错误，请重新校验"),
    EMAIL_SEND_ERROR(40002, "邮箱验证码发送失败，请稍后重试");


    private final Integer code;
    private final String message;


    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
