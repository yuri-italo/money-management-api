package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.dto.UserDto;
import br.com.alura.moneymanagementapi.form.UserForm;
import br.com.alura.moneymanagementapi.model.User;
import br.com.alura.moneymanagementapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new user.")
    public ResponseEntity<?> save(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {
        Optional<User> optional = userService.findByEmail(userForm.getEmail());

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");

        User savedUser = userService.save(userForm);

        return ResponseEntity.created(uriBuilder.path("/users/{id}").buildAndExpand(savedUser.getId()).toUri()).body(new UserDto(savedUser));
    }

    @GetMapping
    @Operation(summary = "Get a list of users.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> listAll(@RequestParam(required = false) String email) {
        List<User> list = userService.listAll(email);

        if (list.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.OK).body(UserDto.convertManyToDto(list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an user by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<User> optional = userService.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new UserDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist.");
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more user fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody @Valid UserForm userForm) {
        Optional<User> optional = userService.findById(id);

        if (optional.isPresent()) {
            if (userService.emailAlreadyExists(userForm.getEmail()))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");

            User updatedUser = userService.update(id, userForm);

            return ResponseEntity.status(HttpStatus.OK).body(new UserDto(updatedUser));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist.");
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete an user by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<User> optional = userService.findById(id);

        if (optional.isPresent()) {
            userService.delete(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist.");
    }
}
