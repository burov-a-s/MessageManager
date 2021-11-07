package ru.iflex.burov.messageManager.web.rpc.errors;

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
}
