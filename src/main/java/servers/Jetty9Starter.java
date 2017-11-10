//package servers;
//
//import servlet.JettyServlet;
//import org.eclipse.jetty.server.*;
//import org.eclipse.jetty.servlet.ServletContextHandler;
//import org.eclipse.jetty.util.thread.QueuedThreadPool;
//
//
///**
// * Created by EML on 2017/11/7.
// */
//public class Jetty9Starter {
//
//    public void start() {
//
//        int maxThreads = 6;
//        QueuedThreadPool pool = new QueuedThreadPool();
//        pool.setName("jetty");
//        pool.setMinThreads(maxThreads);
//        pool.setMaxThreads(maxThreads);
//        Server server = new Server(pool);
//
//        Connector connector;
//
//        String host = "127.0.0.1";
//        int port = 8082;
//
//        connector = createConnector(host, port, server);
//
//        server.setConnectors(new Connector[] {
//                connector
//        });
//        try {
//            ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
//            handler.setContextPath("/");
//            handler.addServlet(JettyServlet.class, "/jetty9");
//            server.setHandler(handler);
//
//
//
//            server.start();
//            server.join();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Connector createConnector(String host, int port, Server server) {
//        ServerConnector connector = new ServerConnector(server);
//        connector.setHost(host);
//        connector.setPort(port);
//        initParam(server);
//        return connector;
//    }
//
//
//    private void initParam(Server server) {
//
//        LowResourceMonitor monitor = new LowResourceMonitor(server);
//
//        monitor.setLowResourcesIdleTimeout(2000);
//        monitor.setMonitorThreads(true);
////        monitor.setMaxLowResourcesTime(2000);
//        server.addBean(monitor);
//    }
//
//    public static void main(String[] args) {
//        Jetty9Starter starter = new Jetty9Starter();
//        starter.start();
//    }
//
//}
