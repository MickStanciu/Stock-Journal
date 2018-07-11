package com.example.gatewayapi.configuration;//package com.example.gatewayapi.configuration;
//
//import com.example.gatewayapi.rest.AccountResource;
//import com.google.inject.Binder;
//import com.google.inject.Key;
//import com.google.inject.Module;
//import com.google.inject.TypeLiteral;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Properties;
//
//public class GuiceConfig implements Module {
//
//    private static final Logger log = LoggerFactory.getLogger(GuiceConfig.class);
//
//    public void configure(Binder binder) {
//        PropertiesUtil propertiesUtil = new PropertiesUtil();
//        Properties props = propertiesUtil.getProperties();
//        for (String key : props.stringPropertyNames()) {
//            String value = props.getProperty(key);
//            binder.bind(Key.get(new TypeLiteral<String>() {}, new ConfigImpl(key))).toInstance(value);
//        }
//
//        //classes
//        binder.bind(AccountResource.class);
//    }
//
//
//}