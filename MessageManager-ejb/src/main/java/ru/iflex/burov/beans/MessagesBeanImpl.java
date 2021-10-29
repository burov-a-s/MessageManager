package ru.iflex.burov.beans;

import ru.iflex.burov.entity.Message;
import ru.iflex.burov.jms.Sender;
import ru.iflex.burov.lib.MessageBean;
import ru.iflex.burov.lib.MessageDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Calendar;
import java.util.List;

@Stateless
public class MessagesBeanImpl implements MessageBean {
    @EJB
    private MessageDAO messageDAO;
    @EJB
    private Sender sender;

    public void addMessage(Message message) {
        sender.sendForPosting(message);
//        messageDAO.addMessage(message);
    }

    public void removeMessage(int id) {
        sender.sendForDeletion(id);
//        messageDAO.removeMessage(id);
    }


    public List<Message> getMessagesByDate(Calendar calendar) {
        return messageDAO.getMessagesByDate(calendar);
    }


    public List<Message> getMessagesBySender(String sender) {
        return messageDAO.getMessagesBySender(sender);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}

