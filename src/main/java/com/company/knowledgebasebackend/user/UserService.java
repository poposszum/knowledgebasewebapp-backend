package com.company.knowledgebasebackend.user;

import com.company.knowledgebasebackend.auth.JwtTokenProvider;
import com.company.knowledgebasebackend.auth.PasswordResetKey;
import com.company.knowledgebasebackend.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Contains all the user related services.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException("User not found with email: " + email);

        return new UserPrincipal(userEntity);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    /**
     * Finds the reset key by iterating through all the users in the database. (lame)
     * @param resetKey
     * @return
     */
    public PasswordResetKey findResetKey(String resetKey) {
        List<UserEntity> userEntityList = userRepository.findAll();

        for (UserEntity user : userEntityList) {
            if (user.getPasswordResetKey() != null && user.getPasswordResetKey().getResetKey().equals(resetKey)) {
                return user.getPasswordResetKey();
            }
        }

        return null;
    }

    /**
     * Finds the user which has the given reset key by iterating through all the users in the database. (lame either)
     * criteria builder !!!
     * native mongo querry
     */
    public UserEntity findUserByResetKey(String resetKey) {
        List<UserEntity> userEntityList = userRepository.findAll();

        for (UserEntity user : userEntityList) {
            if (user.getPasswordResetKey() != null && user.getPasswordResetKey().getResetKey().equals(resetKey)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Cuts out the jwt token from the authorization header.
     * @param request
     * @return
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * Returns that one user, which owns the given jwt token.
     * @param request
     * @return
     * @throws UserException
     */
    public UserEntity getUserFromJwt(HttpServletRequest request) throws UserException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                return findByEmail(tokenProvider.getEmailFromJWT(jwt));
            }
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
        return null;
    }
}

