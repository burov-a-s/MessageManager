package ru.iflex.burov.config;

import MessageManagerBinding.MessageManagerConfiguration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

//@Interceptors(LoggerInterceptor.class)
public class MessageManagerConfigHelper {
    private String fileName = System.getProperty("user.dir").
            concat(File.separator).
            concat("appConfig").
            concat(File.separator).
            concat("MessagesManager.xml");
    private static MessageManagerConfigHelper instance = new MessageManagerConfigHelper();

    private static MessageManagerConfiguration messageManagerConfiguration;

    private MessageManagerConfigHelper() {
    }

    public static MessageManagerConfigHelper getInstance() {
        if (instance == null) {
            synchronized (MessageManagerConfigHelper.class) {
                if (instance == null) {
                    instance = new MessageManagerConfigHelper();
                }
            }
        }
        return instance;
    }

    public MessageManagerConfiguration getConfigurations() {
        if (messageManagerConfiguration == null) {
            synchronized (MessageManagerConfigHelper.class) {
                if (messageManagerConfiguration == null) {
                    messageManagerConfiguration = new MessageManagerConfiguration();
                    File file = new File(fileName);
                    try {
                        JAXBContext context = JAXBContext.newInstance(MessageManagerConfiguration.class);
                        Unmarshaller unmarshaller = context.createUnmarshaller();
                        messageManagerConfiguration = (MessageManagerConfiguration) unmarshaller.unmarshal(file);
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return messageManagerConfiguration;
    }
}