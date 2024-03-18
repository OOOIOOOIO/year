package com.sh.year.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private CustomErrorCode customErrorCode;
}
