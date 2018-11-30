package com.dudu.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.assertj.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author haien
 * @Description 响应报文类
 * @Date 2018/11/3
 **/
public class ServletUtil {

    //服务器标识
    private static String hostName = "";
    //响应的ContentType
    private static final String RESPONSE_CONTENTTYPE = "application/json";
    //响应编码
    private static final String RESPONSE_CHARACTERENCODING = "utf-8";
    //业务名称的缩写
    private static final String BIZ_NAME = "";

    private static final Logger logger = LoggerFactory.getLogger(ServletUtil.class);

    static {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostName = inetAddress.getHostName();
        } catch (UnknownHostException e) {
            logger.error("Get host name failed", e);
        }
    }

    /**
     * @return java.lang.String
     * @Author haien
     * @Description 生成成功报文
     * @Date 2018/11/3
     * @Param [httpCode, result, response]
     **/
    public static String createSuccessResponse(Integer httpCode, Object result,
                                               HttpServletResponse response) {
        return createSuccessResponse(httpCode, result, SerializerFeature.WriteMapNullValue, null, response);
    }

    /**
     * @return java.lang.String
     * @Author haien
     * @Description 生成成功报文
     * @Date 2018/11/3
     * @Param [httpCode, message, result, response]
     **/
    public static String createSuccessResponse(Integer httpCode, String message, Object result,
                                               HttpServletResponse response) {
        return createSuccessResponse(httpCode, message, result, SerializerFeature.WriteMapNullValue, null, response);
    }

    /**
     * @return java.lang.String
     * @Author haien
     * @Description 生成成功报文
     * @Date 2018/11/3
     * @Param [httpCode, result, filter, response]
     **/
    public static String createSuccessResponse(Integer httpCode, Object result,
                                               SerializeFilter filter, HttpServletResponse response) {
        return createSuccessResponse(httpCode, result, SerializerFeature.PrettyFormat, filter, response); //PrettyFormat:输出的时候每对键值对占一行，否则全部挤成一行
    }

    /**
     * @return java.lang.String
     * @Author haien
     * @Description 生成成功报文
     * @Date 2018/11/3
     * @Param [httpCode, result, serializerFeature, filter, response]
     **/
    public static String createSuccessResponse(Integer httpCode, Object result,
                                               SerializerFeature serializerFeature, SerializeFilter filter,
                                               HttpServletResponse response) {

        response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
        response.setContentType(RESPONSE_CONTENTTYPE);
        response.setStatus(httpCode);

        PrintWriter printWriter = null;
        String jsonString = "";
        try { //必须try/catch，不能抛出，因为要保证资源被关闭

            printWriter = response.getWriter();
            if (null != result) {
                if (null != filter) { //是否定制序列化
                    jsonString = JSONObject.toJSONString(result, filter, serializerFeature); //result是接收到的对象
                } else {
                    JSONObject result2= (JSONObject) JSONObject.toJSON(result);
                    System.out.println(result2);
                    jsonString = JSONObject.toJSONStringWithDateFormat(result,
                            "yyyy-MM-dd HH:ss:mm", serializerFeature); //不定制序列化则指定一下时间格式
                }
                System.out.println(result);
                printWriter.write(jsonString);
            }
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace(); //更妥善的处理：throw new IOException();
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }

        return jsonString;
    }

    /**
     * @return java.lang.String
     * @Author haien
     * @Description 生成成功报文
     * @Date 2018/11/3
     * @Param [httpCode, message, result, serializerFeature, filter, response]
     **/
    public static String createSuccessResponse(Integer httpCode, String message, Object result,
                                               SerializerFeature serializerFeature, SerializeFilter filter,
                                               HttpServletResponse response) {
        response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
        response.setContentType(RESPONSE_CONTENTTYPE);
        response.setStatus(httpCode);

        PrintWriter printWriter = null;
        String jsonString = "";
        try {
            printWriter = response.getWriter();
            SerializeConfig config = new SerializeConfig();
            config.put(Date.class, new SimpleDateFormat("yyyy-MM-dd"));
            Map<String, Object> map = new HashMap<String, Object>();
            if (null != result) {
                map.put("res_code", httpCode);
                map.put("message", message);
                map.put("data", result);
                if (null != filter) {
                    jsonString = JSONObject.toJSONString(map, filter, serializerFeature);
                } else {
                    jsonString = JSONObject.toJSONString(map, config, serializerFeature);
                    //相当于下面这句
                    //jsonString=JSONObject.toJSONStringWithDateFormat(map,"yyyy-MM-dd");
                }
                System.out.println(result);
                printWriter.println(jsonString);
            }
            printWriter.flush();
        } catch (IOException e) {
            logger.error("CreateResponse failed", e); //throw new IOException
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }
        return jsonString;
    }

    /**
     * @return java.lang.String
     * @Author haien
     * @Description 生成错误报文
     * @Date 2018/11/3
     * @Param [errorCode, response]
     **/
    public static String createErrorResponse(ErrorCode errorCode, HttpServletResponse response) {
        return createErrorResponse(errorCode.getHttpStatus(), errorCode.getRes_code(),
                errorCode.getCode(), errorCode.getMessage(), response);
    }

    /**
     * @return java.lang.String
     * @Author haien
     * @Description 生成错误报文
     * @Date 2018/11/3
     * @Param [httpStatus, code, message, response]
     **/
    public static String createErrorResponse(Integer httpStatus, Object code, String message,
                                             HttpServletResponse response) {

        response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
        response.setContentType(RESPONSE_CONTENTTYPE);
        response.setStatus(httpStatus);
        code = BIZ_NAME + code; //不过这个BIZ_NAME不知道怎么生成的

        PrintWriter printWriter = null;
        String jsonString = "";
        try {
            printWriter = response.getWriter();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", code);
            map.put("message", message);
            map.put("server_time", DateUtil.formatAsDatetime(new Date()));
            printWriter.write(jsonString);
            printWriter.flush();
        } catch (IOException e) {
            logger.error("CreateResponse failed", e);
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }
        return jsonString;
    }

    /**
     * @return java.lang.String
     * @Author haien
     * @Description 生成错误报文
     * @Date 2018/11/3
     * @Param [httpStatus, code, message, response]
     **/
    public static String createErrorResponse(Integer httpStatus, Integer res_code, Object code,
                                             String message, HttpServletResponse response) {
        response.setCharacterEncoding(RESPONSE_CHARACTERENCODING);
        response.setContentType(RESPONSE_CONTENTTYPE);
        response.setStatus(httpStatus);
        code = BIZ_NAME + code; //不过这个BIZ_NAME不知道怎么生成的

        PrintWriter printWriter = null;
        String jsonString = "";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message", message);
        map.put("res_code", res_code);
        map.put("server_time", DateUtil.formatAsDatetime(new Date()));
        try {
            printWriter = response.getWriter();
            jsonString = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
            printWriter.write(jsonString);
            printWriter.flush();
        } catch (Exception e) {
            logger.error("CreateResponse failed", e);
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }
        return jsonString;
    }
}


























