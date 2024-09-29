import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
// OWASP ESAPI库
import org.owasp.esapi.ESAPI;

@WebServlet("/xssDefense")
public class XSSDefense extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户输入
        String userInput = request.getParameter("userInput");

        // 使用OWASP ESAPI进行HTML编码，以防止XSS攻击
        String safeInput = ESAPI.encoder().encodeForHTML(userInput);

        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");

        // 输出安全编码后的用户输入
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>XSS Defense Example</title></head>");
            out.println("<body>");
            out.println("<h1>Safe User Input:</h1>");
            out.println("<p>" + safeInput + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}