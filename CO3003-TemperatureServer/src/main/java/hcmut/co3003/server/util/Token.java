/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.util;

import java.text.ParsePosition;
import java.util.Date;

public class Token {

    private static final int OTP_LENGTH = 64;
    protected final Type type;
    protected final String value;

    protected Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public static Token getToken(String st) {
        Type type = Type.Undefined;

        if (st.toUpperCase().equals("GET")) {
            type = Type.GET;
        }
        if (st.toUpperCase().equals("SET")) {
            type = Type.SET;
        }
        if (st.toUpperCase().equals("DOWNLOAD")) {
            type = Type.DOWNLOAD;
        }
        if (st.toUpperCase().equals("FILE")) {
            type = Type.FILE;
        }
        if (st.toUpperCase().equals("REGISTER_GATEWAY")){
            type = Type.REGISTER_GATEWAY;
        }
        if (isValidTimeStamp(st)) {
            type = Type.TimeStamp;
        }
        if (isValidIdentifier(st)) {
            type = Type.Identifier;
        }
        if (isValidTemperature(st)) {
            type = Type.Temperature;
        }
        if (isValidHumidity(st)) {
            type = Type.Humidity;
        }
        if (st.length() == OTP_LENGTH) {
            type = Type.Otp;
        }

        return new Token(type, st);
    }

    /**
     * regex = '0\.[0-9]{4}%';
     *
     * @param source string to validate
     * @return true if the source is match
     */
    private static boolean isValidHumidity(String source) {
        return source.matches("0\\.[0-9]{4}%");
    }

    /**
     * regex = '[0-1]\d/[0-3]\d/\d{4}-[0-2]\d:[0-5]\d:[0-5]\d\+\d{4}';
     * regex = '\d{2}/\d{2}/\d{4}-\d{2}:\d{2}:\d{2}\+\d{4}';
     *
     * @param source string to validate
     * @return true if the source is match
     */
    protected static boolean isValidTimeStamp(String source) {
        return source.matches("[0-1]\\d/[0-3]\\d/\\d{4}-[0-2]\\d:[0-5]\\d:[0-5]\\d\\+\\d{4}");
    }

    /**
     * regex = '[a-z_][a-z0-9_]*';
     *
     * @param source string to validate
     * @return true if the source is match
     */
    protected static boolean isValidIdentifier(String source) {
        return source.matches("[a-z_][a-z0-9_]*");
    }

    /**
     * regex = '[0-9]{4}\.[0-9]{2}K';
     *
     * @param source string to validate
     * @return true if the source is match
     */
    protected static boolean isValidTemperature(String source) {
        return source.matches("[0-9]{4}\\.[0-9]{2}K");
    }

    @Override
    public String toString() {
        return value;
    }

    public float toFloat() {
        String s = this.value;
        if (this.type == Type.Temperature) {
            return Float.valueOf(s.substring(0, s.length() - 1));
        } else if (this.type == Type.Humidity) {
            return Float.valueOf(s.substring(0, s.length() - 1));
        } else {
            throw new UnsupportedOperationException("Unsupport " + this.type + " to float");
        }
    }


    public Type getType() {
        return type;
    }

    public Date toDate() {
        if (this.type == Type.TimeStamp) {
            return Common.dateFormatter.parse(this.value, new ParsePosition(0));
        } else {
            throw new UnsupportedOperationException("Unsupport " + this.type + " to float");
        }
    }

    public enum Type {
        GET,
        SET,
        DOWNLOAD,
        FILE,
        REGISTER_GATEWAY,

        Temperature,
        Humidity,
        Identifier,
        TimeStamp,
        Otp,

        Undefined
    }
}
