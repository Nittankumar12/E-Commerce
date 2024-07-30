package com.nittan.e_commerce.filter;

import com.nittan.e_commerce.exception.GenericException;
import com.nittan.e_commerce.exception.InvalidTokenException;
import com.nittan.e_commerce.exception.InvalidUserException;
import com.nittan.e_commerce.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * Gateway filter for JWT authentication.
 */
@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Autowired
    RouteValidator routeValidator;

    @Autowired
    JwtUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    /**
     * Constructor to initialize the filter factory.
     */
    public JwtAuthFilter(){
        super(Config.class);
    }

    /**
     * Applies the JWT authentication filter.
     * @param config Configuration object for the filter
     * @return GatewayFilter instance
     */
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange,chain) -> {

            logger.info(" In gateway filter");
            // Check if request needs to be secured
            if(routeValidator.isSecured.test(exchange.getRequest())){
                // Check if Authorization header is present
                logger.info("this needs to be authenticated");
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    logger.error("missing authorization header");
                    throw new InvalidTokenException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                logger.info("Got authorization header");
                // Extract token from Authorization header
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                try{
                    // Validate JWT token using JwtUtil
                    jwtUtil.validateToken(authHeader);
                }
                catch (Exception e){
                    logger.error("Invalid token");
                    throw new InvalidUserException("Unauthorized access");
                }
            }

            return chain.filter(exchange);
        });
    }

    /**
     * Configuration class for the JWT authentication filter.
     */
    public static class Config{

    }
}
