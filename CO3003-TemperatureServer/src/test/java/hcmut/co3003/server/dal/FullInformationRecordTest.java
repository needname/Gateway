/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.dal;

import hcmut.co3003.server.util.Common;
import hcmut.co3003.server.util.Parser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FullInformationRecordTest {

    @BeforeMethod
    public void setUp() throws Exception {
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    void mainTest() throws Exception {
        String request = "SET foo 03/28/2018-10:04:30+0700 0300.17K 0.2345%";
        FullInformationRecord record = new FullInformationRecord(request);

        System.out.println("Record = " + record);
        String recordString = record.toRecord();
        System.out.println("Formatted record = " + recordString);

        System.out.println(Common.enum2dict(Parser.parse(recordString)));
        FullInformationRecord temp = new FullInformationRecord(recordString);
        System.out.println(temp);

        System.out.println("Compare 2 record: " + record.equals(temp));

    }
}