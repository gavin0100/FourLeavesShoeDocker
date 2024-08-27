package com.data.filtro.interview;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;

@Component
public class MyValueProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(MyValue.class)) {
                MyValue myValue = field.getAnnotation(MyValue.class);
                String value = myValue.value();
                field.setAccessible(true);
                try {
                    // Convert the value to the appropriate type
                    if (field.getType() == int.class) {
                        field.setInt(bean, Integer.parseInt(value));
                    } else if (field.getType() == boolean.class) {
                        field.setBoolean(bean, Boolean.parseBoolean(value));
                    } else {
                        field.set(bean, value);
                    }
                } catch (IllegalAccessException e) {
                    throw new BeansException("Failed to inject value", e) {};
                }
            }
        }
        return bean;
    }
}
