package com.br.miningservice.service;

import com.br.miningservice.config.RabbitConfig;
import com.br.miningservice.model.Command;
import com.br.miningservice.repository.CommandRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class MiningService {

    private final CommandRepository commandRepository;

    private ConcurrentHashMap<String, Integer> totalCommands = new ConcurrentHashMap<>();

    public MiningService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void processComand(String command){
        log.info("Movimentando o robo com o comando: {}", command);
        totalCommands.merge(command, 1, Integer::sum);
    }

    public Map<String, Integer> getTotalCommands() {
        log.info("Total de comandos: {}", totalCommands);
        return commandRepository.findAll().stream()
                .collect(
                        ConcurrentHashMap::new,
                        (map, command) -> map.put(command.getCommandId(), command.getTotalCommands()),
                        ConcurrentHashMap::putAll
                );
    }

    @Scheduled(fixedDelay = 5_000)
    public void flush(){
        if (totalCommands.isEmpty()){
            log.info("Nenhum comando para processar.");
            return;
        }
        log.info("Processando {} comandos.", totalCommands.size());
        totalCommands.forEach(this::addCommandForRobot);
        totalCommands.clear();
    }

    @Transactional
    public void addCommandForRobot(String participantId, Integer commands){
        var command = commandRepository.findById(participantId).orElseGet(
                () -> new Command(participantId, 0)
        );
        command.setTotalCommands(command.getTotalCommands() + commands);
        commandRepository.save(command);
    }
}
