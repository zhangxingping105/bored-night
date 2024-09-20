package com.zxp.bored.util;

import com.zxp.bored.entity.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 返回工具
 *
 * @description: 返回工具
 * @author: zxp
 * @date: 2024/9/18 23:22
 */
public class ResponseUtil {
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ApiResponse<T> response = new ApiResponse<>(200, "成功", data);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(int status, String message) {
        ApiResponse<T> response = new ApiResponse<>(status, message, null);
        return ResponseEntity.status(HttpStatus.valueOf(status)).body(response);
    }
}
