package com.careerconnect.backend.domain.user.token.service;

import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.domain.user.token.Ifs.TokenHelperIfs;
import com.careerconnect.backend.domain.user.token.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * token 에 대한 도메인로직
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    // 토큰 생성
    public TokenDto issueAccessToken(String userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);

    }

    // 토큰 재발행
    public TokenDto issueRefreshToken(String userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);

    }

    // 토큰 검증
    public String validationToken(String token){
        var map = tokenHelperIfs.validationTokenWithThrow(token);
        var userId = map.get("userId");
        Objects.requireNonNull(userId, () ->{throw new ApiException(ErrorCode.NULL_POINT);});
        return userId.toString();

    }

}