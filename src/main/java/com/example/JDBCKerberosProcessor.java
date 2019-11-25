package com.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.sql.*;

public class JDBCKerberosProcessor implements Processor {

    /* Kerberos */
    private String connectionUrl = "jdbc:sqlserver://localhost;databaseName=model;integratedSecurity=true;authenticationScheme=JavaKerberos";

    /* Local connection username, password */
    //private String connectionUrlLocal = "jdbc:sqlserver://localhost:1433;databaseName=model;user=sa;password=password;";

    public void process(Exchange exchange) throws Exception {
        System.out.println("In JDBCKerberosProcessor");

        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        System.setProperty("java.security.krb5.conf", "/opt/webserver/conf/krb5.conf");
        System.setProperty("sun.security.krb5.debug", "true");
        System.setProperty("java.security.auth.login.config", "/opt/webserver/conf/SQLJDBCDriver.conf");

        try {
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            con = DriverManager.getConnection(connectionUrl);
            DatabaseMetaData dbmd = con.getMetaData();

            System.out.println("dbmd:driver version = " + dbmd.getDriverVersion());
            System.out.println("dbmd:driver name = " + dbmd.getDriverName());
            System.out.println("db name = " + dbmd.getDatabaseProductName());
            System.out.println("db ver = " + dbmd.getDatabaseProductVersion());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
