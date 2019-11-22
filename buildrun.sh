#!/bin/bash
mvn clean package
docker build -f Dockerfile -t kerberos-tomcat-camel .
docker run --rm --privileged --net host -v /tmp:/tmp:z -e KRB5CCNAME=FILE:/tmp/krb5cc_1000 kerberos-tomcat-camel:latest

# --dns=127.0.0.1 --add-host kerberos.example.com:127.0.0.1 --add-host example.com:127.0.0.1 --add-host .example.com:127.0.0.1 -e KRB5_CLIENT_KTNAME=/tmp/krb5.keytab
