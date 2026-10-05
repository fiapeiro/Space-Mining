package com.br.commandservice.controller;

import com.br.commandservice.service.CommandService;
import com.br.commandservice.service.CommandService.CommandPayload;
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

    public record CommandRequest(String command) {}

    @PostMapping("/command")
    public String command(@RequestBody CommandRequest request){
        log.info("Comando {} redirecionado para o serviço de validação", request.command());
        try{
            commandService.sendCommand(new CommandPayload(request.command()));
        } catch (Exception e) {
            log.error("Erro ao redirecionar o comando {}: {}", request.command(), e.getMessage());
            throw new RuntimeException("Erro ao redirecionar o comando " + request.command() + ": " + e.getMessage());
        }
        return "Comando redirecionado para o serviço de validação";
    }
}
