package com.ronald.datajpa.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

                SessionFlashMapManager flash = new SessionFlashMapManager();
                FlashMap flashMap = new FlashMap();
                flashMap.put("success", "Ha iniciado session con exito: " + authentication.getName());
                flash.saveOutputFlashMap(flashMap, request, response);
                super.onAuthenticationSuccess(request, response, authentication);
                logger.info(authentication.getName() + " ha iniciado session");

    }

    
    
}
