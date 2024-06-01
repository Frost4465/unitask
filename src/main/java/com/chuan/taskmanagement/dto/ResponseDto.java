package com.chuan.taskmanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@ToString
public class ResponseDto<T> {

    private T data;

    private int status;

    private String errorCode;

    public ResponseDto(HttpStatus status) {
        this.status = status.value();
    }


    public ResponseDto(T body, int status) {
        this.data = body;
        this.status = status;
    }

    public ResponseDto(T body, HttpStatus status) {
        this.data = body;
        this.status = status.value();
    }

    public ResponseDto(T body, String errorCode, HttpStatus status) {
        this.data = body;
        this.status = status.value();
        this.errorCode = errorCode;
    }

    public ResponseDto(T body, String errorCode, int status) {
        this.data = body;
        this.status = status;
        this.errorCode = errorCode;
    }

}
