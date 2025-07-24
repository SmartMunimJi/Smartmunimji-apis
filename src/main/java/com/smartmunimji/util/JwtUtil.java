package com.smartmunimji.util;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.smartmunimji.entities.Admin;
import com.smartmunimji.entities.Customer;
import com.smartmunimji.entities.Seller;
import com.smartmunimji.services.UnifiedUserDetailsService;

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
	
	@Autowired
    private UnifiedUserDetailsService userDetailsService;

	@PostConstruct
	public void init() {
		jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	public String createToken(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		String userEmail;

		if (principal instanceof Admin) {
			userEmail = ((Admin) principal).getEmail();
		} else if (principal instanceof Customer) {
			userEmail = ((Customer) principal).getEmail();
		} else if (principal instanceof Seller) {
			userEmail = ((Seller) principal).getSellersemail();
		} else {
			userEmail = principal.toString();
		}

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpiration);

		return Jwts.builder().setSubject(userEmail).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(jwtKey, SignatureAlgorithm.HS256).compact();
	}

	public Authentication validateToken(String token) {
		JwtParser parser = Jwts.parserBuilder().setSigningKey(jwtKey).build();

		Claims claims = parser.parseClaimsJws(token).getBody();

		String email = claims.getSubject();
//		String adminId = claims.getSubject();
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//		String userId = claims.getSubject();

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
	
	public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(jwtKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }
}