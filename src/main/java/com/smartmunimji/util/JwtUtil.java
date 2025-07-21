package com.smartmunimji.util;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.smartmunimji.entities.Admin;
import com.smartmunimji.entities.Customer;
import com.smartmunimji.entities.Seller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	@Value("${jwt.token.expiration.millis}")
	private long jwtExpiration;

	@Value("${jwt.token.secret}")
	private String jwtSecret;

	private Key jwtKey;

	@PostConstruct
	public void init() {
		jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

//	public String createToken(Authentication authentication) {
//		Admin admin = (Admin) authentication.getPrincipal();
//	    String subject = String.valueOf(admin.getId());
//
//	    Date now = new Date();
//	    Date expiryDate = new Date(now.getTime() + jwtExpiration);
//
//	    return Jwts.builder()
//	            .setSubject(subject)
//	            .setIssuedAt(now)
//	            .setExpiration(expiryDate)
//	            .signWith(jwtKey, SignatureAlgorithm.HS256)
//	            .compact();
//	}

	public String createToken(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		String userId = null;

		if (principal instanceof Customer) {
			userId = String.valueOf(((Customer) principal).getId());
		} else if (principal instanceof Admin) {
			userId = String.valueOf(((Admin) principal).getId());
		} else if (principal instanceof Seller) {
			userId = String.valueOf(((Seller) principal).getId());
		} else {
			userId = principal.toString();
		}

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpiration);

		return Jwts.builder().setSubject(userId).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(jwtKey, SignatureAlgorithm.HS256).compact();
	}

	public Authentication validateToken(String token) {
		JwtParser parser = Jwts.parserBuilder().setSigningKey(jwtKey).build();

		Claims claims = parser.parseClaimsJws(token).getBody();

//		String adminId = claims.getSubject();
		String userId = claims.getSubject();

		return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
	}

	public String extractUserId(String token) {
		return Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody().getSubject();
	}
}