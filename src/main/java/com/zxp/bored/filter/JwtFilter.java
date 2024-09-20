package com.zxp.bored.filter;

import com.zxp.bored.util.JwtUtil;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * jwt过滤器
 *
 * @description: jwt过滤器
 * @author: zxp
 * @date: 2024/9/16 22:03
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String account = request.getHeader("account");
        if (token != null && account != null) {
//            token = token.substring(7);
            try {
                String parseAccount = jwtUtil.extractUsername(token);
                if (parseAccount != null && parseAccount.equals(account) && jwtUtil.validateToken(token, account)) {
                    SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(account, null, Collections.emptyList())
                    );
                }
                filterChain.doFilter(request, response);
            } catch (SignatureException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT signature");
                response.getWriter().flush();
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT token");
                response.getWriter().flush();
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}