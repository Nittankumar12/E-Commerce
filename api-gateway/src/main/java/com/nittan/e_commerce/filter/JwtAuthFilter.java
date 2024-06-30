package com.nittan.e_commerce.filter;

import com.nittan.e_commerce.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Autowired
    RouteValidator routeValidator;

    @Autowired
    JwtUtil jwtUtil;

//    @Autowired
//    RestTemplate restTemplate;

    public JwtAuthFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange,chain) -> {

            if(routeValidator.isSecured.test(exchange.getRequest())){
                //header contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                try{
                    // REST call to Auth Service
//                    restTemplate.getForObject("http://USER-AUTHENTICATION-SERVICE/auth/validate?token"+authHeader,String.class);
                     jwtUtil.validateToken(authHeader);
                }
                catch (Exception e){
                    throw new RuntimeException("unauthorized access");
                }

            }

            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
