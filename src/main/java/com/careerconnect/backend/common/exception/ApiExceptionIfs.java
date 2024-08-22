package com.careerconnect.backend.common.exception;

import com.careerconnect.backend.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}