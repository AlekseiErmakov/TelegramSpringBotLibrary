package com.my.telegram.library.postprocessor;

import com.my.telegram.library.annotations.StartValue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class StartValueAnnotationBeanPostProcessor implements BeanPostProcessor {

    Map<String, Object> beans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getFields();
        for (Field field : fields){
            StartValue startValue = field.getAnnotation(StartValue.class);
            if (startValue != null){
                beans.put(beanName,bean);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Object beanInMap = beans.get(beanName);
        if (beanInMap != null){
            Field[] fields = beanInMap.getClass().getFields();
            for (Field field : fields){
                StartValue annotation = field.getAnnotation(StartValue.class);
                if (annotation!=null){
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,bean,annotation.value());
                }
            }
        }
        return bean;
    }
}
