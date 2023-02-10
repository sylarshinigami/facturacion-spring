package com.ronald.datajpa.auth.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ronald.datajpa.auth.SimpleGrantedAuthorityMixin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTServiceImpl implements JWTService {

	public static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	public static long EXPIRATION_DATE = 3600000 * 4;

	public static final String TOKEN_PREFIX = "Bearer ";

	public static final String HEADER_STRING = "Authorization";

	@Override
	public String create(Authentication auth) throws JsonProcessingException {
		String username = ((User) auth.getPrincipal()).getUsername();

		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder().setSubject(username).signWith(SECRET_KEY).setClaims(claims)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
				.compact(); // valido
							// 4
							// horas
		return token;
	}

	@Override
	public boolean validate(String token) {
		Claims claims = null;
		try {
			claims = getClaims(resolve(token));
			return true;
		} catch (JwtException | IllegalArgumentException ex) { 
			ex.printStackTrace();
			return false;
		}

	}

	@Override
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	@Override
	public String getUsername(String token) {
		return getClaims(resolve(token)).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) {

		Object roles = getClaims(resolve(token)).get("authorities");
		Collection<? extends GrantedAuthority> authorities = null;
		try {
			authorities = Arrays
					.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
							.readValue(roles.toString(), SimpleGrantedAuthority[].class));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return authorities;

	}

	@Override
	public String resolve(String token) {
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			return token.replace(TOKEN_PREFIX, "");
		}
		return null;
	}

}
