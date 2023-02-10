package com.ronald.datajpa.auth.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ronald.datajpa.auth.service.JWTService;
import com.ronald.datajpa.auth.service.JWTServiceImpl;

import io.jsonwebtoken.Claims;

public class JWTAutherizationFilter extends BasicAuthenticationFilter {

	private JWTService jwtService;

	public JWTAutherizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Claims claims = null;

		UsernamePasswordAuthenticationToken authentication = null;

		String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
		if (!jwtService.validate(header)) {
			chain.doFilter(request, response);
			return;
		}else {
				authentication = new UsernamePasswordAuthenticationToken(
						jwtService.getUsername(header), null, 
						jwtService.getRoles(header));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);

	}

}
