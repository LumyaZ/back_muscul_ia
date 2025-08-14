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
     * @throws ServletException Servlet exception / Exception Servlet
     * @throws IOException IO exception / Exception IO
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        
        logger.info("🔍 === JWT FILTER CALLED ===");
        logger.info("URL: {}", request.getRequestURL());
        logger.info("Method: {}", request.getMethod());
        logger.info("Authorization Header: {}", authHeader);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.info("❌ No Bearer token found, continuing without authentication");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            logger.info("✅ JWT token extracted: {}...", jwt.substring(0, Math.min(20, jwt.length())));
            
            final String userEmail = jwtService.extractEmail(jwt);
            logger.info("✅ Email extracted from JWT: {}", userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.info("✅ Loading user details for email: {}", userEmail);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                logger.info("✅ User details loaded: {}", userDetails.getUsername());
                logger.info("✅ User authorities: {}", userDetails.getAuthorities());
                
                if (jwtService.validateToken(jwt)) {
                    logger.info("✅ JWT token is valid");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("✅ Authentication set in SecurityContext");
                    logger.info("✅ SecurityContext Authentication: {}", SecurityContextHolder.getContext().getAuthentication());
                    logger.info("✅ SecurityContext Authentication Principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                    logger.info("✅ SecurityContext Authentication isAuthenticated: {}", SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
                } else {
                    logger.error("❌ JWT token validation failed");
                }
            } else {
                if (userEmail == null) {
                    logger.error("❌ Could not extract email from JWT");
                }
                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    logger.info("ℹ️ Authentication already exists in SecurityContext");
                    logger.info("ℹ️ Existing Authentication: {}", SecurityContextHolder.getContext().getAuthentication());
                    logger.info("ℹ️ Existing Authentication Principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                    logger.info("ℹ️ Existing Authentication isAuthenticated: {}", SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
                }
            }
        } catch (Exception e) {
            logger.error("❌ Error processing JWT token: {}", e.getMessage(), e);
        }

        logger.info("🔄 Continuing with filter chain");
        logger.info("🔄 Final SecurityContext Authentication: {}", SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
    }
} 