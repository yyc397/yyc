import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

// 自定义异常类，用于处理CSRF验证失败的情况
class CSRFException extends ServletException {
    public CSRFException(String message) {
        super(message);
    }
}

@WebServlet("/csrfSecurity")
public class CSRFSecurity extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 生成CSRF Token并存储在会话中
        HttpSession session = request.getSession();
        String csrfToken = UUID.randomUUID().toString();
        session.setAttribute("csrfToken", csrfToken);

        // 将Token作为响应的一部分返回
        response.getWriter().println("CSRF Token: " + csrfToken);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 验证CSRF Token
        HttpSession session = request.getSession();
        String sessionToken = (String) session.getAttribute("csrfToken");
        String requestToken = request.getParameter("csrfToken");

        if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
            throw new CSRFException("Invalid CSRF token");
        }

    }

}