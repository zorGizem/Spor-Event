package com.mehmetvasfi.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter{
	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header;
		String token;
		String username;

		header = request.getHeader("Authorization");

		if (header == null) {
			filterChain.doFilter(request, response);
			return;
		}
		token = header.substring(7);
		try {
			username = securityService.getUsernameByToken(token);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (userDetails != null && securityService.isTokenExpired(token)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							username, null, userDetails.getAuthorities());

					authentication.setDetails(userDetails);

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (ExpiredJwtException e) {
			System.out.println("Token süresi dolmuştur : " + e.getMessage());
		}
		catch(Exception e) {
			System.out.println("Genel bir hata oluştu : " + e.getMessage());
		}
		filterChain.doFilter(request, response);
	}
}
