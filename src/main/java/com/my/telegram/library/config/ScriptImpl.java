package com.my.telegram.library.config;

import org.springframework.stereotype.Component;

@Component
public class ScriptImpl implements Script{


    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
