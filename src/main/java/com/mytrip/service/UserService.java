package com.mytrip.service;

import com.mytrip.entity.UserEntity;
import com.mytrip.pojo.ApplicationUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;

@Service
@Transactional
public class UserService {

    @PersistenceContext
    private EntityManager em;

    /**
     * The Spring Boot authentication manager configured for JWT.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Get <code>UserDetails</code> for authentication details for a specific
     * user.
     *
     * @param username - The user's username. - {@link String}
     * @param password - The user's password. - {@link String}
     * @return The user's detail. - {@link ApplicationUserDetails}
     */
    public ApplicationUserDetails getAuthenticationUserDetails(final String username, final String password) {
        // Perform the security
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (ApplicationUserDetails) authentication.getPrincipal();
    }

    public void updateLastLoginDate(String username) {
        TypedQuery<UserEntity> userEntityTypedQuery = em.createNamedQuery("UserEntity.findByUsername", UserEntity.class);
        userEntityTypedQuery.setParameter("username", username);
        final UserEntity user = userEntityTypedQuery.getSingleResult();
        user.setLastLogin(new Date());
    }
}
