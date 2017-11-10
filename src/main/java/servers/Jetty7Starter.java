package servers;

import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import servlet.JettyServlet;

/**
 * Created by EML on 2017/11/6.
 */
public class Jetty7Starter {

    public void start() {
        Server server = new Server();

        Connector connector;

        String host = "127.0.0.1";
        int port = 8080;

        connector = createConnector(host, port);

        server.setConnectors(new Connector[] {
                connector
        });
        try {

            ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
            handler.setContextPath("/");
            handler.addServlet(new ServletHolder(new JettyServlet()), "/jetty");
            server.setHandler(handler);

            server.start();
            server.join();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

//        ContextHandler handler = new ContextHandler();
//        handler.setContextPath("/");
//        server.setHandler(handler);
//        handler.setClassLoader();
    }

    private Connector createConnector(String host, int port) {
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setHost(host);
        connector.setPort(port);
        initParam(connector);
        return connector;
    }


    private void initParam(AbstractConnector connector) {
        int maxIdleTime = 500;
        int lowResourceMaxIdleTime = 1000;
        int acceptor = 1;
        int maxThreads = 3;
        QueuedThreadPool pool = new QueuedThreadPool();
        pool.setName("jetty");
        pool.setMinThreads(maxThreads);
        pool.setMaxThreads(maxThreads);
        connector.setMaxIdleTime(maxIdleTime);
        connector.setLowResourcesMaxIdleTime(lowResourceMaxIdleTime);
        connector.setAcceptors(acceptor);
        connector.setThreadPool(pool);
    }

    public static void main(String[] args) {
        Jetty7Starter starter = new Jetty7Starter();
        starter.start();
    }

}
