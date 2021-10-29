package ru.iflex.burov.config;

import MessageManagerBinding.MessageManagerConfiguration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class MyConfigHelper {
    private String fileName = System.getProperty("user.dir").
            concat(File.separator).
            concat("appConfig").
            concat(File.separator).
            concat("MessagesManager.xml");
    private static MyConfigHelper instance = new MyConfigHelper();

    private static MessageManagerConfiguration messageManagerConfiguration;

    private MyConfigHelper() {
    }

    public static MyConfigHelper getInstance() {
        if (instance == null) {
            synchronized (MyConfigHelper.class) {
                if (instance == null) {
                    instance = new MyConfigHelper();
                }
            }
        }
        return instance;
    }

    public MessageManagerConfiguration getConfigurations() {
        System.out.println();
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("fileName: " + fileName);
        System.out.println("--------------------------------------------------");
        System.out.println();
        System.out.println();
        if (messageManagerConfiguration == null) {
            synchronized (MyConfigHelper.class) {
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
