package com.example.shopping;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.shopping.service.InitService;

@Component
public class ConsoleRunner implements CommandLineRunner {
	private final InitService init;

	public ConsoleRunner(InitService init) {
		this.init = init;
	}

	@Override
	public void run(String... args) throws Exception {
		init.initDb();
	}

}
