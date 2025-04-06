package com.simplesdental.product.dto;

import com.simplesdental.product.enums.UserRole;
import com.simplesdental.product.model.User;
import com.simplesdental.product.validation.ValueOfEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{

    private Long id;

    @NotEmpty(message = "Campo name é obrigatório")
    @Size(max = 80, message = "Campo name deve ter no maximo 80 caracteres")
    private String name;

    @NotEmpty(message = "Campo email é obrigatório")
    @Size(max = 80, message = "Campo email deve ter no maximo 80 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "O email fornecido não é válido")
    private String email;

    @ValueOfEnum(enumClass = UserRole.class, message = "Valores permitidos 'ADMIN' OU 'USER'")
    private UserRole role = UserRole.user;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
