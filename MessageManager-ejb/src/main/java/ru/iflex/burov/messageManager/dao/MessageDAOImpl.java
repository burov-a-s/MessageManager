package ru.iflex.burov.messageManager.dao;

import ru.iflex.burov.entity.Message;
import ru.iflex.burov.lib.MessageDAO;
import ru.iflex.burov.messageManager.connections.DBConnection;

import javax.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Stateless
public class MessageDAOImpl implements MessageDAO {

    public void addMessage(Message message) {
        try (Connection connection = DBConnection.getConnection()) {
            String strSQL = "INSERT INTO message (sender, send_time, content) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(strSQL);
            preparedStatement.setString(1, message.getSender());
            Timestamp timestamp = new Timestamp(message.getSend_time().getTimeInMillis());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.setString(3, message.getContent());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMessage(int id) {
        try (Connection connection = DBConnection.getConnection()) {
            String strSQL = "DELETE FROM message WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(strSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessagesByDate(Calendar calendar) {
        List<Message> messages = null;
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        try (Connection connection = DBConnection.getConnection()) {
            String strSQL = "SELECT * FROM message WHERE DATE(send_time) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(strSQL);
            preparedStatement.setTimestamp(1, timestamp);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                messages = new ArrayList();
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setId(resultSet.getInt("id"));
                    message.setSender(resultSet.getString("sender"));
                    message.setSend_time(calendar);
                    message.setContent(resultSet.getString("content"));
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public List<Message> getMessagesBySender(String sender) {
        List<Message> messages = null;
        try (Connection connection = DBConnection.getConnection()) {
            String strSQL = "SELECT * FROM message WHERE sender = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(strSQL);
            preparedStatement.setString(1, sender);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                messages = new ArrayList();
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setId(resultSet.getInt("id"));
                    message.setSender(resultSet.getString("sender"));
                    Timestamp timestamp1 = resultSet.getTimestamp("send_time");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timestamp1.getTime());
                    message.setSend_time(calendar);
                    message.setContent(resultSet.getString("content"));
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = null;
        try (Connection connection = DBConnection.getConnection();) {
            String strSQL = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(strSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                messages = new ArrayList();
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setId(resultSet.getInt("id"));
                    message.setSender(resultSet.getString("sender"));
                    Timestamp timestamp1 = resultSet.getTimestamp("send_time");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timestamp1.getTime());
                    message.setSend_time(calendar);
                    message.setContent(resultSet.getString("content"));
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}