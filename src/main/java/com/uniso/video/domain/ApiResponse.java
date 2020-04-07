package com.uniso.video.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 1813553490774959616L;

    private String errorMessage;
    private Integer errorCode;
    private T data;
    private Integer status;

    public ApiResponse() {
        this.errorCode = 0;
        this.data = null;
        this.errorMessage = null;
        this.status = null;
    }
}
