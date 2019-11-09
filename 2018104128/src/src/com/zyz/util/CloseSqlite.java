package com.zyz.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseSqlite {
    public static void close(Connection conn, Statement stmt){
        try {

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
