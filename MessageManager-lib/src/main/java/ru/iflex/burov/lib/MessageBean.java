package ru.iflex.burov.lib;

import ru.iflex.burov.entity.Message;

import java.util.Calendar;
import java.util.List;

public interface MessageBean {
    public void addMessage(Message message);

    public void removeMessage(int id);

    public List<Message> getMessagesByDate(Calendar calendar);

    public List<Message> getMessagesBySender(String sender);

    public List<Message> getAllMessages();
}
