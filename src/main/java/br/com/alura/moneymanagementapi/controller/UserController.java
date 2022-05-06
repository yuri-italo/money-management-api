package br.com.alura.moneymanagementapi.controller;

import br.com.alura.moneymanagementapi.form.ExpenseForm;
import br.com.alura.moneymanagementapi.form.UserForm;
import br.com.alura.moneymanagementapi.service.ExpenseService;
import br.com.alura.moneymanagementapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {
        return userService.save(userForm,uriBuilder);
    }
    @GetMapping
    public ResponseEntity<?> listAll(@RequestParam(required = false) String email) {
      return userService.listAll(email);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return userService.getById(id);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody @Valid UserForm userForm) {
        return userService.updateById(id,userForm);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
