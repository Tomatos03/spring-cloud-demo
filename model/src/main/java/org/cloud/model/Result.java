package org.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/26
 */
@Getter
@Setter
@AllArgsConstructor
public class Result <T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "请求成功", data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(400, message, null);
    }
}
