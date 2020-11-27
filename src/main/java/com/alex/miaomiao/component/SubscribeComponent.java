package com.alex.miaomiao.component;

import com.alex.miaomiao.config.Util;
import com.alex.miaomiao.service.SubscribeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(SubscribeComponent.class);

    @Autowired
    private SubscribeService subscribeService;
    private boolean init = false;

    @ShellMethod("初始化，设置cookie、tk和region，必须先执行此命令")
    public void init() {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("请输入header（使用Base64加密）：\n");
            String header = input.next();
            System.out.print("请选择城市：(成都-5101，天津-1201)\n");
            String region = input.next();
            System.out.println("确认参数输入正确?   [y/n]");
            String confirm = input.next();
            if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
                Util.setHeader(header);
                Util.REGION = region;
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

    @ShellMethod("查看疫苗列表")
    public void list() {
        subscribeService.listVacc(Util.REGION).forEach(System.out::println);
    }

    @ShellMethod("开始秒杀")
    public void start() {
        // TODO
        subscribeService.subscribe();
    }

    @ShellMethodAvailability({"now", "start"})
    public Availability initCheck() {
        return init ? Availability.available() : Availability.unavailable("必要参数尚未设置，请先执行init命令");
    }

}
