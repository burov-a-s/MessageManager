package ru.iflex.burov.Converter;

import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

@Stateless
public class MessageConverter {

    public List<ru.iflex.burov.messsage.Message> convert(List<ru.iflex.burov.entity.Message> messagesIn) {
        if (messagesIn != null) {
            List<ru.iflex.burov.messsage.Message> messagesOut = new ArrayList<>();
            for (ru.iflex.burov.entity.Message messageIn : messagesIn) {
                ru.iflex.burov.messsage.Message messageOut = new ru.iflex.burov.messsage.Message();
                messageOut.setId(messageIn.getId());
                messageOut.setSender(messageIn.getSender());
                try {
                    Calendar calendarIn = messageIn.getSend_time();
                    XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendarIn.get(Calendar.YEAR),
                            calendarIn.get(Calendar.MONTH),
                            calendarIn.get(Calendar.DAY_OF_MONTH),
                            calendarIn.get(Calendar.HOUR),
                            calendarIn.get(Calendar.MINUTE),
                            calendarIn.get(Calendar.SECOND),
                            calendarIn.get(Calendar.MILLISECOND),
                            calendarIn.getTimeZone().getDSTSavings());
                    messageOut.setSendTime(xmlGregorianCalendar);
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
                messageOut.setContent(messageIn.getContent());
                messagesOut.add(messageOut);
            }
            return messagesOut;
        } else {
            return null;
        }
    }
}
