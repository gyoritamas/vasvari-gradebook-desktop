package org.vasvari.gradebook.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.model.User;
import org.vasvari.gradebook.helpers.PasswordEncoder;
import org.vasvari.gradebook.model.UserRole;
import org.vasvari.gradebook.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE =
            "User with name %s not found";
    private final static String USER_ALREADY_EXISTS_MESSAGE =
            "User with name %s already exists";
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElse(null);
                //.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    }

    public boolean isPasswordCorrect(UserDetails userDetails, String passwordToCheck) {
        String correctPassword = userDetails.getPassword();

        return passwordEncoder.matches(correctPassword, passwordToCheck);
    }

    private boolean hasAuthority(UserDetails userDetails, UserRole userRole) {
        String userRoleName = userRole.name();

        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority(userRoleName));
    }

    public UserRole getUserRole(UserDetails userDetails) {
        for (UserRole ur : UserRole.values()) {
            if (hasAuthority(userDetails, ur)) {
                return ur;
            }
        }
        throw new IllegalStateException("Unknown user role");
    }

    public String registerUser(User user) {
        String username = user.getUsername();
        boolean userExists = userRepo.findByUsername(username).isPresent();

        if (userExists)
            throw new IllegalStateException(String.format(USER_ALREADY_EXISTS_MESSAGE, username));

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "it works";
    }
}
