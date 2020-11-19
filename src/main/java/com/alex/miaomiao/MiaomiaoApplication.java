package com.alex.miaomiao;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
public class MiaomiaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiaomiaoApplication.class, args);
	}

	@Bean
	public PromptProvider prompt() {
		return () -> new AttributedString("miaomiao:_> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.MAGENTA));
	}
}
