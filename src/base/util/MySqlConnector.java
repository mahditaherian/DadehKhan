package base.util;

import base.Config;

import java.sql.*;

/**
 * @author Mahdi
 */
public class MySqlConnector {
    private Connection conn;
    private Statement statement;

    public MySqlConnector() {
        conn = null;
        statement = null;
    }

    public void connect() {
        try {
//            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=monty&password=greatsqldb");
            conn = DriverManager.getConnection("jdbc:mysql://"+Config.MYSQL_HOST, Config.MYSQL_USERNAME, Config.MYSQL_PASSWORD);
//            Statement stmt = null;
//            ResultSet rs = null;
//            try {
//                stmt = conn.createStatement();
//                rs = stmt.executeQuery("SELECT foo FROM bar");
//
//                // or alternatively, if you don't know ahead of time that
//                // the query will be a SELECT...
//
//                if (stmt.execute("SELECT foo FROM bar")) {
//                    rs = stmt.getResultSet();
//                }
//
//                // Now do something with the ResultSet ....
//            } catch (SQLException ex) {
//                // handle any errors
//                System.out.println("SQLException: " + ex.getMessage());
//                System.out.println("SQLState: " + ex.getSQLState());
//                System.out.println("VendorError: " + ex.getErrorCode());
//            } finally {
//                if (rs != null) {
//                    try {
//                        rs.close();
//                    } catch (SQLException ignore) {
//                    }
//
//                    rs = null;
//                }
//
//                if (stmt != null) {
//                    try {
//                        stmt.close();
//                    } catch (SQLException ignore) {
//                    }
//
//                    stmt = null;
//                }
//            }
            // Do something with the Connection
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public Statement createStatement() {
        Statement statement = null;
        try {
            if (conn == null || conn.isClosed()) {
                connect();
            }
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            if (statement == null || statement.isClosed()) {
                createStatement();
            }
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void insert() {

    }
}
