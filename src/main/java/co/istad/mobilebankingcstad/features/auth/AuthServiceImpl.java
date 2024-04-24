package co.istad.mobilebankingcstad.features.auth;

import co.istad.mobilebankingcstad.features.auth.dto.AuthRequest;
import co.istad.mobilebankingcstad.features.auth.dto.AuthRespone;
import co.istad.mobilebankingcstad.features.auth.dto.RefresTokenRequest;
import co.istad.mobilebankingcstad.security.TokenGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final TokenGenerator tokenGenerator;

    private final JwtEncoder jwtRefreshTokenEncoder;

    private final JwtDecoder jwtRefreshTokenDecoder;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    public AuthRespone login(AuthRequest authRequest) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
        );
        return tokenGenerator.generateTokens(authentication);
    }

    public AuthRespone refreshToken(RefresTokenRequest refresTokenRequest)
    {
       Authentication authentication = jwtAuthenticationProvider.authenticate(
               new BearerTokenAuthenticationToken(refresTokenRequest.refreshToken())
       );
       return tokenGenerator.generateTokens(authentication);
    }


}
