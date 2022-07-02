package yanbin.blog;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@RestController
public class WebController {

    public static void main(String[] args) {
        SpringApplication.run(WebController.class, args);
    }

    @GetMapping("/private-api")
    public String privateApi(HttpServletRequest request, @AuthenticationPrincipal DecodedJWT decodedJWT) {
        System.out.println("privateApi decodeJWT:" + decodedJWT);
        System.out.println("privateApi request:" + request);
        System.out.println("privateApi authentication:" + SecurityContextHolder.getContext().getAuthentication());
        return "this is a private api";
    }

    @GetMapping("/public-api")
    public String publicApi(HttpServletRequest request, @AuthenticationPrincipal DecodedJWT decodedJWT) {
        System.out.println("publicApi decodedJWT:" + decodedJWT);
        System.out.println("publicApi request: " + request);
        System.out.println("publicApi authentication: " + SecurityContextHolder.getContext().getAuthentication());
        return "this is a public api";
    }
}
