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
    USER_PASSWORD_NOT_SET(20014,"用户未设置密码，请用邮箱验证码登录" ),

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
    EMAIL_SEND_ERROR(40002, "邮箱验证码发送失败，请稍后重试"),

    /**
     * 分类
     */
    PARENT_CATEGORY_NOT_EXIST(50001, "父分类不存在"),
    CATEGORY_HAS_CHILDREN(50002, "该分类有子分类，不能删除"), CATEGORY_NAME_EXIST(50003, "该分类名已存在"),

    /**
     * 商品
     */
    GOODS_NOT_EXIST(60001, "商品不存在"),
    GOODS_IS_NOT_ON_SALE(60002, "拍卖已结束"),
    GOODS_TIME_ERROR(60003, "商品时间设置错误"),
    GOODS_START_TIME_ERROR(60004, "开始时间必须设置在一小时之后"),
    GOODS_IS_ON_SALE(60005,"拍卖还未结束" ),

    /**
     * 参拍信息
     */
    PARTICIPATE_BID_INFO_ALREADY_EXIST(70001, "您已经报名成功，请尽快支付"),
    PARTICIPATE_BID_INFO_ALREADY_PAY(70002, "您已经报名并支付成功，请勿重复报名"),
    PARTICIPATE_SUCCESS_BUT_PAY_FAIL(70003, "您已报名成功，但是支付失败，请尽快完成支付"),
    PARTICIPATE_BID_INFO_NOT_EXIST(70004, "拍卖信息不存在"),
    PARTICIPATE_IS_SELLER(70005, "不能参与自己发布的拍卖"),

    /**
     * 支付
     */
    BALANCE_NOT_ENOUGH(80001, "余额不足"),
    NOT_PAY_SELF(80002, "不能给自己转账"),
    PAY_ALREADY(80003,"您已支付，请勿重复支付" ),

    /**
     * 出价
     */
    BID_PRICE_TOO_LOW(90001, "出价不能低于目前最高价加最低出价"),
    NOT_BID_SUCCESS(90001,"该拍品的得主不是您，无法付款" );


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
