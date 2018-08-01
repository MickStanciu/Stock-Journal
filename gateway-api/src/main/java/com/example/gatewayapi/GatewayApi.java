package com.example.gatewayapi;

import com.example.gatewayapi.configuration.PropertiesUtil;
import com.example.gatewayapi.configuration.RestEasyConfig;
import com.example.gatewayapi.filter.TokenFilter;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.FilterInfo;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.util.Properties;

import static io.undertow.Handlers.resource;


public class GatewayApi {

    private static final Logger log = LoggerFactory.getLogger(GatewayApi.class);

    public static void main(String[] args) {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties properties = propertiesUtil.getProperties();

        UndertowJaxrsServer server = new UndertowJaxrsServer();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(RestEasyConfig.class.getName());
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment)
                .setClassLoader(GatewayApi.class.getClassLoader())
                .setContextPath("/api")
                .addFilter(new FilterInfo("TokenFilter", TokenFilter.class))
                .addFilterUrlMapping("TokenFilter", "/*", DispatcherType.REQUEST)
                .addFilterUrlMapping("TokenFilter", "/*", DispatcherType.FORWARD)
                .addListener(Servlets.listener(Listener.class))
                .setDeploymentName("Undertow RestEasy Weld");

        server.deploy(deploymentInfo);
        server.addResourcePrefixPath("/index.htm", resource(new ClassPathResourceManager(GatewayApi.class.getClassLoader()))
                        .addWelcomeFiles("webapp/index.htm"));

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
