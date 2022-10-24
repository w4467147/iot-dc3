/*
 * Copyright 2022 Pnoker All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pnoker.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.pnoker.common.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Response
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean ok = false;
    private int code = Code.OK.getValue();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message = CommonConstant.Response.ERROR;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 成功
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> ok() {
        return new R<T>().success();
    }

    /**
     * 成功 自定义提示信息
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> ok(String message) {
        return new R<T>().success(message);
    }

    /**
     * 成功 自定义 Code & 提示信息
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> ok(Code code, String message) {
        return new R<T>().success(code.getValue(), message);
    }

    /**
     * 成功 返回结果
     *
     * @param data 返回结果
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> ok(T data) {
        return new R<T>(data).success();
    }

    /**
     * 成功 返回结果 & 自定义提示信息
     *
     * @param data 返回结果
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> ok(T data, String message) {
        return new R<T>(data).success(message);
    }

    /**
     * 失败
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> fail() {
        return new R<T>().failure();
    }

    /**
     * 失败 自定义提示信息
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> fail(String message) {
        return new R<T>().failure(message);
    }

    /**
     * 失败 自定义 Code & 提示信息
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> fail(Code code, String message) {
        return new R<T>().failure(code.getValue(), message);
    }

    /**
     * 失败 返回结果
     *
     * @param data 返回结果
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> fail(T data) {
        return new R<T>(data).failure();
    }

    /**
     * 失败 返回结果 & 自定义提示信息
     *
     * @param data 返回结果
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> R<T> fail(T data, String message) {
        return new R<T>(data).failure(message);
    }

    /**
     * 构造函数
     *
     * @param data 数据
     */
    private R(T data) {
        this.data = data;
    }

    /**
     * 成功
     *
     * @return Response
     */
    private R<T> success() {
        this.ok = true;
        this.code = Code.OK.getValue();
        this.message = CommonConstant.Response.OK;
        return this;
    }

    /**
     * 成功 自定义提示信息
     *
     * @param message 成功提示信息
     * @return Response
     */
    private R<T> success(String message) {
        this.ok = true;
        this.code = Code.OK.getValue();
        this.message = message;
        return this;
    }

    /**
     * 成功 自定义提示信息
     *
     * @param code    Code
     * @param message 成功提示信息
     * @return Response
     */
    private R<T> success(int code, String message) {
        this.ok = true;
        this.code = code;
        this.message = message;
        return this;
    }

    /**
     * 失败
     *
     * @return Response
     */
    private R<T> failure() {
        this.ok = false;
        this.code = Code.FAILURE.getValue();
        this.message = CommonConstant.Response.ERROR;
        return this;
    }

    /**
     * 失败 自定义提示信息
     *
     * @param message 错误提示信息
     * @return Response
     */
    private R<T> failure(String message) {
        this.ok = false;
        this.code = Code.FAILURE.getValue();
        this.message = message;
        return this;
    }

    /**
     * 失败 自定义提示信息
     *
     * @param code    Code
     * @param message 错误提示信息
     * @return Response
     */
    private R<T> failure(int code, String message) {
        this.ok = false;
        this.code = code;
        this.message = message;
        return this;
    }

    @Getter
    @AllArgsConstructor
    public enum Code {
        OK(200),
        FAILURE(500),
        NOT_FOUND(3404);

        private final int value;
    }
}