package com.pbutkus.chirper.service;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.auth.UserInfo;
import com.auth0.net.Request;
import com.auth0.net.SignUpRequest;
import com.auth0.net.TokenRequest;
import com.pbutkus.chirper.model.request.EmailPasswordAuthRequest;
import com.pbutkus.chirper.model.response.EmailPasswordSignUpResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Auth0Service {

    @Value("${AUTH0_DOMAIN}")
    private String DOMAIN;
    @Value("${AUTH0_CLIENT_ID}")
    private String CLIENT_ID;
    @Value("${AUTH0_CLIENT_SECRET}")
    private String CLIENT_SECRET;
    private String API_TOKEN;
    private AuthAPI auth;
    private ManagementAPI mgmt;

    @PostConstruct
    private void postConstruct() throws Auth0Exception {
        auth = AuthAPI.newBuilder(DOMAIN,
                        CLIENT_ID,
                        CLIENT_SECRET)
                .build();

//        TokenRequest tokenRequest = auth.requestToken(String.format("https://%s/api/v2/", DOMAIN));
//        TokenHolder holder = tokenRequest.execute().getBody();
//        API_TOKEN = holder.getAccessToken();
//
//        mgmt = ManagementAPI.newBuilder(DOMAIN,
//                        API_TOKEN)
//                .build();
    }

    public EmailPasswordSignUpResponse emailPasswordSignUp(EmailPasswordAuthRequest emailPasswordAuthRequest) throws Auth0Exception {
        // TODO fix connection
        SignUpRequest req = auth.signUp(emailPasswordAuthRequest.email(),
                emailPasswordAuthRequest.password().toCharArray(),
                "Username-Password-Authentication");
        return new EmailPasswordSignUpResponse(req.execute().getBody().getUserId());
    }

    public TokenHolder emailPasswordAuth(String email, String password) throws Auth0Exception {
        TokenRequest req = auth.login(email, password.toCharArray());
        return req.setScope("offline_access").execute().getBody();
//        return new AuthResponse(holder.getAccessToken(),
//                holder.getIdToken(),
//                holder.getRefreshToken(),
//                holder.getTokenType(),
//                holder.getExpiresIn(),
//                holder.getExpiresAt(),
//                holder.getScope());
    }

    public String refreshAccessToken(String refreshToken) throws Auth0Exception {
        TokenRequest req = auth.renewAuth(refreshToken);
        TokenHolder holder = req.execute().getBody();
        return holder.getAccessToken();
    }

    public String getRefreshToken(String accessToken) throws Auth0Exception {
        return null;
    }

    public UserInfo getUserData(String accessToken) throws Auth0Exception {
        Request<UserInfo> req = auth.userInfo(accessToken);
        return req.execute().getBody();
    }

}
