FROM jboss/wildfly:11.0.0.Final

# create users
RUN /opt/jboss/wildfly/bin/add-user.sh admin secret --silent

# Install jRebel
RUN curl http://dl.zeroturnaround.com/jrebel-stable-nosetup.zip -o /tmp/jrebel.zip
RUN unzip /tmp/jrebel.zip -d /opt/jboss/
ENV REBEL_HOME=/opt/jboss/jrebel
RUN echo "JAVA_OPTS=\"\$JAVA_OPTS -Xms256m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Drebel.remoting_plugin=true -agentpath:$REBEL_HOME/lib/libjrebel64.so\"" >> /opt/jboss/wildfly/bin/standalone.conf

# configure WilfFly
RUN mkdir -p /opt/jboss/wildfly/modules/org/postgres/main
COPY wildfly/modules/org/postgres/main/postgresql-9.4.1208.jre7.jar /opt/jboss/wildfly/modules/org/postgres/main/
COPY wildfly/modules/org/postgres/main/module.xml /opt/jboss/wildfly/modules/org/postgres/main/
COPY wildfly/standalone/configuration/standalone.xml /opt/jboss/wildfly/standalone/configuration/

# Start WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "--debug"]
