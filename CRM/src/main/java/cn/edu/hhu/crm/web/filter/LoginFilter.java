package cn.edu.hhu.crm.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String path = request.getServletPath();
        if("/login.jsp".equals(path)||"/settings/user/login.do".equals(path))
            chain.doFilter(req,resp);
        else {
            if(request.getSession().getAttribute("user")!=null)
                chain.doFilter(req, resp);
            else
                response.sendRedirect(request.getContextPath()+"/login.jsp");
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
