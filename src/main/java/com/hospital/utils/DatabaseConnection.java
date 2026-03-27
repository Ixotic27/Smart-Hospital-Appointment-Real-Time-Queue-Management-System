package com.hospital.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Supabase Cloud Connection
    private static final String URL = "jdbc:postgresql://db.vgzpysluefgwyepiuiev.supabase.co:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "GaUgeYe2212";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}