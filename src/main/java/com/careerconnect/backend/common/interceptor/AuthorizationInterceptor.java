package com.careerconnect.backend.common.interceptor;

import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.error.TokenErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.domain.user.token.business.TokenBusiness;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;
    //private final CustomUserDetail customUserDetail;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        // WEB, chrome의 경우 GET, POST OPTIONS
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        // js, html, png resource를 요청하는 경우 = pass
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }


        var accessToken = request.getHeader("authorization-token");
        if(accessToken == null){
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

//        CustomUserDetail customUserDetail = tokenBusiness.validationAccessToken(accessToken);
//        if(customUserDetail != null) {
//            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
//            //requestContext.setAttribute("userId", userDetails.getUserId(), RequestAttributes.SCOPE_REQUEST);
//            requestContext.setAttribute("username", customUserDetail.getUsername(), RequestAttributes.SCOPE_REQUEST);
//            requestContext.setAttribute("location", customUserDetail.getLocation(), RequestAttributes.SCOPE_REQUEST);
//            return true;
//        }

        throw new ApiException(ErrorCode.BAD_REQUEST, "인증실패");

    }
}
