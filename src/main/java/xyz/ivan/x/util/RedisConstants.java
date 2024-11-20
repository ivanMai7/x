package xyz.ivan.x.util;

public class RedisConstants {
    // 验证码保存在redis中的前缀
    public static final String USER_CODE = "code:";
    // 验证码过期时间
    public static final Long CODE_DURATION = 1800L;

    // 自动生成的用户昵称的前缀
    public static final String USER_NICKNAME_PREFIX = "user_";
    // token在redis中保存的前缀
    public static final String TOKEN_PREFIX = "login:";
    // token在redis中保存的过期时间
    public static final Long TOKEN_TTL = 31L;
}
