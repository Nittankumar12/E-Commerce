package com.nittan.e_commerce.filter;

import com.nittan.e_commerce.exception.GenericException;
import com.nittan.e_commerce.exception.InvalidTokenException;
import com.nittan.e_commerce.exception.InvalidUserException;
import com.nittan.e_commerce.util.JwtUtil;
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

            System.out.println("in gateway filter");
            // Check if request needs to be secured
            if(routeValidator.isSecured.test(exchange.getRequest())){
                // Check if Authorization header is present
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new InvalidTokenException("Missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                System.out.println("got authorization header");
                // Extract token from Authorization header
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                try{
                    // Validate JWT token using JwtUtil
                    jwtUtil.validateToken(authHeader);
                }
                catch (Exception e){
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
