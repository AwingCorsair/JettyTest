//package servers;
//
//import servlet.JettyServlet;
//import org.mortbay.jetty.AbstractConnector;
//import org.mortbay.jetty.Connector;
//import org.mortbay.jetty.Server;
//import org.mortbay.jetty.nio.SelectChannelConnector;
//import org.mortbay.jetty.servlet.ServletHandler;
//import org.mortbay.jetty.servlet.ServletHolder;
//import org.mortbay.thread.QueuedThreadPool;
//
///**
// * Created by EML on 2017/11/6.
// */
//public class Jetty6Starter {
//
//    public void start() {
//        Server server = new Server();
//
//        Connector connector;
//
//        String host = "127.0.0.1";
//        int port = 8081;
//
//        connector = createConnector(host, port);
//
//        server.setConnectors(new Connector[] {
//                connector
//        });
//        try {
//
//            ServletHandler handler = new ServletHandler();
//            handler.addServletWithMapping(new ServletHolder(new JettyServlet()),"/jetty6");
//            server.setHandler(handler);
//
//            server.start();
//            server.join();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Connector createConnector(String host, int port) {
//        SelectChannelConnector connector = new SelectChannelConnector();
//        connector.setHost(host);
//        connector.setPort(port);
//        initParam(connector);
//        return connector;
//    }
//
//
//    private void initParam(AbstractConnector connector) {
//        int maxIdleTime = 2000;
//        int lowResourceMaxIdleTime = 2000;
//        int acceptor = 1;
//        int maxThreads = 3;
//        int lowResourcesConnections = 1;
//        QueuedThreadPool pool = new QueuedThreadPool();
//        pool.setName("jetty6");
//        pool.setMinThreads(maxThreads);
//        pool.setMaxThreads(maxThreads);
//        connector.setMaxIdleTime(maxIdleTime);
//        connector.setLowResourceMaxIdleTime(lowResourceMaxIdleTime);
//        ((SelectChannelConnector) connector).setLowResourcesConnections(lowResourcesConnections);
//        connector.setAcceptors(acceptor);
//        connector.setThreadPool(pool);
//    }
//
//    public static void main(String[] args) {
//        Jetty6Starter starter = new Jetty6Starter();
//        starter.start();
//    }
//
//}
