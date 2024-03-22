package com.pbutkus.chirper.model.response;

import java.util.Date;

public record AuthResponse(String accessToken,
                           String idToken,
                           String refreshToken,
                           String tokenType,
                           long expiresIn,
                           Date expiresAt,
                           String scope) {
}
