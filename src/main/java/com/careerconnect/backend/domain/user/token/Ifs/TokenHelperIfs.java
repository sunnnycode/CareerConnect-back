package com.careerconnect.backend.domain.user.token.Ifs;


import com.careerconnect.backend.domain.user.token.dto.TokenDto;

import java.util.Map;

public interface TokenHelperIfs {

    TokenDto issueAccessToken(Map<String, Object> data);

    TokenDto issueRefreshToken(Map<String, Object> data);

    Map<String, Object> validationTokenWithThrow(String token);


}
