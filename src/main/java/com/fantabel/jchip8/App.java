package com.fantabel.jchip8;

import org.apache.commons.lang3.StringUtils;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" + args[0] + " " + StringUtils.isEmpty(args[0]) );
    }
}
