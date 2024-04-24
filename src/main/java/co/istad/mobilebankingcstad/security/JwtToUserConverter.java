package co.istad.mobilebankingcstad.security;

import co.istad.mobilebankingcstad.domain.User;
import co.istad.mobilebankingcstad.features.user.UserRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Data
@RequiredArgsConstructor
@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final  UserRepository userRepository;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        User user = userRepository.findByEmail(source.getSubject()).orElseThrow(()->
                new IllegalArgumentException("User not found"));

        UserDetail userDetail = new UserDetail();
        userDetail.setUser(user);

        return new UsernamePasswordAuthenticationToken(
                userDetail,
                "",
                userDetail.getAuthorities());
    }
}
