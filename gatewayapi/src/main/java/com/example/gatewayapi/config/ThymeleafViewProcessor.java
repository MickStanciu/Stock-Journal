//package com.example.gatewayapi.config;
//
//import org.thymeleaf.TemplateEngine;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.ext.Provider;
//
//@Provider
//public class ThymeleafViewProcessor implements ViewProcessor<String> {
//
//    @Context
//    HttpServletRequest request;
//
//    @Context
//    HttpServletResponse response;
//
//    @Context
//    ServletContext servletContext;
//
//    private TemplateEngine templateEngine;
//
//    public ThymeleafViewProcessor() {
//        TemplateResolver resolver = new ServletContextTemplateResolver();
//        resolver.setPrefix("/WEB-INF/templates/");
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode("HTML5");
//        resolver.setCacheTTLMs(3600000L);
//        TemplateEngine templateEngine = new TemplateEngine();
//        templateEngine.setTemplateResolver(resolver);
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see com.sun.jersey.spi.template.ViewProcessor#resolve(java.lang.String)
//     */
//    @Override
//    public String resolve(final String path) {
//        return path;
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see com.sun.jersey.spi.template.ViewProcessor#writeTo(java.lang.Object,
//     * com.sun.jersey.api.view.Viewable, java.io.OutputStream)
//     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public void writeTo(final String resolvedPath, final Viewable viewable, final OutputStream out) throws IOException {
//        // Commit the status and headers to the HttpServletResponse
//        out.flush();
//        WebContext context = new WebContext(request, servletContext, request.getLocale());
//        Object model = viewable.getModel();
//        if (Map.class.isAssignableFrom(model.getClass())) {
//            context.setVariables((Map<String, ?>) viewable.getModel());
//        }
//        templateEngine.process(viewable.getTemplateName(), context, response.getWriter());
//    }
//}