package com.my.telegram.library.handler;

import com.my.telegram.library.annotations.BotCommand;
import com.my.telegram.library.annotations.TelegramBotController;
import com.my.telegram.library.config.Script;
import com.my.telegram.library.exception.CommandUnsupportedException;
import com.my.telegram.library.exception.TelegramBotMethodException;
import com.my.telegram.library.exception.TelegramControllerException;
import com.my.telegram.library.exception.TelegramScriptException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class TelegramUpdateHandlerImpl implements TelegramBotUpdateHandler {

    private ApplicationContext ctx;

    private Script script;

    private TelegramUpdateScriptHandler telegramUpdateScriptHandler;

    @Autowired
    public void setScript(Script script) {
        this.script = script;
    }

    @Override
    @Autowired
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }

    @Override
    public Validable onUpdateReceived(Update update) {
        if (script == null){
            throw new TelegramScriptException("You must configure TelegramScriptConfig in your config");
        }else if (!script.isActive()){
            String userCommand = getUserCommand(update);
            Map<String, Object> beansWithAnnotation = ctx.getBeansWithAnnotation(TelegramBotController.class);

            if (beansWithAnnotation.size() == 0){
                throw new TelegramControllerException(
                        "Should be at least one class, annotated with \"@TelegramBotController\"");
            }
            Map<Method, Object> methodList = getAnnotatedMethods(beansWithAnnotation);
            if (methodList.size() == 0){
                throw  new CommandUnsupportedException("No such method for command");
            }
            Map<Method,Object> valueMethods = getAnnotatedMethods(methodList,userCommand);
            return getMessage(valueMethods,update);
        } else {
            return telegramUpdateScriptHandler.onUpdateReceived(update);
        }


    }

    private Validable getMessage(Map<Method,Object> valueMethods, Update update){

        for (Method method : valueMethods.keySet()){
            try{
                Validable validable
                       = (Validable) ReflectionUtils.invokeMethod(method,valueMethods.get(method), update);
                return validable;
            } catch (ClassCastException ex){
                throw new TelegramBotMethodException("Method return type should be \"instance of SendMessage\"");
            }
        }
        throw new TelegramBotMethodException("Method arg should be instance of Update");
    }

    private String getUserCommand(Update update) {
        Message message = update.getMessage();
        String messageText = message.getText();
        return getUserCommand(messageText);
    }

    private String getUserCommand(String messageText) {
        String[] s = messageText.trim().split(" ");
        if(s.length != 0 && !s[0].equals("")){
            if (s[0].substring(0,1).equals("/")){
                return s[0];
            } else {
                throw new CommandUnsupportedException("Command Should Starts With \"/\"");
            }
        }
        throw new CommandUnsupportedException("Command is empty");
    }

    private Map<Method, Object> getAnnotatedMethods(Map<String, Object> beansWithAnnotation ){
        Map<Method, Object> methodObjectMap = new HashMap<>();
        for (Object bean : beansWithAnnotation.values()){
            Method[] declaredMethods = bean.getClass().getDeclaredMethods();
            for (Method method : declaredMethods){
                BotCommand annotation = method.getAnnotation(BotCommand.class);
                if (annotation != null){
                    methodObjectMap.put(method,bean);
                }
            }
        }
        return methodObjectMap;
    }

    private Map<Method,Object> getAnnotatedMethods(Map<Method, Object> methodMap, String userCommand) {
        return methodMap.keySet().stream()
                .filter(method -> userCommand.equals(method.getAnnotation(BotCommand.class).value()))
                .collect(Collectors.toMap(method->method, methodMap::get));
    }

}
