package com.wemarket.cc.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public enum BaseResponseStatus {

    SUCCESS("0", "请求成功"),
    INTERNAL_SERVER_ERROR("999999", "服务内部错误"),
    FRONT_INTERNAL_SERVER_ERROR("999998", "系统异常，请您稍后再试"),
    REQUEST_UNSAFE("999997", "系统请求不安全");

    // 优化加速
    private static final Map<String, BaseResponseStatus> CACHE_MAP =
            new ConcurrentHashMap<String, BaseResponseStatus>();
    private static final Logger LOG = LoggerFactory.getLogger(BaseResponseStatus.class);
    private String code;
    private String message;

    private final static int MAX_CACHE_NUM = 500;
    private final static AtomicInteger cacheCount = new AtomicInteger(0);

    BaseResponseStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取响应状态
     * @param code 状态码
     * @return 响应状态
     */
    public static BaseResponseStatus getByCode(String code) {
        BaseResponseStatus wopStatus = CACHE_MAP.get(code);
        if (wopStatus != null) {
            return wopStatus;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("cache map isn't cache code:" + code);
        }

        for (BaseResponseStatus w : values()) {
            if (w.getCode().equals(code)) {
                if (cacheCount.addAndGet(1) <= MAX_CACHE_NUM) {
                    CACHE_MAP.put(code, w);
                }
                return w;
            }
        }

        throw new IllegalArgumentException("code is error:" + code);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
