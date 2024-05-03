package com.mfg.askfm.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRegisterResponse {

    private String message;
    private int httpStatus;
}
