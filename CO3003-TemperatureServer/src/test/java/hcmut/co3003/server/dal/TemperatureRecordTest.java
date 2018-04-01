/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.dal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;

class TemperatureRecordTest {

    @BeforeEach
    void setUp() {
        System.out.println("================\n/>\n");
    }

    @AfterEach
    void tearDown() {
        System.out.println("/>END TC.");
    }


    @Test
    void testConstructor0() throws IOException {
        TemperatureRecord record = new TemperatureRecord("");
        System.out.write(record.getBytes());
    }

    @Test
    void testConstructor1() throws IOException {
        TemperatureRecord record = new TemperatureRecord("Hello");
        System.out.write(record.getBytes());
    }

    @Test
    void testConstructor2() throws IOException {
        TemperatureRecord record = new TemperatureRecord("! 12 Record01 2.3");
        System.out.write(record.getBytes());
    }

    @Test
    void testConstructor3() throws IOException {
        TemperatureRecord record = new TemperatureRecord("! 1234 Record02 4.6565654");
        System.out.write(record.getBytes());
    }

    @Test
    void testConstructor4() throws IOException {
        TemperatureRecord record = new TemperatureRecord("! 1234566467578 RecordTooBig 1.1234234");
        System.out.write(record.getBytes());
    }

    @Test
    void testConstructor5() throws IOException {
        TemperatureRecord record = new TemperatureRecord("! 12 Record01_ 2.3".getBytes());
        System.out.write(record.getBytes());
    }

    @Test
    void testConstructor6() throws IOException {
        TemperatureRecord record = new TemperatureRecord("! 13 Recódấu 2.3");
        System.out.write(record.getBytes());
    }

    @Test
    void testConstructor7() throws IOException {
        TemperatureRecord record = new TemperatureRecord("! 14 Recódấu 2.4".getBytes());
        System.out.write(record.getBytes());
    }

    @Test
    void testConstructor8() throws IOException {
        TemperatureRecord record = new TemperatureRecord("! 15 Recódấu 2.5".getBytes(Charset.forName("UTF-8")));
        System.out.write(record.getBytes());
    }

    @Test
    void testConstructor9() throws IOException {
        TemperatureRecord record = new TemperatureRecord("! 16 Record 2.6 too much param");
        System.out.write(record.getBytes());
    }
}