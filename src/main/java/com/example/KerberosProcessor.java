package com.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.ietf.jgss.*;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

public class KerberosProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        System.out.println("Hello from KerberosProcessor");

        System.setProperty("java.security.krb5.conf", "/opt/webserver/conf/krb5.conf");
        System.setProperty("sun.security.krb5.debug", "true");
        System.setProperty("java.security.auth.login.config", "/opt/webserver/conf/SQLJDBCDriver.conf");

        // The Login Context should match whever is defined in the Login Config listed above.
        LoginContext lc = new LoginContext("SQLJDBCDriver");
        lc.login();
        Subject clientSubject = lc.getSubject();
        byte[] serviceTicket = (byte[]) Subject.doAs(clientSubject, new KerberosTestBase());
        System.out.println("Service ticket generated, JAVA works!");

    }
}

class KerberosTestBase implements PrivilegedExceptionAction {
    public Object run() throws Exception {
        try {
            Oid kerberos5Oid = new Oid("1.2.840.113554.1.2.2");
            GSSManager manager = GSSManager.getInstance();
            // **** Need to add User name of a valid user here in the form of username@domain.suffix ****
            GSSName client = manager.createName("admin/admin@EXAMPLE.COM", GSSName.NT_USER_NAME);
            // **** Need to input the SPN for the SQL Server you want to connect to.  This is just used to get the Service Ticket ****
            // **** This does NOT actually connect to SQL Server. ****
            GSSName service = manager.createName("MSSQLSvc/msql.example.com:1433", null);
            GSSCredential clientCredentials = manager.createCredential(null, 8 * 60 * 60, kerberos5Oid, GSSCredential.INITIATE_ONLY);
            GSSContext gssContext = manager.createContext(service, kerberos5Oid, clientCredentials, GSSContext.DEFAULT_LIFETIME);

            // Added by Microsoft to match what the SQL JDBC Driver is doing
            gssContext.requestCredDeleg(true);
            gssContext.requestMutualAuth(true);
            gssContext.requestInteg(true);

            byte[] serviceTicket = gssContext.initSecContext(new byte[0], 0, 0);
            gssContext.dispose();
            return serviceTicket;
        } catch (Exception ex) {
            throw new PrivilegedActionException(ex);
        }
    }
}
