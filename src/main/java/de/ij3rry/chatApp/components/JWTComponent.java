package de.ij3rry.chatApp.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JWTComponent {
    @Value("${jwt.secret}")
    private String secret;

    private static String ROLE = "role";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username,String role) {
        return createToken(username,role);
    }

    private String createToken(String subject, String role) {

        long tenHoursInMillis = TimeUnit.HOURS.toMillis(10);
        long expDate = System.currentTimeMillis() + tenHoursInMillis;

        return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expDate))
                .claim(ROLE, role)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public UsernamePasswordAuthenticationToken validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        if(username.equals(userDetails.getUsername()) && !isTokenExpired(token) && validateRoles(token,userDetails)) {
            List<GrantedAuthority> authority = List.of(new SimpleGrantedAuthority(extractClaim(token, claims -> claims.get(ROLE)).toString()));
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, authority);
        }
        throw new UsernameNotFoundException("Invalid token");
    }

    public Boolean validateRoles(String token, UserDetails userDetails){
        Claims claims = extractAllClaims(token);
        List<String> tokenRoles = (List<String>) claims.get("roles");
        List<String> assignedRoles = userDetails.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).toList();
        /* If any of the roles in the token mismatches with the assigned role then it's a corrupted token */
        for(String tokenRole : tokenRoles){
            if( !assignedRoles.contains(tokenRole) )
                return false;
        }
        return true;
    }


}
