package com.example.tenant.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Singleton
public class WildflyUtil {

    private static final String JBOSS_BIND_ADDRESS = "jboss.bind.address";
    private static final String SWARM_BIND_ADDRESS = "swarm.bind.address";
    private static final String JBOSS_SOCKET_BINDING_PORT_OFFSET = "jboss.socket.binding.port-offset";
    private static final String SWARM_PORT_OFFSET = "swarm.port.offset";

    private String hostName = "localhost";
    private int hostPort = 8080;
    private int hostSecurePort = 8443;

    @PostConstruct
    public void init() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName http = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");
        ObjectName ws = new ObjectName("jboss.ws", "service", "ServerConfig");

        hostName = (String) mBeanServer.getAttribute(http,"boundAddress");
        hostPort = (int) mBeanServer.getAttribute(http,"boundPort");
        hostSecurePort = (int) mBeanServer.getAttribute(ws, "WebServiceSecurePort");
    }

    public String getHostName() {
        return hostName;
    }

    public int getHostPort() {
        return hostPort;
    }

    public int getHostSecurePort() {
        return hostSecurePort;
    }
}
