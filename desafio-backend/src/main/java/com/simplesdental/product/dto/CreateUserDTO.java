package com.simplesdental.product.dto;

import com.simplesdental.product.enums.UserRole;
import com.simplesdental.product.model.User;
import com.simplesdental.product.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    @NotEmpty(message = "Campo name é obrigatório")
    @Size(max = 80, message = "Campo name deve ter no maximo 80 caracteres")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "O email fornecido não é válido")
    @NotEmpty(message = "Campo email é obrigatório")
    @Size(max = 80, message = "Campo email deve ter no maximo 80 caracteres")
    @Schema(example = "test@com.br")
    private String email;

    @NotEmpty(message = "Campo password é obrigatório")
    @Size(max = 80, message = "Campo password deve ter no maximo 80 caracteres")
    private String password;

    @ValueOfEnum(enumClass = UserRole.class, message = "Valores permitidos 'admin' OU 'user'")
    private String role;


    public CreateUserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public User toEntitie() {
        return User.builder()
                .name(this.name)
                .password(this.password)
                .email(this.email)
                .role(UserRole.valueOf(this.role))
                .build();
    }
}
