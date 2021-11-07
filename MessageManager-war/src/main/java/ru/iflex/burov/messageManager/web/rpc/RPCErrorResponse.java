package ru.iflex.burov.messageManager.web.rpc;

import ru.iflex.burov.messageManager.web.rpc.errors.RPCError;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RPCErrorResponse {
    private String jsonrpc = "2.0";
    @Inject
    private RPCError rpcError;
    private String id;

    public RPCErrorResponse() {
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public RPCError getRpcError() {
        return rpcError;
    }

    public void setRpcError(RPCError rpcError) {
        this.rpcError = rpcError;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
