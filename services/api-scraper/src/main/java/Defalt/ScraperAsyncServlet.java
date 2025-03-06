package Defalt;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(value = "/scraper", asyncSupported = true)
public class ScraperAsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(600000);
        ServletOutputStream out = response.getOutputStream();

        asyncContext.start(() -> {
            Process process = null;
            try {
                ProcessBuilder pb = new ProcessBuilder("xvfb-run","python3", "/api-scraper/scraper.py");
                pb.redirectErrorStream(true);
                process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    out.write((line + "\n").getBytes());
                    out.flush();
                }

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    response.setStatus(HttpServletResponse.SC_OK);  // Code 200 OK
                    out.write(("Script terminé avec code : " + exitCode + "\n").getBytes());
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // Code 500
                    out.write(("Erreur du script avec code de sortie : " + exitCode + "\n").getBytes());
                }
                out.flush();

            } catch (Exception e) {
                try {
                    System.out.println(e);
                    out.write(("{\n" +
                            "  \"message\": \"Erreur lors du scraping ou de l'envoi des données. Veuillez réessayer plus tard.\"}").getBytes());
                    out.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } finally {
                if (process != null) {
                    process.destroy();
                }
                asyncContext.complete();
            }
        });
    }
}



