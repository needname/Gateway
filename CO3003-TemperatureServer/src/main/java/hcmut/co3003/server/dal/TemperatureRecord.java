/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.dal;

import java.nio.charset.Charset;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 * @author DuongThaiMinh
 */
public class TemperatureRecord implements Comparable<TemperatureRecord> {

    private static final Logger logger = Logger.getLogger(TemperatureRecord.class.getName());

    private long id;
    private String name;
    private float value;

    public TemperatureRecord(String record) {
        StringTokenizer tokenizer = new StringTokenizer(record);

        try {
            String _key = tokenizer.nextToken();
            if (_key.equals("!") && tokenizer.countTokens() == 3) {
                long _id = Long.valueOf(tokenizer.nextToken());
                String _name = tokenizer.nextToken();
                float _value = Float.valueOf(tokenizer.nextToken());

                this.id = _id;
                this.name = _name;
                this.value = _value;
            } else {
                throw new Exception("Wrong syntax");
            }
        } catch (Exception e) {
            logger.warning(
                    ("Record has wrong syntax "
                            + "\nExpect: ! %i64 %s %f "
                            + "\nFound: " + record
                            + "\n" + e.getMessage())
            );
        }
    }

    public TemperatureRecord(byte[] record) {
        this(new String(record));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int compareTo(TemperatureRecord o) {
        int sComp = name.compareTo(o.name);
        int fComp = Float.compare(this.value, o.value);
        return sComp == 0 ? fComp : sComp;
    }

    /**
     * @return String bytes code. Follow syntax: ! [id] [name] [value]
     */
    public byte[] getBytes() {
        String sId = String.valueOf(id);
        String sValue = String.valueOf(value);
        String record = String.format("! %s %s %s", sId, name, sValue);
        return record.getBytes(Charset.forName("UTF-8"));
    }
}
