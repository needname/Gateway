/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.util;

import java.util.*;

public class Parser {
    public static Enumeration<Token> parse(String source) {
        StringTokenizer tokenizer = new StringTokenizer(source);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            tokens.add(Token.getToken(tokenizer.nextToken()));
        }

        return Collections.enumeration(tokens);
    }

}
