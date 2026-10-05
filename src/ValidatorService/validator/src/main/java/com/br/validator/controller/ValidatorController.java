package com.br.validator.controller;

import com.br.validator.config.RabbitConfig;
import com.br.validator.model.CommandEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class ValidatorController {

    private final RabbitTemplate rabbitTemplate;

    public ValidatorController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private record CommandRequest(CommandEnum commandEnum){}

    @Retryable(
            includes = ResponseStatusException.class,
            maxRetries = 5,
            delay = 500,
            jitter = 20,
            //exponentialBackoff
            multiplier = 2,
            maxDelay = 10_000
    )
    @PostMapping("/command")
    public void validateCommand(@RequestBody CommandRequest request) {
        try{
            log.info("Comando {} recebido para validação", request.commandEnum());

            var random = Math.random();
            if (random < 0.001) {
                log.info("Redirecionamento para o serviço de comando devido a falha na validação do comando");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Redirecionamento para o serviço de comando devido a falha na validação do comando");
            }
            log.info("Comando {} validado com sucesso", request.commandEnum());
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE_NAME,
                    RabbitConfig.ROUTING_KEY,
                    request.commandEnum().name()
            );
            log.info("Comando {} enviado para o RabbitMQ", request.commandEnum());

        }catch (Exception e){
            log.error("Erro ao validar o comando {}: {}", request.commandEnum(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao validar o comando " + request.commandEnum() + ": " + e.getMessage());
        }
    }

}
