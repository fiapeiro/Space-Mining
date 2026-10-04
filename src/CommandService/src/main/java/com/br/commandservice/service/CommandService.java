package com.br.commandservice.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(
        url = "http://localhost:8081/command",
        accept = "application/json"
)
public interface CommandService {

    @PostMapping
    public void sendCommand(String command);
}
