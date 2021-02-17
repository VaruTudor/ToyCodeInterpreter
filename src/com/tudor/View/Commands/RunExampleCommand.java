package com.tudor.View.Commands;

import com.tudor.Controller.Controller;

public class RunExampleCommand extends Command{
    private final Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        } catch (RuntimeException e) {
            System.out.println(e.toString());
        }
    }
}
