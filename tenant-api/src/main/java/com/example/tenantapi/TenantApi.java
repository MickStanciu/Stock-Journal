package com.example.tenantapi;

import com.example.common.property.PropertiesUtil;
import com.example.tenantapi.configuration.RestEasyConfig;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class TenantApi {

    private static final Logger log = LoggerFactory.getLogger(TenantApi.class);

    public static void main(String[] args) {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties properties = propertiesUtil.getProperties();

        UndertowJaxrsServer server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(RestEasyConfig.class.getName());
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");

        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment)
                .setClassLoader(TenantApi.class.getClassLoader())
                .setContextPath("/rest")
                .addListener(Servlets.listener(Listener.class))
                .setDeploymentName("Undertow RestEasy Weld");

        server.deploy(deploymentInfo);
//        server.addResourcePrefixPath("/",
//                resource(new ClassPathResourceManager(Server.class.getClassLoader()))
//                        .addWelcomeFiles("index.html"));

        Undertow.Builder undertowBuilder = Undertow.builder()
                .addHttpListener(getServerPort(properties), "0.0.0.0");
        server.start(undertowBuilder);
        log.info(generateLogo());
    }

    private static int getServerPort(Properties properties) {
        if (properties.containsKey("server.port")) {
            return Integer.valueOf(properties.getProperty("server.port"));
        }

        return 8080;
    }

    private static String generateLogo() {
        return  "                                                           \n" +
                " __          __                                   _ _ _ _ _ \n" +
                " \\ \\        / /                                  | | | | | |\n" +
                "  \\ \\  /\\  / /_ _ __________ _  __ _  __ _  __ _ | | | | | |\n" +
                "   \\ \\/  \\/ / _` |_  /_  / _` |/ _` |/ _` |/ _`  | | | | | |\n" +
                "    \\  /\\  / (_| |/ / / / (_| | (_| | (_| | (_|  |_|_|_|_|_|\n" +
                "     \\/  \\/ \\__,_/___/___\\__,_|\\__,_|\\__,_|\\__,_ (_|_|_|_|_)\n" +
                "                                                           \n" +
                "                                                           \n";
    }
}
