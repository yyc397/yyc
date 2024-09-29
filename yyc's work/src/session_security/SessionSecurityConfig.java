import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/secureSession")
public class SessionSecurityConfig extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = "exampleSessionId123";

        // 创建一个安全的Cookie
        Cookie sessionCookie = new Cookie("JSESSIONID", sessionId);
        sessionCookie.setHttpOnly(true);  // 防止JavaScript访问
        sessionCookie.setSecure(true);    // 仅在HTTPS下传输
        sessionCookie.setSameSite("Strict"); // 防止CSRF攻击

        // 将Cookie添加到响应中
        response.addCookie(sessionCookie);

    }
}