package com.example.gatewayapi;

import com.example.gatewayapi.configuration.JerseyConfig;
import com.example.gatewayapi.configuration.PropertiesUtil;
import com.example.gatewayapi.filter.TokenFilter;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import java.util.Properties;

import static io.undertow.servlet.Servlets.servlet;


public class GatewayApi {

    private static final Logger log = LoggerFactory.getLogger(GatewayApi.class);
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
                .setClassLoader(GatewayApi.class.getClassLoader())
                .setContextPath("/")
                .addListener(Servlets.listener(Listener.class))
                .addServlets(
                    servlet("jerseyServlet", ServletContainer.class)
                        .setLoadOnStartup(1)
                        .addInitParam("javax.ws.rs.Application", JerseyConfig.class.getName())
                        .addMapping("/api/*")
                )
                .addFilter(new FilterInfo("TokenFilter", TokenFilter.class))
                .addFilterUrlMapping("TokenFilter", "/*", DispatcherType.REQUEST)
                .addFilterUrlMapping("TokenFilter", "/*", DispatcherType.FORWARD)
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
                "   _____       _                                      _____ _____ \n" +
                "  / ____|     | |                               /\\   |  __ \\_   _|\n" +
                " | |  __  __ _| |_ _____      ____ _ _   _     /  \\  | |__) || |  \n" +
                " | | |_ |/ _` | __/ _ \\ \\ /\\ / / _` | | | |   / /\\ \\ |  ___/ | |  \n" +
                " | |__| | (_| | ||  __/\\ V  V / (_| | |_| |  / ____ \\| |    _| |_ \n" +
                "  \\_____|\\__,_|\\__\\___| \\_/\\_/ \\__,_|\\__, | /_/    \\_\\_|   |_____|\n" +
                "                                      __/ |                       \n" +
                "                                     |___/                        \n" +
                "                                                           \n";
    }
}
