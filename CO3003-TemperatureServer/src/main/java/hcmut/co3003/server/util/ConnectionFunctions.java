/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author DuongThaiMinh
 */
public class ConnectionFunctions {
    public static Connection getMemoryConnection() throws SQLException {
        String url = "mem";
        String username = "root";
        String password = "1234";
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
}
