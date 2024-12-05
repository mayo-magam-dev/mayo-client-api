package com.mayo.client.mayoclientapi.common.interceptor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mayo.client.mayoclientapi.common.annotation.Authenticated;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.common.utils.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Authenticated authenticated = handlerMethod.getMethodAnnotation(Authenticated.class);

            if(authenticated != null) {
                String token = JwtTokenUtils.getBearerToken(request);
                if(token == null) {
                    throw new ApplicationException(
                            ErrorStatus.toErrorStatus("허가 되지 않은 사용자입니다.", 401, LocalDateTime.now())
                    );
                }

                FirebaseToken decodedToken = null;
                try {
                    decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                } catch (FirebaseAuthException e) {
                    throw new ApplicationException(
                            ErrorStatus.toErrorStatus("허가 되지 않은 사용자입니다.", 401, LocalDateTime.now())
                    );
                }
                request.setAttribute("uid", decodedToken.getUid());
            }
        }
        return true;
    }
}
