/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author DuongThaiMinh
 */
public class Common {

    /**
     * Default Date formatter for records
     */
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy-KK:mm:ssZ");

    public static Dictionary<Token.Type, List<Token>> enum2dict(Enumeration<Token> tokens) {
        Dictionary<Token.Type, List<Token>> dict = new Hashtable<>();
        while (tokens.hasMoreElements()) {
            Token t = tokens.nextElement();
            if (dict.get(t.type) == null) {
                dict.put(t.type, new ArrayList<>());
            }
            dict.get(t.type).add(t);
        }
        return dict;
    }

    /**
     * Validation requests
     *
     * @param tokens {@code Enumeration<Token>} parsed from sequence
     * @param list   definition need to check, see: {@link RequestSet}, {@link hcmut.co3003.server.dal.FullInformationRecord}
     * @return true if the sequence's tokens match definition
     */
    public static boolean match(Enumeration<Token> tokens, Token.Type[] list) {
        List<Token> tList = Collections.list(tokens);
        if (tList.size() != list.length) return false;
        for (int i = 0; i < list.length; ++i) {
            if (tList.get(i).getType() != list[i]) return false;
        }
        return true;
    }

    public interface Error {

        class BadRequest implements Error {
            private String message;

            public BadRequest(String message) {
                this.message = message;
            }

            @Override
            public String toString() {
                return String.format("BAD REQUEST: %s", message);
            }
        }
    }
}
