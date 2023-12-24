package com.cailleach.mudengine.world;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages= {
		"com.cailleach.mudengine.world",
		"com.cailleach.mudengine.common"
})
@EnableAspectJAutoProxy
public class MudWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(MudWorldApplication.class, args);
	}
	
}
