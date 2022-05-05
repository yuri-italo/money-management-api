package br.com.alura.moneymanagementapi.dto;

import br.com.alura.moneymanagementapi.model.User;

public class UserDto {
    private final String email;
    public UserDto(User user) {
        this.email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }
}
