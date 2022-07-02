package yanbin.blog;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<DecodedJWT> optionalDecodedJWT = Optional.ofNullable(request.getHeader("authorization"))
                .filter(s -> s.startsWith("Bearer ")).map(s -> s.substring(7)).map(s -> {
                    try {
                        return JWT.decode(s);
                    } catch (JWTDecodeException ex) {
                        return null;
                    }
                });

        if (optionalDecodedJWT.isPresent()) {
            Authentication authentication = new JwtAuthentication(optionalDecodedJWT.get());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
