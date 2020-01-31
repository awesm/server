package store.awesm.configuration.security.customize;

import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;
import store.awesm.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * accept json about user info, and AuthenticationManager will authenticate
 *
 * @author zhishangli
 * @time 14:26 2020-01-21
 */
public class JsonLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String POST = "POST";
    public JsonLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/auth/login", POST));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username;
            String password;

            User user = getUser(request);
            if (ObjectUtils.isEmpty(user)) {
                username = "";
                password = "";
            } else {
                username = user.getUsername();
                password = user.getPassword();
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);
            authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    private User getUser(HttpServletRequest request) throws IOException {
        InputStream is = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF_8));

        StringBuilder sbf = new StringBuilder();
        String temp;
        while ((temp = br.readLine()) != null) {
            sbf.append(temp);
            sbf.append("\r\n");
        }
        String json = sbf.toString().trim();
        return JSON.isValid(json) ? JSON.parseObject(json, User.class) : null;
    }
}
