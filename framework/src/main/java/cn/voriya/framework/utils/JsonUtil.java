package cn.voriya.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串
     *
     * @param obj 对象
     * @return json字符串
     */
    @SneakyThrows
    public static String toJson(Object obj) {
        return MAPPER.writeValueAsString(obj);
    }

    /**
     * 将json字符串转换成对象
     *
     * @param json  json字符串
     * @param clazz 对象类型
     * @param <T>   对象类型
     * @return 对象
     */
    @SneakyThrows
    public static <T> T toObject(String json, Class<T> clazz) {
        return MAPPER.readValue(json, clazz);
    }
}
