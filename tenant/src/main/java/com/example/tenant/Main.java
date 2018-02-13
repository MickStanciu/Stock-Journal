//package com.example.tenant;
//
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.wildfly.swarm.Swarm;
//import org.wildfly.swarm.jaxrs.JAXRSArchive;
//
//public class Main {
//
//    public static void main(String[] args) throws Exception {
//        // Instantiate the container
//        Swarm swarm = new Swarm();
//
//        // Create one or more deployments
//        JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
//
//        // Add resource to deployment
////        deployment.addClass(MyResource.class);
//        archive.addPackage(Main.class.getPackage());
//
//        swarm.start();
//        swarm.deploy(archive);
//    }
//}
