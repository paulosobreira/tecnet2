package servicos;

import org.hsqldb.Server;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


/**
 * @author Paulo Sobreira
 * Criado Em 23:43:22
 */
public class ServletHipersonic extends HttpServlet {
    private Server server = new Server();

    public void init() throws ServletException {
        String dbFile = getServletContext().getRealPath("")+File.separator+"WEB-INF"+File.separator+"hipersonic"+File.separator+"inet2";
        System.out.println("dbFile -> "+dbFile);
        server.setDatabaseName(0, "xdb");
        server.setDatabasePath(0,
            "file:"+dbFile);
        server.setLogWriter(new PrintWriter(System.out));
        server.setErrWriter(new PrintWriter(System.out));
        server.setSilent(false);
        server.start();
        System.out.println("Hipersonic Iniciado.");
        super.init();
    }
    
    public void destroy() {
        server.shutdown();
        System.out.println("Hipersonic Parado.");
        super.destroy();
    }
}
