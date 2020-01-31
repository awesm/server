package store.awesm.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import store.awesm.domain.User;
import store.awesm.domain.vo.ResultCode;
import store.awesm.util.EmailUtils;
import store.awesm.domain.vo.HttpResult;
import store.awesm.service.impl.UserDetailsServiceImpl;
import store.awesm.util.JwtOperator;
import store.awesm.util.SimpleTokenUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static store.awesm.domain.vo.HttpResult.build;

/**
 *
 * @author zhishangli
 * @time 16:37 2020-01-18
 */

@RestController
@RequestMapping("api")
public class AuthController {

    @Resource
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Resource
    private JwtOperator jwtOperator;
    @Resource
    private AuthenticationManager manager;

    @RequestMapping("/auth/init")
    public HttpResult init() {
        return build(ResultCode.PLEASE_LOGIN);
    }


    @PostMapping("/auth/register")
    public HttpResult register(@RequestBody User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getPassword());
        try {
            userDetailsServiceImpl.register(user);
            Authentication authenticate = manager.authenticate(token);
            if (!ObjectUtils.isEmpty(authenticate)) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                return build(ResultCode.SUCCESS,
                        jwtOperator.quickGenerateWithUser((User) authenticate.getPrincipal()));
            }
        } catch (Exception ignored) {}
        return build(ResultCode.REGISTER_FAILURE);
    }

    @PostMapping("/auth/forgot-password")
    public HttpResult forgotPassword(@RequestBody Map<String, Object> param, HttpSession session) {
        String emailY = (String) session.getAttribute("email");
        String captchaY = (String) session.getAttribute("captcha");
        if (StringUtils.isEmpty(emailY) || StringUtils.isEmpty(captchaY)) {
            return build(ResultCode.PLS_GET_CAPTCHA);
        }
        String emailX = (String) param.get("email");
        String captchaX = (String) param.get("captcha");
        if(StringUtils.pathEquals(emailX, emailY) && StringUtils.pathEquals(captchaX, captchaY)) {
            session.removeAttribute("captcha");
            String tempToken = SimpleTokenUtils.makeToken();
            session.setAttribute("tempToken", tempToken);
            JSONObject map = new JSONObject();
            map.put("tempToken", tempToken);
            return build(ResultCode.SUCCESS, map);
        }
        return build(ResultCode.FAILURE);
    }

    @PostMapping("/auth/reset-password")
    public HttpResult resetPassword(@RequestBody Map<String, Object> param, HttpSession session) {
        String emailY = (String) session.getAttribute("email");
        String tempTokenY = (String) session.getAttribute("tempToken");
        if (StringUtils.isEmpty(emailY) || StringUtils.isEmpty(tempTokenY)) {
            return build(ResultCode.PLS_GET_VERIFY);
        }
        String emailX = (String) param.get("email");
        String tempTokenX = (String)param.get("tempToken");

        if (StringUtils.pathEquals(emailX, emailY)
                && StringUtils.pathEquals(tempTokenX, tempTokenY)) {
            String password = (String) param.get("password");
            if (userDetailsServiceImpl.updatePasswordByEmail(emailX, password)) {
                session.removeAttribute("tempToken");
                session.removeAttribute("email");
                return build(ResultCode.SUCCESS);
            }
        }
        return build(ResultCode.FAILURE);
    }

    @RequestMapping("/auth/logout")
    public HttpResult logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectUtils.isEmpty(authentication)) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return build(ResultCode.SUCCESS);
        }
        return build(ResultCode.FAILURE);
    }

    @RequestMapping("/auth/email-captcha")
    public HttpResult captcha(@RequestBody Map<String, Object> param, HttpSession session) {
        String email = (String) param.get("email");
        String code = EmailUtils.getRandomCode(6);
        if (EmailUtils.sendEmail(email, code)) {
            session.setAttribute("email", email);
            session.setAttribute("captcha", code);
            return build(ResultCode.SUCCESS);
        }
        return build(ResultCode.SEND_CAPTCHA_FAILED);
    }
}
