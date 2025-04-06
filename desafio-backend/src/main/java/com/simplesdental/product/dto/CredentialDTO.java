package com.simplesdental.product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialDTO {
	@NotEmpty(message = "Campo email é obrigatório")
	@Size(max = 80, message = "Campo email deve ter no maximo 80 caracteres")
    private String email;
	@NotEmpty(message = "Campo password é obrigatório")
	@Size(max = 80, message = "Campo password deve ter no maximo 80 caracteres")
    private String password;
}


