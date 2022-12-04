package com.epam.training.ticketservice.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class QuitCommand implements Quit.Command {

    @SuppressWarnings("unused")
    @ShellMethod(key = "exit", value = "Terminates the application process")
    public void exit() {
        System.exit(0);
    }

}
