package com.nittan.e_commerce.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * Component to validate if a route is secured or not.
 */
@Component
public class RouteValidator {

    // List of open endpoints that do not require authorization
    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/getToken",
            "/auth/validateToken",
            "/auth/getUserById",
            "/eureka"
    );

    /**
     * Predicate to check if a request is secured based on URI.
     */
    Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
