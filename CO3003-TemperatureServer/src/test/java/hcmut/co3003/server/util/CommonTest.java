/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.util;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Enumeration;

public class CommonTest {

    @Test
    public void testMatch() throws Exception {
        String query = "SET foo sensor_01 03/28/2018-10:04:30+0700 0300.17K 0.2345%";
        Enumeration<Token> tokens = Parser.parse(query);

        System.out.println(Collections.list(Parser.parse(query)));
        if (Common.match(tokens, RequestSet.SET_REQUEST)) {
            System.out.println("This is a set request");
        }
        System.out.println(Collections.list(Parser.parse(query)));
        if (Common.match(tokens, RequestSet.GET_REQUEST)) {
            System.out.println("This is a get request");
        }
        if (Common.match(tokens, RequestSet.DOWNLOAD_GET_REQUEST)) {
            System.out.println("This is a download-get request");
        }
        if (Common.match(tokens, RequestSet.DOWNLOAD_REQUEST)) {
            System.out.println("This is a download-set-up request");
        }
        if (Common.match(tokens, RequestSet.REGISTER_GATEWAY_REQUEST)) {
            System.out.println("This is a gateway register request");
        }
    }
}