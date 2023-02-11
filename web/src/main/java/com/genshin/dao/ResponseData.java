package com.genshin.dao;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ResponseData<T> {
    private int status;
    private String message;
    private T data;
    private String date;

    public ResponseData() {
        this.date = LocalDateTime.now().toString();
    }

    public static <T> ResponseData<T> success( T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage(HttpStatus.OK.getReasonPhrase());
        responseData.setData(data);
        return responseData;
    }

    public static <T> ResponseData<T> fail(int statusCode, String message) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setStatus(statusCode);
        responseData.setMessage(message);
        return responseData;
    }
}
