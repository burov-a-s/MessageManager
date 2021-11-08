package ru.iflex.burov.jms;

import ru.iflex.burov.interceptors.LoggerInterceptor;
import ru.iflex.burov.lib.MessageDAO;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.interceptor.Interceptors;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(mappedName = "ru/iflex/burov/MyPostingQueue")
@Interceptors(LoggerInterceptor.class)
public class ConsumerForPosting implements MessageListener {
    @EJB
    private MessageDAO messageDAO;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            ru.iflex.burov.entity.Message messageToAdd = (ru.iflex.burov.entity.Message) objectMessage.getObject();
            messageDAO.addMessage(messageToAdd);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
