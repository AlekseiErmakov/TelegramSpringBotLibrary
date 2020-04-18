package com.my.telegram.library.handler;

import org.springframework.context.ApplicationContextAware;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramBotUpdateHandler extends ApplicationContextAware {
    Validable onUpdateReceived(Update update);
}
