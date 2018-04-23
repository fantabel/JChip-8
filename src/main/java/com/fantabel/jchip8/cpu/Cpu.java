package com.fantabel.jchip8.cpu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cpu {
	private static final Logger logger = LogManager.getLogger();
	private byte[] registers;
	private byte[] memory;

	private static final int NUMBER_OF_REGISTERS = 0x10;
	private static final int MEMORY_LENGTH = 0x1000;

	public Cpu() {
		logger.traceEntry();
		reset();
		logger.traceExit();
	}

	public void reset() {
		logger.traceEntry();

		logger.trace("Initializing registers");
		registers = new byte[NUMBER_OF_REGISTERS];

		logger.trace("Initializing memory");
		memory = new byte[MEMORY_LENGTH];

		logger.traceExit();
	}

	public String dump() {
		logger.traceEntry();
		StringBuilder sb = new StringBuilder();

		sb.append("Registers\n");
		int i = 0;
		for (byte b : registers)
			sb.append("v" + Integer.toHexString(i++).toUpperCase() + ": " + String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + "\n");
		
		sb.append("\nMemory\n");
		i = 0;
		for (byte b : memory) {
			if (i % 16 == 0)
				sb.append(String.format("%4s", Integer.toHexString(i)).toUpperCase().replace(' ', '0') + " ");
			sb.append(b);
			if (++i % 16 == 0)
				sb.append('\n');
		}
		return logger.traceExit(sb.toString());
	}
	
}
