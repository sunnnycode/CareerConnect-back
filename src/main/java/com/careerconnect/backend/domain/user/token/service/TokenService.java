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


    public TokenDto issueAccessToken(String userName){
        var data = new HashMap<String, Object>();
        data.put("userName", userName);
        return tokenHelperIfs.issueAccessToken(data);

    }

    public TokenDto issueRefreshToken(String useeName){
        var data = new HashMap<String, Object>();
        data.put("userName", useeName);
        return tokenHelperIfs.issueAccessToken(data);

    }

    public String validationToken(String token){
        var map = tokenHelperIfs.validationTokenWithThrow(token);
        var userName = map.get("userName");
        Objects.requireNonNull(userName, () ->{throw new ApiException(ErrorCode.NULL_POINT);});
        return userName.toString();

    }
//
//    public String extractUsername(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.get("username", String.class); // "username" 필드에서 추출
//    }
}