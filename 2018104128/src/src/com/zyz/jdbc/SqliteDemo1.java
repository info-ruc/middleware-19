package com.zyz.jdbc;

import com.zyz.util.CloseSqlite;
import com.zyz.util.DBUtil;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SqliteDemo1 {
    public static void main(String[] args) throws SQLException {

        SqlMenu.deleteTable();
        SqlMenu.createTable();

    }
}
