package com.my.telegram.library.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import javax.annotation.PostConstruct;

public class TelegramBotInitializerImpl implements TelegramBotInitializer{

    private TelegramBotsApi telegramBotsApi;

    private LongPollingBot longPollingBot;

    public void setTelegramBotsApi(TelegramBotsApi telegramBotsApi) {
        this.telegramBotsApi = telegramBotsApi;
    }

    public void setLongPollingBot(LongPollingBot longPollingBot) {
        this.longPollingBot = longPollingBot;
    }

    @PostConstruct
    @Override
    public void registerBot(){
        try {
            telegramBotsApi.registerBot(longPollingBot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}