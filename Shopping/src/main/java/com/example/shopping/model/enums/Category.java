package com.example.shopping.model.enums;

public enum Category {

	COMPUTERS("Computers"), SMARTPHONES("Smartphones"), SMARTWATCHES("Smart watches"), TABLETS("Tablets"),
	LAPTOPS("Laptops"), CONSOLES("Consoles"), MONITORS("Monitors"), FLASHDRIVES("Flash drives");

	public String name;

	Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
