package com.example.gatewayapi;

import com.example.gatewayapi.configuration.PropertiesUtil;
import com.example.gatewayapi.configuration.RestEasyConfig;
import com.example.gatewayapi.filter.TokenFilter;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.FilterInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.servlet.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.util.Properties;

public class GatewayApi {

    private static final Logger log = LoggerFactory.getLogger(GatewayApi.class);

    public static void main(String[] args) {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties properties = propertiesUtil.getProperties();

        UndertowJaxrsServer server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(RestEasyConfig.class.getName());
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");

        //?
//        ServletInfo servletInfo = new ServletInfo("dispatcher", HttpServletDispatcher.class);
//        servletInfo.addInitParam("javax.ws.rs.Application", RestEasyConfig.class.getName());


        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment)
                .setClassLoader(GatewayApi.class.getClassLoader())
                .setContextPath("/")
                .addFilter(new FilterInfo("TokenFilter", TokenFilter.class))
                .addFilterUrlMapping("TokenFilter", "/*", DispatcherType.REQUEST)
                .addFilterUrlMapping("TokenFilter", "/*", DispatcherType.FORWARD)
                .addListener(Servlets.listener(Listener.class))
//                .addServlet(servletInfo)
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
