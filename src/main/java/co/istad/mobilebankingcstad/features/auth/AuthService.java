package co.istad.mobilebankingcstad.features.auth;

import co.istad.mobilebankingcstad.features.auth.dto.AuthRequest;
import co.istad.mobilebankingcstad.features.auth.dto.AuthRespone;

public interface AuthService {
    AuthRespone login(AuthRequest authRequest);
    AuthRespone signup(AuthRequest authRequest);
    AuthRespone refreshToken(String refreshToken);
}
