package br.com.alura.moneymanagementapi.service;

import br.com.alura.moneymanagementapi.form.UserForm;
import br.com.alura.moneymanagementapi.model.User;
import br.com.alura.moneymanagementapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserForm userForm) {
        userForm.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));

        User user = new User();
        BeanUtils.copyProperties(userForm,user);

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> listAll(String email) {
        List<User> userList;

        if (email == null)
            userList = userRepository.findAll();
        else
            userList = userRepository.findByEmailContainingIgnoreCase(email);

        return userList;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User update(Long id, UserForm userForm) {
        return updateFields(userRepository.getById(id),userForm);
    }

    private User updateFields(User user, UserForm userForm) {
        user.setEmail(userForm.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));

        return user;
    }

    public boolean emailAlreadyExists(String email) {
        return userRepository.emailAlreadyExists(email);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
