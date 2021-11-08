package ru.iflex.burov.soapService;

import ru.iflex.burov.Converter.MessageConverter;
import ru.iflex.burov.entity.Message;
import ru.iflex.burov.interceptors.LoggerInterceptor;
import ru.iflex.burov.lib.MessageBean;
import ru.iflex.burov.messsage.GetMessageByDateRequest;
import ru.iflex.burov.messsage.GetMessageBySenderRequest;
import ru.iflex.burov.messsage.GetMessageResponse;
import ru.iflex.burov.messsage.ws.MessageManagerPortType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import java.util.Calendar;
import java.util.List;

@WebService(serviceName = "MessageManagerService",
        portName = "MessageManagerPortType",
        endpointInterface = "ru.iflex.burov.messsage.ws.MessageManagerPortType",
        targetNamespace = "http://burov.iflex.ru/messsage/ws")
@Stateless
@Interceptors(LoggerInterceptor.class)
public class MessageManagerSoapService implements MessageManagerPortType {

    @EJB
    private MessageBean messageBean;

    @EJB
    private MessageConverter converter;

    @Override
    public GetMessageResponse getMessageByDate(GetMessageByDateRequest request) {
        if (request.getDate() != null) {
            Calendar calendar = request.getDate().toGregorianCalendar();
            List<Message> messages = messageBean.getMessagesByDate(calendar);
            GetMessageResponse response = new GetMessageResponse();
            response.getMessages().addAll(converter.convert(messages));
            return response;
        } else {
            return null;
        }
    }

    @Override
    public GetMessageResponse getMessageBySender(GetMessageBySenderRequest request) {
        if (request.getSender() != null) {
            String sender = request.getSender();
            List<Message> messages = messageBean.getMessagesBySender(sender);
            GetMessageResponse response = new GetMessageResponse();
            response.getMessages().addAll(converter.convert(messages));
            return response;
        } else {
            return null;
        }
    }
}