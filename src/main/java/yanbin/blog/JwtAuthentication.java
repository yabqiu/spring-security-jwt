package yanbin.blog;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final DecodedJWT decodedJWT;

    public JwtAuthentication(DecodedJWT decodedJWT) {
        super(null);
        this.decodedJWT = decodedJWT;
        setAuthenticated(decodedJWT.getExpiresAt().after(new Date()));
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(decodedJWT.getClaim("roles").asList(String.class))
                .map(roles -> roles.stream().map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                        .collect(toList()))
                .orElse(emptyList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return decodedJWT;
    }
}
