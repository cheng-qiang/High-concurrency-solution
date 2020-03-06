package com.chen.concurrency.config.web.resolver;

import com.chen.concurrency.model.MiaoshaUser;
import com.chen.concurrency.service.MiaoshaUserService;
import com.chen.concurrency.vo.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 程强
 * @date 2020年03月06日 13:33
 * @Description:
 * supportsParameter此方法用于判断若控制器方法中有MiaoshaUser.class类型的参数返回true，执行resolveArgument方法！
 * resolveArgument方法的返回的就是控制器参数类型
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> aClass = methodParameter.getParameterType();
        return aClass == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(Constants.COOKIE_TOKEN_NAME);
        String cookieToken = getCookieValue(request,Constants.COOKIE_TOKEN_NAME);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return miaoshaUserService.getByToken(response,token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieTokenName) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieTokenName)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
