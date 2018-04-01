/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.dal;

import hcmut.co3003.server.util.Common;
import hcmut.co3003.server.util.Parser;
import hcmut.co3003.server.util.Token;

import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import static hcmut.co3003.server.util.Token.Type.*;

/**
 * @author DuongThaiMinh
 * @version 1.0.0
 */
public class FullInformationRecord {
    private static final Token.Type[] FORMAT = {Identifier, Temperature, Humidity, TimeStamp};
    private String id = null;
    private float temperature = 0;
    private float humidity = 0;
    private Date timestamp = null;

    public FullInformationRecord(String id, float temperature, float humidity, Date timestamp) {
        this.id = id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.timestamp = timestamp;
    }

    public FullInformationRecord(String record) {
        Enumeration<Token> tokens = Parser.parse(record);
        if (Common.match(tokens,FORMAT)) {
            Dictionary<Token.Type, List<Token>> dict = Common.enum2dict(tokens);
            this.id = dict.get(Identifier).get(0).toString();
            this.temperature = dict.get(Token.Type.Temperature).get(0).toFloat();
            this.humidity = dict.get(Token.Type.Humidity).get(0).toFloat();
            this.timestamp = dict.get(Token.Type.TimeStamp).get(0).toDate();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        String f = "{id:%s, temperature:%s, humidity:%s, timestamp:%s}";
        return String.format(f, id, temperature, humidity, timestamp);
    }

    public String toRecord() {
        String f = "%s %s %04d.%dK %.4f";
        long a = (long) Math.floor(temperature);
        int b = (int) Math.floor((temperature - Math.floor(temperature)) * 100);
        String record = String.format(f, id, Common.dateFormatter.format(timestamp), a, b, humidity) + "%";
        record = record.replace(',', '.');
        return record;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            FullInformationRecord that = (FullInformationRecord) obj;
            return (this.id.equals(that.id))
                    || (this.timestamp.equals(that.timestamp))
                    || (this.temperature == that.temperature)
                    || (this.humidity == that.humidity);
        } catch (Exception e) {
            return false;
        }
    }
}
