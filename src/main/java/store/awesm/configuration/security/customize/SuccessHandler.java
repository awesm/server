package store.awesm.configuration.security.customize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import store.awesm.domain.User;
import store.awesm.domain.vo.HttpResult;
import store.awesm.domain.vo.ResultCode;
import store.awesm.util.JwtOperator;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * When authentication success return JWT
 *
 * @author zhishangli
 * @time 14:39 2020-01-21
 */
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {
    
    @Resource
    private JwtOperator jwtOperator;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        User user = (User) authentication.getPrincipal();
        JSONObject jwt = jwtOperator.quickGenerateWithUser(user);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(JSON.toJSONString(HttpResult.build(ResultCode.SUCCESS, jwt)));
        writer.close();
    }
}
