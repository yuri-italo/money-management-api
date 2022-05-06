package br.com.alura.moneymanagementapi.dto;

import br.com.alura.moneymanagementapi.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private final Long id;
    private final String email;
    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

    public static List<UserDto> convertManyToDto(List<User> userList) {
        return userList.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
