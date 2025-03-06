package Defalt;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class TomcatServer {
    public static void main(String[] args) {
        int port = 9090;

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector().setProperty("address", "0.0.0.0");

        String baseDir = new File(".").getAbsolutePath();
        tomcat.setBaseDir(baseDir);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        var context = tomcat.addContext(contextPath, docBase);

        Tomcat.addServlet(context, "ScraperAsyncServlet", new ScraperAsyncServlet());
        context.addServletMappingDecoded("/scraper", "ScraperAsyncServlet");

        try {
            // Démarrer Tomcat
            tomcat.start();
            System.out.println("Serveur Tomcat démarré sur http://localhost:" + port);
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
