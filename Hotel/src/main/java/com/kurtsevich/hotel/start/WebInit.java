package com.kurtsevich.hotel.start;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

//public class WebInit implements WebApplicationInitializer {
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
//        webContext.register(WebConfig.class);
//
//        DispatcherServlet servlet = new DispatcherServlet(webContext);
//        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", servlet);
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//
//        dispatcher.setInitParameter("contextClass", webContext.getClass().getName());
//        servletContext.addListener(new ContextLoaderListener(webContext));
//    }
//}
