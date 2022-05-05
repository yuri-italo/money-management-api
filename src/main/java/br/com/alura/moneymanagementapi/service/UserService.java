package br.com.alura.moneymanagementapi.service;

import br.com.alura.moneymanagementapi.dto.UserDto;
import br.com.alura.moneymanagementapi.form.UserForm;
import br.com.alura.moneymanagementapi.model.User;
import br.com.alura.moneymanagementapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> save(UserForm userForm, UriComponentsBuilder uriBuilder) {
        Optional<User> optional = userRepository.findByEmail(userForm.getEmail());

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");

        userForm.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));

        User user = new User();
        BeanUtils.copyProperties(userForm,user);

        userRepository.save(user);

        return ResponseEntity.created(uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri()).body(new UserDto(user));
    }
}
