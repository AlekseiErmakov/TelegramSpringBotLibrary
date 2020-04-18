package com.my.telegram.library.annotations;

import org.springframework.stereotype.Component;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface TelegramBotController {
}
