package ru.iflex.burov.messageManager.web.rpc;

import ru.iflex.burov.entity.Message;
import ru.iflex.burov.interceptors.LoggerInterceptor;

import javax.interceptor.Interceptors;
import java.util.List;

@Interceptors(LoggerInterceptor.class)
public class RPCResponse {
    private String jsonrpc = "2.0";
    private List<Message> result;
    private String id;

    public RPCResponse() {
    }

    public List<Message> getResult() {
        return result;
    }

    public void setResult(List<Message> result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    @Override
    public String toString() {
        return "RPCResponse{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", result=" + result +
                ", id='" + id + '\'' +
                '}';
    }
}
