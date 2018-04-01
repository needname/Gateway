/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.util;

import org.testng.annotations.Test;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class TokenTest {

    @org.testng.annotations.Test
    public void testGetToken() {
    }

    @org.testng.annotations.Test
    public void testIsValidTimeStamp() {
        Date time = new Date();

        String timestampDefault = time.toString();
        String timestampUTC = Common.dateFormatter.format(time);


        System.out.println("timestampDefault = " + timestampDefault + " -> " + Token.isValidTimeStamp(timestampDefault));
        System.out.println("timestampUTC = " + timestampUTC + " -> " + Token.isValidTimeStamp(timestampUTC));
    }

    @Test
    public void testIsValidIdentifier() {
        Map<String, String> dict = new LinkedHashMap<>();
        dict.put("id default", "hello_world0123");
        dict.put("id general", "foo");
        dict.put("id number", "123456");
        dict.put("id mix", "_foo123");
        dict.put("id uppercase", "DUMMY");
        dict.put("id wrong 1", "this?");
        dict.put("id wrong 2", "\tfoo");

        dict.forEach((k, v) -> System.out.println(k + " = " + v + " -> " + Token.isValidIdentifier(v)));
    }

    @Test
    public void testTemperature() {
        Map<String, String> dict = new LinkedHashMap<>();
        dict.put("Default temperature","0273.15K");

        dict.put("Celsius temperature","0273oC");
        dict.put("Negative temperature","-273K");
        dict.put("Standalone temperature","273");
        dict.put("Standalone negative temperature","-273.01");

        dict.put("Bad temperature","foo");
        dict.put("Bad temperature 2","?@#");
        dict.put("Bad temperature 3","fooo.baK");

        dict.forEach((k, v) -> System.out.println(k + " = " + v + " -> " + Token.isValidTemperature(v)));
    }
}