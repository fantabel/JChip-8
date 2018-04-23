package com.fantabel.jchip8;

import org.apache.commons.lang3.StringUtils;

import com.fantabel.jchip8.cpu.Cpu;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
		Cpu cpu = new Cpu();
		cpu.dump();
    }
}
