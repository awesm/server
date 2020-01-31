package store.awesm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import store.awesm.domain.Privilege;
import store.awesm.repository.PrivilegeRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *
 * @author zhishangli
 * @time 20:13 2020-01-21
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    private HashMap<String, Collection<ConfigAttribute>> map;

    private void loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        // 查询到权限表所有信息
        List<Privilege> privileges = privilegeRepository.findAll();

        for(Privilege privilege : privileges) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(privilege.getUrl());
            /*
             *此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。
             *此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
             */
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(privilege.getUrl(), array);
        }

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (null == map) loadResourceDefine();
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (String s : map.keySet()) {
            resUrl = s;
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
