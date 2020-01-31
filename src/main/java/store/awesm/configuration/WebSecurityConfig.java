package store.awesm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import store.awesm.configuration.security.customize.FailureHandler;
import store.awesm.configuration.security.customize.SuccessHandler;
import store.awesm.configuration.security.customize.JsonLoginAuthenticationFilter;
import store.awesm.filter.JwtAuthenticationTokenFilter;
import store.awesm.interceptor.MyFilterSecurityInterceptor;
import store.awesm.service.impl.UserDetailsServiceImpl;

import javax.annotation.Resource;

/**
 *
 * @author zhishangli
 * @time 13:15 2020-01-18
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Value("spring.security.antMatchers")
//    private String antMatchers;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    private SuccessHandler successHandler;
    @Resource
    private FailureHandler failureHandler;
    @Resource
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Resource
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsServiceImpl)
            .passwordEncoder(passwordEncoder())
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/login", "/index").permitAll()
                    .antMatchers("/api/auth/**").permitAll()
//                    .antMatchers(antMatchers.split(",")).permitAll()
                    .anyRequest().authenticated()
            .and()
                    .csrf().disable()
                .formLogin()
                    .loginPage("/api/auth/init")
                    .loginProcessingUrl("/api/auth/login")
            .and()
                .logout()
                    .logoutUrl("/api/auth/logout")
                    .logoutSuccessUrl("/")
            .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jsonLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
        ;
        // 禁用缓存
        http.headers().cacheControl();
    }

    @Bean
    public JsonLoginAuthenticationFilter jsonLoginAuthenticationFilter() throws Exception {
        JsonLoginAuthenticationFilter filter = new JsonLoginAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
