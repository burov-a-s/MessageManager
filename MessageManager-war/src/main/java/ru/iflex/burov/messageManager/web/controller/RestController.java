package ru.iflex.burov.messageManager.web.controller;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.iflex.burov.entity.Message;
import ru.iflex.burov.interceptors.LoggerInterceptor;
import ru.iflex.burov.lib.MessageBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Path("/RestController")
@Stateless
@Interceptors(LoggerInterceptor.class)
public class RestController {

    @EJB
    private MessageBean messageBean;
    private ObjectMapper mapper = new ObjectMapper();

    //GET    /messages      - получить все записи(READ)
    //POST   /messages      - создать новую запись (CREATE)
    //GET    /messages/{id} - получить одну запись (READ)
    //PATCH  /messages/{id} - Обновить запись (UPDATE)
    //DELETE /messages/{id} - Удалить запись (DELETE)

    @POST
    @Path("/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMessage(String json) {
        try {
            Message message = mapper.readValue(json, Message.class);
            messageBean.addMessage(message);
            return Response.ok().build();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("/messages/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeMessage(@PathParam("id") int id) {
        try {
            messageBean.removeMessage(id);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("/messages")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMessageBySenderOrDate(@QueryParam("sender") String sender,
                                             @QueryParam("date") String strDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        mapper.setDateFormat(dateFormat);
        try {
            if (sender == null && strDate == null) {
                List<Message> messages = messageBean.getAllMessages();
                StringWriter writer = new StringWriter();
                mapper.writeValue(writer, messages);
                String result = writer.toString();
                return Response.ok(result).build();
            } else if (sender != null) {
                List<Message> messages = messageBean.getMessagesBySender(sender);
                StringWriter writer = new StringWriter();
                mapper.writeValue(writer, messages);
                String result = writer.toString();
                return Response.ok(result).build();
            } else if (strDate != null) {
                Date date = dateFormat.parse(strDate);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                List<Message> messages = messageBean.getMessagesByDate(calendar);
                StringWriter writer = new StringWriter();
                mapper.writeValue(writer, messages);
                String result = writer.toString();
                return Response.ok(result).build();
            }
            return Response.ok().build();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
