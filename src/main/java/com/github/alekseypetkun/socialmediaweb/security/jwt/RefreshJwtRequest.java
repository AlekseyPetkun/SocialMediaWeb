package com.github.alekseypetkun.socialmediaweb.security.jwt;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {

    @NotEmpty(message = "строка с токеном не может быть пустой!")
    public String refreshToken;

}
