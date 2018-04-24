package com.fantabel.jchip8.cpu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Cpu {
	private static final Logger logger = LogManager.getLogger();
	private byte[] registers;
	private byte[] memory;
	private boolean[] screen;
	private short[] stack;
	private boolean[] keys;

	private short I;
	private short programCounter;
	private byte stackPointer;

	private byte delayTimer;
	private byte soundTimer;

	private static final int NUMBER_OF_REGISTERS = 0x10;
	private static final int MEMORY_LENGTH = 0x1000;
	private static final int NUMBER_OF_KEYS = 0x10;
	private static final int STACK_SIZE = 0x10;
	private static final int SCREEN_WIDTH = 64;
	private static final int SCREEN_HEIGHT = 32;
	private static final int SCREEN_SIZE = SCREEN_WIDTH * SCREEN_HEIGHT;

	public Cpu() {
		logger.traceEntry();
		init();
		reset();
		logger.traceExit();
	}

	private void init() {
		registers = new byte[NUMBER_OF_REGISTERS];
		memory = new byte[MEMORY_LENGTH];
		keys = new boolean[NUMBER_OF_KEYS];
		screen = new boolean[SCREEN_SIZE];
		stack = new short[STACK_SIZE];
	}

	public void reset() {
		logger.traceEntry();

		logger.trace("Initializing registers");
		Arrays.fill(registers, (byte)0);

		logger.trace("Initializing memory");
		Arrays.fill(memory, (byte)0);

		logger.trace("Initializing keyboard");
		Arrays.fill(keys, false);

		logger.trace("Initializing screen");
		Arrays.fill(screen, false);

		logger.trace("Initializing stack");
		Arrays.fill(stack, (short)0);

		logger.trace("Initializing variables");
		I = 0;
		programCounter = 0x200;
		stackPointer = 0;
		delayTimer = 0;
		soundTimer = 0;

		logger.traceExit();
	}

	private static byte getRandomByte() {
		int range = Byte.MAX_VALUE - Byte.MIN_VALUE;
		return (byte)((Math.random() * range) + Byte.MIN_VALUE);
	}

	//TODO Move to a util class
	private static void fillWithRandomData(byte[] array) {
		for (int i = 0 ; i < array.length ; i++)
			array[i] = getRandomByte();
	}
	
	public static boolean isCharPrintable(int i) {
		return (i >= 32 && i <= 126 ||
			i >= 161 && i <= 172 ||
			i >= 174 && i <= 255);
	}

	public static char convertByteToChar(int i) {
		return isCharPrintable(i) ? (char)i : '.';
	}

	public String dump() {
		logger.traceEntry();
		StringBuilder sb = new StringBuilder();

		sb.append("Registers\n");
		sb.append(dumpRegisters());

		sb.append("\nMemory\n");
		sb.append(dumpMemory());
		
		return logger.traceExit(sb.toString());
	}

	public String dumpRegisters() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		StringBuilder sbTemp = new StringBuilder();
		for (byte b : registers) {
			sb.append("V" + String.format("%x", i++) + ": " + String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " " + String.format("%02X", b) + "\n");
			//sb.append("V" + String.format("%x", i++) + ": " + String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " " + String.format("%2s", Integer.toHexString(b & 0xFF)).replace(' ', '0').toUpperCase() + "\n");
		}
		return sb.toString();
	}

	public String dumpMemory() {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbTemp = new StringBuilder();
		int i = 0;
		for (byte b : memory) {
			if (i % 16 == 0)
				sb.append(String.format("%04X ", i));
			sbTemp.append(convertByteToChar(b));
			sb.append(String.format("%02X ", b));
			if (++i % 16 == 0) {
				sb.append(sbTemp.toString());
				sbTemp = new StringBuilder();
				sb.append('\n');
			}
		}
		return sb.toString();
	}
}
