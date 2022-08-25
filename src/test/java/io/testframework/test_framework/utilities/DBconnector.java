package io.testframework.test_framework.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Properties;

@Component
public class DBconnector {

    private final String dbURl;
    private final String user;
    private final String password;

    public DBconnector(@Autowired Environment env) throws SQLException {
        this.dbURl = env.getProperty("spring-test.datasource.url");
        this.password = env.getProperty("spring-test.datasource.password");
        this.user = env.getProperty("spring-test.datasource.username");
    }


    public Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user",user);
        props.setProperty("password",password);

        Connection conn = DriverManager.getConnection(dbURl,props);
        return conn;
    }

    public void updateQuery(Connection conn, String query) throws SQLException{
        Statement st = conn.createStatement();
        st.executeUpdate(query);

    }

    public ResultSet selectQuery(Connection conn, String query) throws SQLException{
        Statement st = conn.createStatement();
        return st.executeQuery(query);
    }

    public void closeConnection(Connection conn) throws SQLException{
        if(!conn.isClosed()){
            conn.close();
        }
    }



}
