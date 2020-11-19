package com.alex.miaomiao.component;

import com.alex.miaomiao.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Date;
import java.util.Scanner;

@ShellComponent
@ShellCommandGroup("秒杀")
public class SubscribeComponent {

    @Autowired
    private SubscribeService subscribeService;
    private boolean init = false;

    @ShellMethod("初始化，设置cookie、tk和region，必须先执行此命令")
    public void init() {
        String cookies, tk, region;
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("请输入cookie（使用Base64加密）：\n");
            cookies = input.next();
            System.out.print("请输入tk：\n");
            tk = input.next();
            System.out.print("请选择城市：\n");
            region = input.next();
            System.out.println(String.format("输入参数：\n\t[cookie]:%s\n\t[tk]:%s\n\t[region]:%s", cookies, tk, region));
            System.out.println("确认参数输入正确?   [yes(y)/no(n)]");
            String confirm = input.next();
            if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
                break;
            }
        }

        init = true;
    }

    @ShellMethod("查看服务器当前时间")
    public void now() {
        Date time = new Date(subscribeService.serverTime());
        System.out.println(time);
    }

    @ShellMethod("开始秒杀")
    public void start() {
        // TODO 
    }

    @ShellMethodAvailability({"now", "start"})
    public Availability initCheck() {
        return init ? Availability.available() : Availability.unavailable("必要参数尚未设置，请先执行init命令");
    }

}
