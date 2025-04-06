package com.simplesdental.product.openapi;

import com.simplesdental.product.dto.CreateUserDTO;
import com.simplesdental.product.dto.CredentialDTO;
import com.simplesdental.product.dto.PasswordUserDTO;
import com.simplesdental.product.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Auth")
public interface AuthControllerOpenApi {

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Cria um usuario")
	UserDTO createUser(@RequestBody CreateUserDTO user);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Login")
	String login(@RequestBody CredentialDTO credential);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Retorna o perfil")
	UserDTO context();

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Atualiza a senha")
	UserDTO updatePassword(@RequestBody PasswordUserDTO user);


}
