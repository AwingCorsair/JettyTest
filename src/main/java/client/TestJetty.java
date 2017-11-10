package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by EML on 2017/11/6.
 */
public class TestJetty {

    private static Logger logger = LoggerFactory.getLogger(TestJetty.class);

    private static int executeTimes = 2;

    private static int poolSize = 8;


    public static void callHttp() {
        BlockingQueue<Runnable> queue = new SynchronousQueue<Runnable>();
        CusThreadFactory factory = new CusThreadFactory("client");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize, 0, TimeUnit.MILLISECONDS
                , queue, factory);
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    logger.error("reject request ");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("fail while rejecting", e);
                }
                executor.execute(r);
            }
        });
        Runnable task = new Runnable() {
            public void run() {
                for (int i = 0; i< executeTimes; i++) {
                    Date start = new Date();
                        try {
                            //Test Jetty 6
//                            String result6 = getJetty6();
//                            logger.error(result6);

                            //Test Jetty 8
                            String result8 = getJetty8();
                            logger.error(result8);

//                            String result9 = getJetty9();
//                            logger.error(result9);
                        }
                        catch (Exception e) {
                            logger.error("fail while sending request", e);
                        }
                        finally {
                            Date end = new Date();
                            long cost = end.getTime()-start.getTime();
                            logger.error("cost= " + cost);
                        }
                }
            }
        };
        for (int i = 0; i < poolSize; i++) {
            executor.execute(task);
        }

    }

    private static Integer temp = 0;

    private static String getJetty6() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("request", temp.toString());
        temp++;
        return sendGet("http://127.0.0.1:8081/jetty6",
                parameters);
    }

    private static String getJetty8() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("request", temp.toString());
        temp++;
        return sendGet("http://127.0.0.1:8080/jetty",
                parameters);
    }

    private static String getJetty9() {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("request", temp.toString());
        temp++;
        return sendGet("http://127.0.0.1:8082/jetty9",
                parameters);
    }


    public static String sendGet(String url, Map<String, String> parameters) {
        String result = "";
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        String params = "";
        try {
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String fullUrl = url + "?" + params;
            java.net.URL connURL = new java.net.URL(fullUrl);
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Close");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            logger.warn("url = " + fullUrl);
            httpConn.connect();

            logger.error("start to connect");

            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            httpConn.disconnect();
        }
        catch (SocketException e) {
            logger.error("-----------------------------------------------");
            logger.error("low resources control running, connection down!");
            logger.error("-----------------------------------------------");
        }
        catch (IOException e) {
            logger.error("catch IO Exception", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        callHttp();
    }

}
