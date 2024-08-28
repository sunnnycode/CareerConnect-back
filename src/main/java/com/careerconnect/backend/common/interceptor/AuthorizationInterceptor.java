package com.careerconnect.backend.common.interceptor;

import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.error.TokenErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.domain.token.business.TokenBusiness;
import com.careerconnect.backend.db.user.UserRepository; // Import 추가
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
    private final UserRepository userRepository; // UserRepository 주입

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        // WEB, chrome의 경우 GET, POST OPTIONS
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // js, html, png resource를 요청하는 경우 = pass
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // 헤더 토큰 검증
        var accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        var loginId = tokenBusiness.validationAccessToken(accessToken);

        if (loginId != null) {
            var userOptional = userRepository.findByLoginId(loginId); // loginId로 사용자 조회

            if (userOptional.isPresent()) {
                var username = userOptional.get().getUsername(); // username 가져오기
                var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
                requestContext.setAttribute("username", username, RequestAttributes.SCOPE_REQUEST); // username 저장
                return true;
            } else {
                throw new ApiException(ErrorCode.BAD_REQUEST, "사용자를 찾을 수 없습니다.");
            }
        }

        throw new ApiException(ErrorCode.BAD_REQUEST, "인증실패");
    }
}
