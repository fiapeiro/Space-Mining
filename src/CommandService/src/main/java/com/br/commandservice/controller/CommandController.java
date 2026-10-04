package com.br.commandservice.controller;

import com.br.commandservice.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    private record CommandRequest(String command) {}

    @PostMapping("/command")
    public String command(@RequestBody CommandRequest request){
        log.info("Comando {} redirecionado para o serviço de validação", request.command());
        commandService.sendCommand(request.command);
        return "Comando redirecionado para o serviço de validação";
    }
}
