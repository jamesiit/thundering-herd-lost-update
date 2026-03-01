package com.example.backend.state;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class StoreState {

    private final AtomicBoolean isSoldOut = new AtomicBoolean(false);

    //getter
    public boolean checkIsSoldOut() {
        return isSoldOut.get();
    }

    //setter
    public void markAsSoldOut() {
        isSoldOut.set(true);
    }

}
