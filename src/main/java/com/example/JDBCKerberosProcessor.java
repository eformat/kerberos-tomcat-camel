package com.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;

public class JDBCKerberosProcessor implements Processor {

    private Context ctx = null;
    private DataSource ds = null;

    public void process(Exchange exchange) throws Exception {
        System.out.println("In JDBCKerberosProcessor");

        // Declare the JDBC objects.

        Statement stmt = null;
        ResultSet rs = null;

        System.setProperty("java.security.krb5.conf", "/opt/webserver/conf/krb5.conf");
        System.setProperty("sun.security.krb5.debug", "true");
        System.setProperty("java.security.auth.login.config", "/opt/webserver/conf/SQLJDBCDriver.conf");

        try {
            ctx = (Context) new InitialContext().lookup("java:comp/env");
            ds = (DataSource) ctx.lookup("jdbc/MySQLServer");
            DatabaseMetaData dbmd = ds.getConnection().getMetaData();

            System.out.println("dbmd:driver version = " + dbmd.getDriverVersion());
            System.out.println("dbmd:driver name = " + dbmd.getDriverName());
            System.out.println("db name = " + dbmd.getDatabaseProductName());
            System.out.println("db ver = " + dbmd.getDatabaseProductVersion());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
