package ru.iflex.burov.jms;

import ru.iflex.burov.entity.Message;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;

@Stateless
public class Sender {
    @Resource(lookup = "ru/iflex/burov/MyConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "ru/iflex/burov/MyDeletionQueue")
    private Queue queueForDeletion;

    @Resource(lookup = "ru/iflex/burov/MyPostingQueue")
    private Queue queueForPosting;

    public void sendForDeletion(int id) {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession();
            MessageProducer producer = session.createProducer(queueForDeletion);
            TextMessage textMessage = session.createTextMessage(String.valueOf(id));
            producer.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendForPosting(Message message) {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession();
            MessageProducer producer = session.createProducer(queueForPosting);
            ObjectMessage objectMessage = session.createObjectMessage(message);
            producer.send(objectMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
