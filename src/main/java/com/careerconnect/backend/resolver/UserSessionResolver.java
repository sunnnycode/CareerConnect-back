package com.careerconnect.backend.resolver;

import com.careerconnect.backend.common.annotation.UserSession;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {
    // 로그인 한 사용자 정보(APi-info)를 확인하기 위한 클래스
    // 토큰을 넣은 세션, 즉 헤더에서 userId를 가져오고 userId에 맞는 나머지 정보(닉네임 등)를 가져오고자 함
    // 그러나 spring MVC를 사용하지 않으면 세션 사용이 불가하다는 이슈 발생...

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크

        // 1. 어노테이션이 있는지 체크
        var annotation = parameter.hasParameterAnnotation(UserSession.class);

        // 2. 파라미터의 타입 체크
        var parameterType = parameter.getParameterType().equals(User.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // support parameter 에서 true 반환시 여기 실행

        // request context holder에서 찾아오기
        var requestContext = RequestContextHolder.getRequestAttributes();
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        var userEntity = userService.getUserWithThrow(userId.toString());


        // 사용자 정보 셋팅
        return User.builder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .passwordHash(userEntity.getPasswordHash())
                .createdAt(userEntity.getCreatedAt())
                .lastLogin(userEntity.getLastLogin())
                .isActive(userEntity.getIsActive())
                .build()
                ;

    }
}
