package org.example.jwt;

import org.springframework.stereotype.Service;

public interface LoginService {
    AuthModel login (UserModel userModel);
    AuthModel refresh(String token);
}
