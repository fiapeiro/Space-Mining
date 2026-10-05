package com.br.commandservice.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(
        url = "http://localhost:8081",
        accept = "application/json",
        contentType = "application/json"
)
public interface CommandService {

    record CommandPayload(String commandEnum) {}

    @PostExchange("/command")
    void sendCommand(@RequestBody CommandPayload command);
}
