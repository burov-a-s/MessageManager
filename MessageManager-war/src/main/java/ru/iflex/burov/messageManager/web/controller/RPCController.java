package ru.iflex.burov.messageManager.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.iflex.burov.entity.Message;
import ru.iflex.burov.interceptors.LoggerInterceptor;
import ru.iflex.burov.lib.MessageBean;
import ru.iflex.burov.messageManager.web.rpc.RPCErrorResponse;
import ru.iflex.burov.messageManager.web.rpc.RPCRequest;
import ru.iflex.burov.messageManager.web.rpc.RPCResponse;
import ru.iflex.burov.messageManager.web.rpc.errors.RPCError;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/RPCController")
@Stateless
@Interceptors(LoggerInterceptor.class)
public class RPCController {

    @EJB
    private MessageBean messageBean;
    private final ObjectMapper mapper = new ObjectMapper();

    @POST
    @Path("/messages")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllMessages(String json) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        mapper.setDateFormat(dateFormat);
        String rpcRequestId = null;
        try {
            RPCRequest rpcRequest = mapper.readValue(json, RPCRequest.class);
            rpcRequestId = rpcRequest.getId();
            if (rpcRequest.getJsonrpc().equals("2.0") && rpcRequest.getMethod().equals("getAllMessages")) {
                List<Message> messages = messageBean.getAllMessages();
                RPCResponse rpcResponse = new RPCResponse();
                rpcResponse.setResult(messages);
                rpcResponse.setId(rpcRequestId);
                StringWriter writer = new StringWriter();
                mapper.writeValue(writer, rpcResponse);
                String result = writer.toString();
                return Response.ok(result).build();
            } else {
                RPCError error = new RPCError();
                error.setCode("-32600");
                error.setMessage("Invalid Request");
                RPCErrorResponse errorResponse = new RPCErrorResponse();
                errorResponse.setRpcError(error);
                errorResponse.setId(rpcRequestId);
                try {
                    StringWriter writer = new StringWriter();
                    mapper.writeValue(writer, errorResponse);
                    String result = writer.toString();
                    return Response.ok(result).build();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RPCError error = new RPCError();
        error.setCode("-32700");
        error.setMessage("Parse error");
        RPCErrorResponse errorResponse = new RPCErrorResponse();
        errorResponse.setRpcError(error);
        errorResponse.setId(rpcRequestId);
        try {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, errorResponse);
            String result = writer.toString();
            return Response.ok(result).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}