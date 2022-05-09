package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.form.UserForm;
import br.com.alura.moneymanagementapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

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
        return userService.save(userForm,uriBuilder);
    }
    @GetMapping
    @Operation(summary = "Get a list of users.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> listAll(@RequestParam(required = false) String email) {
      return userService.listAll(email);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get an user by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return userService.getById(id);
    }
    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more user fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody @Valid UserForm userForm) {
        return userService.updateById(id,userForm);
    }
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete an user by id.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
