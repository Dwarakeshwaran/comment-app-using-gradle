package commentapp.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class CommentAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        System.out.println("CommentAppInitializer Started in Docker Container");

        AnnotationConfigWebApplicationContext webContainer = new AnnotationConfigWebApplicationContext();
        webContainer.register(CommentAppServletConfig.class);

        DispatcherServlet servlet = new DispatcherServlet(webContainer);
        ServletRegistration.Dynamic customServlet = servletContext.addServlet("commentAppServlet", servlet);

        customServlet.addMapping("/CommentApp/*");
        customServlet.setLoadOnStartup(1);


    }
}
