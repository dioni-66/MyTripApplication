package com.mytrip.service;

import com.mytrip.entity.AuthorityEntity;
import com.mytrip.entity.UserEntity;
import com.mytrip.pojo.ApplicationUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyTripUserDetailsService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<UserEntity> userEntityTypedQuery = em.createNamedQuery("UserEntity.findByUsername", UserEntity.class);
        userEntityTypedQuery.setParameter("username", username);
        final UserEntity user = userEntityTypedQuery.getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("Wrong login name or password");
        } else {
            ApplicationUserDetails applicationUserDetails = createUserDetailsFromEntity(user);
            return applicationUserDetails;
        }
    }

    private ApplicationUserDetails createUserDetailsFromEntity(final UserEntity user) {
        final ApplicationUserDetails details = new ApplicationUserDetails();
        details.setId(user.getId());
        details.setUsername(user.getUsername());
        details.setEmail(user.getEmail());
        details.setEnabled(user.getEnabled());
        details.setPassword(user.getPassword());
        details.setLastLogin(user.getLastLogin());
        details.setAuthorities(mapToGrantedAuthorities(user.getAuthorities()));
        return details;
    }

    /**
     * Extracts the roles of the user.
     *
     * @param authorities a list of the <code>Authority</code> objects of the user.
     * @return A list of <code>GrantedAuthority</code> objects.
     */
    private List<GrantedAuthority> mapToGrantedAuthorities(final List<AuthorityEntity> authorities) {
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }

}
