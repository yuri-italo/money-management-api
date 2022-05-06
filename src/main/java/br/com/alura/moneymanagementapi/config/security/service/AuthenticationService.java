package br.com.alura.moneymanagementapi.config.security.service;

import br.com.alura.moneymanagementapi.model.User;
import br.com.alura.moneymanagementapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByEmail(username);

        if (optional.isPresent())
            return optional.get();

        throw new UsernameNotFoundException("Invalid Credentials.");
    }
}
