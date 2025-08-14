package com.example.muscul_ia.config;

import com.example.muscul_ia.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter for processing JWT tokens in HTTP requests.
 * Filtre d'authentification JWT pour traiter les tokens JWT dans les requêtes HTTP.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	/**
	 * Constructor for JWT Authentication Filter.
	 * Constructeur pour le filtre d'authentification JWT.
	 * 
	 * @param jwtService Service for JWT operations / Service pour les opérations JWT
	 * @param userDetailsService Service for loading user details / Service pour charger les détails utilisateur
	 */
	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Process JWT token from Authorization header and set authentication if valid.
	 * Traite le token JWT depuis l'en-tête Authorization et définit l'authentification si valide.
	 * 
	 * @param request HTTP request / Requête HTTP
	 * @param response HTTP response / Réponse HTTP
	 * @param filterChain Filter chain / Chaîne de filtres
	 * @throws ServletException Servlet exception / Exception de configuration
	 * @throws IOException IO exception / Exception IO
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
							   HttpServletResponse response, 
							   FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			if (logger.isDebugEnabled()) {
				logger.debug("No Bearer token found, continuing without authentication");
			}
			filterChain.doFilter(request, response);
			return;
		}

		try {
			final String jwt = authHeader.substring(7);
			if (logger.isDebugEnabled()) {
				logger.debug("JWT token received (redacted)");
			}
			final String userEmail = jwtService.extractEmail(jwt);
			if (logger.isDebugEnabled()) {
				logger.debug("Email extracted from JWT: {}", userEmail);
			}

			if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
				if (jwtService.validateToken(jwt)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);
					if (logger.isDebugEnabled()) {
						logger.debug("Authentication has been set in SecurityContext for {}", userEmail);
					}
				} else {
					logger.warn("JWT token validation failed for {}", userEmail);
				}
			}
		} catch (Exception e) {
			logger.error("Error processing JWT token: {}", e.getMessage());
		}

		filterChain.doFilter(request, response);
	}
} 