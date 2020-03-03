import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet",urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext servletContext = getServletContext();

        HttpSession session = request.getSession();

        String navn = request.getParameter("navn");
        String kodeord = request.getParameter("kodeord");

        if (servletContext.getAttribute("brugerMap") == null) {
            Map<String, String> brugerMap = new HashMap<>();

            brugerMap.put("test","test");
            brugerMap.put("admin","1234");

            servletContext.setAttribute("brugerMap", brugerMap);
        }


        if (!((Map<String,String>)servletContext.getAttribute("brugerMap")).containsKey(navn)) {
            //TODO gå til loginside

            request.setAttribute("besked", "Opret dig som bruger");
            request.getRequestDispatcher("WEB-INF/OpretBruger.jsp").forward(request,response);
        }

        if (((Map<String,String>)servletContext.getAttribute("brugerMap")).get(navn).equalsIgnoreCase(kodeord)) {
            //TODO gå til huskelisten

            if (navn.equalsIgnoreCase("admin")) {
                //TODO gå til adminside
                request.getRequestDispatcher("WEB-INF/Admin.jsp").forward(request,response);
            }

            session.setAttribute("besked", "Du er logget ind med navnet " + navn);

            request.getRequestDispatcher("WEB-INF/HuskeListe.jsp").forward(request,response);
        }

        //TODO gå til login dvs. index
        request.setAttribute("besked", "Din kode var forkert, prøv igen");
        request.getRequestDispatcher("index.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
