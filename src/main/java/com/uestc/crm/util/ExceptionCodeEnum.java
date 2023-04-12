package com.uestc.crm.util;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * @author zhangqingyang
 * @create 2023-02-2023/2/14 19:58
 */

@Getter
public enum ExceptionCodeEnum {

    /**
     * 通用结果
     */
    ERROR(-1, "网络错误"),
    SUCCESS(20000, "成功"),

    /**
     * 参数校验
     */
    EMPTY_PARAM(10001, "参数为空");

    private final Integer code;
    private final String desc;

    ExceptionCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<Integer, ExceptionCodeEnum> ENUM_CACHE = new HashMap<>();

    static {
        for (ExceptionCodeEnum exceptionCodeEnum : ExceptionCodeEnum.values()) {
            ENUM_CACHE.put(exceptionCodeEnum.code, exceptionCodeEnum);
        }
    }

    public static String getDesc(Integer code) {
        return Optional.ofNullable(ENUM_CACHE.get(code))
                .map(ExceptionCodeEnum::getDesc)
                .orElseThrow(() -> new IllegalArgumentException("invalid exception code!"));
    }

}