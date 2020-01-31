package store.awesm.configuration.security.customize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import store.awesm.domain.vo.HttpResult;
import store.awesm.domain.vo.ResultCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhishangli
 * @time 14:39 2020-01-21
 */
@Component
public class FailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        String json = JSON.toJSONString(HttpResult.build(ResultCode.LOGIN_FAIL.setMsg(e.getMessage())), SerializerFeature.WriteMapNullValue);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(json);
        writer.close();
    }
}
