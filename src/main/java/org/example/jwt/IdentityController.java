package org.example.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class IdentityController {
    private final LoginService service;
    @Value("${jwt.refresh-exp}")
    private int EXPREFRESH;

    public IdentityController(LoginService service) {
        this.service = service;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserModel user, HttpServletResponse response){
        AuthModel authen = service.login(user);
        Cookie cookie = new Cookie("X-AUTHEN", authen.refresh);
        cookie.setMaxAge(EXPREFRESH);
        cookie.setHttpOnly(true );
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(Map.of("accesstoken",authen.access));
    }

    @GetMapping("test")
    public String login(){

        return "asdasdasd";
    }

    @PostMapping("refresh")
    public String refresh(@RequestBody String token){

        return "";
    }
}
