package ru.otus.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.TestManager;

@ShellComponent
public class ShellCommandsManager {
    private final TestManager testManager;

    @Autowired
    public ShellCommandsManager(TestManager testManager) {
        this.testManager = testManager;
    }

    @ShellMethod(value = "run", key = { "run" })
    public void run() {
        testManager.run();
    }
}
