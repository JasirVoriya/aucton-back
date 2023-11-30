package cn.voriya.framework.utils;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * response 输出响应工具
 *
 */
@Slf4j
public class ResponseUtil {

    static final String ENCODING = "UTF-8";
    static final String CONTENT_TYPE = "application/json;charset=UTF-8";


    /**
     * response 输出JSON
     *
     * @param response
     * @param resultMap
     */
    public static void output(HttpServletResponse response, Map<String, Object> resultMap) {
        ServletOutputStream servletOutputStream = null;
        try {
            response.setCharacterEncoding(ENCODING);
            response.setContentType(CONTENT_TYPE);
            servletOutputStream = response.getOutputStream();
            servletOutputStream.write(JsonUtil.toJson(resultMap).getBytes());
        } catch (Exception e) {
            log.error("response output error:", e);
        } finally {
            if (servletOutputStream != null) {
                try {
                    servletOutputStream.flush();
                    servletOutputStream.close();
                } catch (IOException e) {
                    log.error("response output IO close error:", e);
                }
            }
        }
    }

    /**
     * 构造响应
     *
     * @param flag
     * @param code
     * @param msg
     * @return
     */
    public static Map<String, Object> resultMap(boolean flag, Integer code, String msg) {
        return resultMap(flag, code, msg, null);
    }

    /**
     * 构造响应
     *
     * @param flag
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Map<String, Object> resultMap(boolean flag, Integer code, String msg, Object data) {

        Map<String, Object> resultMap = new HashMap<String, Object>(16);
        resultMap.put("success", flag);
        resultMap.put("message", msg);
        resultMap.put("code", code);
        resultMap.put("timestamp", System.currentTimeMillis());
        if (data != null) {
            resultMap.put("result", data);
        }
        return resultMap;
    }
}
