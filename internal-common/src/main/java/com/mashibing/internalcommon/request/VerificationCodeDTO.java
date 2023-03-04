package com.mashibing.internalcommon.request;

import lombok.Data;

@Data
public class VerificationCodeDTO {
    public String passengerPhone;
    public String verificationCode;
}
