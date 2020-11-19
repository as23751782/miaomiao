package com.alex.miaomiao.component;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class MyExit implements Quit.Command {

    @ShellMethod(value = "Exit the shell.", key = {"quit", "exit"}, group = "Built-In Commands")
    public void quit() {
        System.out.println("期待与您的下次相遇~~  撒由那拉(｡･ω･｡)ﾉ♡");
        System.exit(0);
    }
}
