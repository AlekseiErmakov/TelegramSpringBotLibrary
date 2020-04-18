package com.my.telegram.library.telegram;

import com.my.telegram.library.exception.TelegramNotValidMessageException;
import com.my.telegram.library.handler.TelegramBotUpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.basic.ProxyingInstantiator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



public class TelegramSpringBot extends TelegramLongPollingBot {

    private String botToken;

    private String botName;

    private TelegramBotUpdateHandler handler;

    public void setHandler(TelegramBotUpdateHandler handler) {
        this.handler = handler;
    }



    public void setBotToken(String botToken){
        this.botToken = botToken;
    }
    public void setBotName(String botName){
        this.botName = botName;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            try {
                Validable validable = handler.onUpdateReceived(update);
                BotApiMethod<Message> sender = (BotApiMethod<Message>) validable;
                execute(sender);
            } catch (TelegramNotValidMessageException ex){
                ex.printStackTrace();
            } catch (TelegramApiException e) {
                System.out.println("nen");
            }
        }
    }
    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
