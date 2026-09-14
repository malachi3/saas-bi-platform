package com.saas.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * 用于生成、验证和解析 Token
 *
 * @author saas
 */
@Component
public class JwtUtils {

    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    /**
     * 生成 Token
     *
     * @param userId   用户ID
     * @param tenantId 租户ID
     * @return JWT Token
     */
    public String generateToken(Long userId, Long tenantId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("tenantId", tenantId);
        return createToken(claims, userId.toString());
    }

    /**
     * 生成 Token（带额外信息）
     *
     * @param claims  声明信息
     * @param subject 主题（用户标识）
     * @return JWT Token
     */
    public String generateToken(Map<String, Object> claims, String subject) {
        return createToken(claims, subject);
    }

    /**
     * 生成刷新 Token
     *
     * @param userId   用户ID
     * @param tenantId 租户ID
     * @return 刷新 Token
     */
    public String generateRefreshToken(Long userId, Long tenantId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("tenantId", tenantId);
        claims.put("type", "refresh");
        return createToken(claims, userId.toString(), refreshExpiration);
    }

    /**
     * 创建 Token
     *
     * @param claims  声明信息
     * @param subject 主题
     * @return JWT Token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return createToken(claims, subject, expiration);
    }

    /**
     * 创建 Token
     *
     * @param claims     声明信息
     * @param subject    主题
     * @param expiration 过期时间（毫秒）
     * @return JWT Token
     */
    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 验证 Token
     *
     * @param token JWT Token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            log.error("无效的JWT签名: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("无效的JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token已过期: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token格式不支持: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims为空: {}", e.getMessage());
        }
        return false;
    }

    /**
     * 从 Token 中获取用户ID
     *
     * @param token JWT Token
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        Object userId = claims.get("userId");
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }
        if (userId instanceof Long) {
            return (Long) userId;
        }
        if (userId instanceof String) {
            return Long.parseLong((String) userId);
        }
        return null;
    }

    /**
     * 从 Token 中获取租户ID
     *
     * @param token JWT Token
     * @return 租户ID
     */
    public Long getTenantIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        Object tenantId = claims.get("tenantId");
        if (tenantId instanceof Integer) {
            return ((Integer) tenantId).longValue();
        }
        if (tenantId instanceof Long) {
            return (Long) tenantId;
        }
        if (tenantId instanceof String) {
            return Long.parseLong((String) tenantId);
        }
        return null;
    }

    /**
     * 从 Token 中获取用户名
     *
     * @param token JWT Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 获取 Token 的过期时间
     *
     * @param token JWT Token
     * @return 过期时间
     */
    public Date getExpirationFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    /**
     * 判断 Token 是否过期
     *
     * @param token JWT Token
     * @return 是否过期
     */
    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationFromToken(token);
        return expiration != null && expiration.before(new Date());
    }

    /**
     * 从 Token 中获取声明信息
     *
     * @param token JWT Token
     * @return 声明信息
     */
    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("解析JWT token失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取签名密钥
     *
     * @return 签名密钥
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
