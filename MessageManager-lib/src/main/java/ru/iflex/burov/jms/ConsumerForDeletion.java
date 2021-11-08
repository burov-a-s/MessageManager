package ru.iflex.burov.jms;

import ru.iflex.burov.lib.MessageDAO;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.interceptor.Interceptors;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import ru.iflex.burov.interceptors.LoggerInterceptor;

@MessageDriven(mappedName = "ru/iflex/burov/MyDeletionQueue")
@Interceptors(LoggerInterceptor.class)
public class ConsumerForDeletion implements MessageListener {
    @EJB
    private MessageDAO messageDAO;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            int id = Integer.parseInt(textMessage.getText());
            messageDAO.removeMessage(id);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
