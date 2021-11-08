package ru.iflex.burov.interceptors;

import org.apache.logging.log4j.Logger;
import ru.iflex.burov.loggers.MessageManagerLogger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Arrays;

public class LoggerInterceptor {
    private static final Logger LOGGER = MessageManagerLogger.getLogger(LoggerInterceptor.class.getName());

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        LOGGER.info("Начало работы метода - {} в классе - {}\n", context.getMethod().getName(), context.getTarget().getClass().getSimpleName());
        LOGGER.info("Метод вызывается с параметрами: {}\n", Arrays.asList(context.getParameters()));
        Object result = context.proceed();
        LOGGER.info("Результат работы метода: {}\n", result.toString());
        LOGGER.info("Конец работы метода - {} в классе - {}\n", context.getMethod().getName(), context.getTarget().getClass().getSimpleName());
        return result;
    }
}
