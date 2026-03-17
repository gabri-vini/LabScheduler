package com.xaviervinicius.labschedule.services;

import com.xaviervinicius.labschedule.dto.SimpleEmail;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {
    public CompletableFuture<Boolean> sendAsync(SimpleEmail email){
        throw new UnsupportedOperationException("Unimplemented");
    }
}
