package com.br.miningservice.controller;

import com.br.miningservice.service.MiningService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MiningController {

    private final MiningService miningService;

    public MiningController(MiningService miningService) {
        this.miningService = miningService;
    }

    @GetMapping("/total-command")
    public Map<String, Integer> getTotalCommands() {
        return miningService.getTotalCommands();
    }
}
