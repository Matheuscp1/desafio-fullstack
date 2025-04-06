package com.simplesdental.product.dto;

import com.simplesdental.product.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUserDTO {


    @NotEmpty(message = "Campo password é obrigatório")
    @Size(max = 80, message = "Campo password deve ter no maximo 80 caracteres")
    private String password;


    public PasswordUserDTO(User user) {
        this.password = user.getPassword();
    }

}
