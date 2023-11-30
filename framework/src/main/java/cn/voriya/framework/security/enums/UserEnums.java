package cn.voriya.framework.security.enums;

/**
 * token角色类型
 *
 */
public enum UserEnums {
    /**
     * 角色
     */
    USER("用户"),
    MANAGER("管理员"),
    SYSTEM("系统");
    private final String role;

    UserEnums(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
