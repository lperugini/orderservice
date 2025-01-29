package com.sap.orderservice.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class IpFilter extends OncePerRequestFilter {

    // IP autorizzato (l'indirizzo del gateway)
    private static final String GATEWAY_IP = "127.0.0.1"; 

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String remoteAddress = request.getRemoteAddr();
        System.out.println("Request from " + request.getRemoteAddr());

        // Verifica se l'indirizzo IP è autorizzato
        /* if (!GATEWAY_IP.equals(remoteAddress)) {
            // Se non autorizzato, restituisci stato 403 Forbidden
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden: Access denied");
            return;
        } */

        System.out.println("Allowed");

        // Se l'IP è autorizzato, passa al filtro successivo
        filterChain.doFilter(request, response);
    }
}

