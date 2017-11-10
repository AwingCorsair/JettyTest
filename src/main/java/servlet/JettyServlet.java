package servlet;



import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * Created by EML on 2017/11/6.
 */
public class JettyServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        process(req, res);
    }

    private void process(ServletRequest req, ServletResponse res) {
        try {
            Thread.sleep(2000);
            System.out.println("deal ok");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
