/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.util;

import org.testng.annotations.Test;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParserTest {
    @Test
    public void testParser() {
        Map<String, String> dict = new LinkedHashMap<>();
        dict.put("Query GET 1", "GET foo");
        dict.put("Query SET 1", "SET foo 03/28/2018-10:04:30+0700 0300.17K 0.2345%");
//        dict.put("", "");
        dict.forEach((k, v) ->
        {
            System.out.print(k + " = " + v + "\n\t->");
            Enumeration<Token> tokens = Parser.parse(v);
            while(tokens.hasMoreElements()){
                System.out.print(tokens.nextElement().type+" ");
            }
            System.out.println();
        });
    }
}