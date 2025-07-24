package com.smartmunimji.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartmunimji.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

		String path = request.getRequestURI();
		if (path.startsWith("/api/admin/login") || path.startsWith("/api/admin/register") ||
		    path.startsWith("/api/customer/login") || path.startsWith("/api/customer/register") ||
		    path.startsWith("/api/seller/login") || path.startsWith("/api/seller/register")) {
		    filterChain.doFilter(request, response);
		    return;
		}

	    String authHeader = request.getHeader("Authorization");
	    boolean hasBearer = authHeader != null && authHeader.startsWith("Bearer");
	    Authentication authentication = null;

	    if (hasBearer) {
	        String token = authHeader.substring(7).trim();
	        authentication = jwtUtil.validateToken(token);
	    }

	    if (authentication != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    }

	    filterChain.doFilter(request, response);
	}
}
