//package com.example.tenant;
//
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
//import org.wildfly.swarm.Swarm;
//import org.wildfly.swarm.jaxrs.JAXRSArchive;
//import org.wildfly.swarm.topology.TopologyArchive;
//
//import java.net.URL;
//
//public class Main {
//
//    public static void main(String[] args) throws Exception {
//        URL stageConfig = Main.class.getClassLoader().getResource("project-stages.yml");
//        Swarm swarm = new Swarm().withConfig(stageConfig);
//        swarm.start();
//
//        JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
//        archive.addPackage(Main.class.getPackage());
//        archive.addAsWebInfResource(new ClassLoaderAsset("META-INF.bk/persistence.xml", Main.class.getClassLoader()), "classes/META-INF.bk/persistence.xml");
//
//        // advertise service
//        archive.as(TopologyArchive.class).advertise(
//                swarm.stageConfig()
//                        .resolve("service.catalog.service-name")
//                        .getValue()
//        );
//        swarm.deploy(archive);
//    }
//}
