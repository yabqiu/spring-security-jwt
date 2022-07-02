package yanbin.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests().antMatchers("/public-api").permitAll()
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(new JwtTokenFilter(), AuthorizationFilter.class);
        return httpSecurity.build();
    }
}
