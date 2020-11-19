package com.alex.miaomiao.component;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Script;

@ShellComponent
public class MyScript implements Script.Command {

    @ShellMethod(value = "to do ~~", group = "Built-In Commands")
    public void script() {
        System.out.println("to do ~~");
    }
}
