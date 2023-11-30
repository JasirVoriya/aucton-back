package cn.voriya.framework.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.util.Date;

/**
 * 雪花分布式id获取
 *
 */
public class SnowFlakeUtil {

    /**
     * 机器id
     */
    private static final long workerId = 0L;
    /**
     * 机房id
     */
    private static final long datacenterId = 0L;

    private static final Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);
    public static long getId() {
        return snowflake.nextId();
    }

    /**
     * 生成字符，带有前缀
     * @param prefix 前缀
     * @return 字符串
     */
    public static String createStr(String prefix) {
        return prefix + DateUtil.toString(new Date(), "yyyyMMdd") + SnowFlakeUtil.getId();
    }
    public static String getIdStr() {
        return snowflake.nextId() + "";
    }
}
