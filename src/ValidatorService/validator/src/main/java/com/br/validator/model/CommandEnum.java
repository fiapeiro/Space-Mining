package com.br.validator.model;

public enum CommandEnum {
    RIGTH("RIGTH"),
    LEFT("LEFT"),
    FRONT("FRONT"),
    BACK("BACK"),
    OPEN("OPEN"),
    CLOSE("CLOSE");

    private final String command;

    CommandEnum(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
