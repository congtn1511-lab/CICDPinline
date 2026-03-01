package org.example.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cglib.core.Local;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService{
    private final JwtProvider provider;
    private final StringRedisTemplate template;

    public LoginServiceImpl(JwtProvider provider, StringRedisTemplate template) {
        this.provider = provider;

        this.template = template;
    }

    @Override
    public AuthModel login(UserModel userModel) {
        AuthModel authModel = new AuthModel();
        LocalDateTime time = LocalDateTime.now();
        authModel.setRefresh(provider.generateRefreshToken(mapToClaims(userModel.getUsername()), time));
        authModel.setAccess(provider.generateAccessToken(mapToClaims(userModel.getUsername()), time));
        template.opsForValue().set(userModel.getUsername(), authModel.refresh);
        return authModel;
    }

    @Override
    public AuthModel refresh(String token) {
        AuthModel authModel = new AuthModel();
        return authModel;
    }

    private Claims mapToClaims(String userName){
        Claims claims =  Jwts.claims();
        claims.put("user", userName);
        return claims;
    }
}
