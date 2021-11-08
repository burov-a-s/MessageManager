package ru.iflex.burov.messageManager.web.rpc.errors;

import ru.iflex.burov.interceptors.LoggerInterceptor;

import javax.interceptor.Interceptors;

@Interceptors(LoggerInterceptor.class)
public class RPCError {
    private String code;
    private String message;
    private String id;

    public RPCError() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RPCError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
