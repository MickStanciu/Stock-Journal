package com.example.accountapi;

import com.example.accountapi.configuration.JerseyConfig;
import com.example.accountapi.configuration.PropertiesUtil;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.Properties;

import static io.undertow.servlet.Servlets.servlet;

public class AccountApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApi.class);
    private static Undertow server;

    public static void main(String[] args) throws ServletException {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties properties = propertiesUtil.getProperties();
        int port = getServerPort(properties);
        startContainer(port);
        log.info(generateLogo());
    }

    private static void startContainer(int port) throws ServletException {
        DeploymentInfo deploymentInfo = Servlets.deployment()
                .setClassLoader(AccountApi.class.getClassLoader())
                .setContextPath("/")
                .addListener(Servlets.listener(Listener.class))
                .addServlets(servlet("jerseyServlet", ServletContainer.class)
                    .setLoadOnStartup(1)
                    .addInitParam("javax.ws.rs.Application", JerseyConfig.class.getName())
                    .addMapping("/rest/*")
                )
                .setDeploymentName("Undertow Jersey Weld");

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(deploymentInfo);
        manager.deploy();

        PathHandler path = Handlers.path(Handlers.redirect("/"))
                .addPrefixPath("/", manager.start());

        server = Undertow.builder()
                .addHttpListener(port, "0.0.0.0")
                .setHandler(path)
                .build();
        server.start();
    }

    private static void stopContainer() {
        if (server != null) {
            server.stop();
        }
    }

    private static int getServerPort(Properties properties) {
        if (properties.containsKey("server.port")) {
            return Integer.valueOf(properties.getProperty("server.port"));
        }

        return 8080;
    }

    private static String generateLogo() {
        return  "                                                           \n" +
                "                                    _            _____ _____ \n" +
                "     /\\                            | |     /\\   |  __ \\_   _|\n" +
                "    /  \\   ___ ___ ___  _   _ _ __ | |_   /  \\  | |__) || |  \n" +
                "   / /\\ \\ / __/ __/ _ \\| | | | '_ \\| __| / /\\ \\ |  ___/ | |  \n" +
                "  / ____ \\ (_| (_| (_) | |_| | | | | |_ / ____ \\| |    _| |_ \n" +
                " /_/    \\_\\___\\___\\___/ \\__,_|_| |_|\\__/_/    \\_\\_|   |_____|\n" +
                "                                                             \n" +
                "                                                             ";
    }
}
