package br.com.alura.moneymanagementapi.dto;

import br.com.alura.moneymanagementapi.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private final String email;
    public UserDto(User user) {
        this.email = user.getEmail();
    }

    public static List<UserDto> convertManyToDto(List<User> userList) {
        return userList.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public String getEmail() {
        return email;
    }
}
