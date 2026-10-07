package com.demo.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApiMessage<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;
    private Pagination pagination;
    
    public static <T> ApiMessage<T> success(T data) {
        return new ApiMessage<>(
                true,
                ApiCode.SUCCESS.getCode(),
                ApiCode.SUCCESS.getMessage(),
                data,
                null
        );
    }
    
    public static <T> ApiMessage<T> success(String message) {
        return new ApiMessage<>(
                true,
                ApiCode.SUCCESS.getCode(),
                message,
                null,
                null
        );
    }
    
    public static <T> ApiMessage<T> success(
            T data,
            Pagination pagination) {

        return new ApiMessage<>(
                true,
                ApiCode.SUCCESS.getCode(),
                ApiCode.SUCCESS.getMessage(),
                data,
                pagination
        );
    }
    
    public static <T> ApiMessage<T> success(
            String message,
            T data) {

        return new ApiMessage<>(
                true,
                ApiCode.SUCCESS.getCode(),
                message,
                data,
                null
        );
    }

    public static <T> ApiMessage<T> success(
    		String message,
            T data,
            Pagination pagination) {

        return new ApiMessage<>(
                true,
                ApiCode.SUCCESS.getCode(),
                message,
                data,
                pagination
        );
    }
    
    public static <T> ApiMessage<T> fail(ApiCode code) {
        return new ApiMessage<>(
                false,
                code.getCode(),
                code.getMessage(),
                null,
                null
        );
    }

    public static <T> ApiMessage<T> fail(
            ApiCode code,
            String message) {

        return new ApiMessage<>(
                false,
                code.getCode(),
                message,
                null,
                null
        );
    }
}