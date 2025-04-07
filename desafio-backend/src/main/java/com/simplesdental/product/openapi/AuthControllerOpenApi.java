package com.simplesdental.product.openapi;

import com.simplesdental.product.dto.CreateUserDTO;
import com.simplesdental.product.dto.CredentialDTO;
import com.simplesdental.product.dto.PasswordUserDTO;
import com.simplesdental.product.dto.TokenDTO;
import com.simplesdental.product.dto.UpdateUserDTO;
import com.simplesdental.product.dto.UserDTO;
import com.simplesdental.product.swagger.annotations.PageableParameter;
import com.simplesdental.product.utils.PageableQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name = "bearer")
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
	TokenDTO login(@RequestBody CredentialDTO credential);

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

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Lista os usuários")
	@PageableParameter
	Page<UserDTO> getAllUsers(
			@Parameter(hidden = true) PageableQuery filter);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Lista o usuário por id")
	ResponseEntity<UserDTO> geUserById(@Parameter(description = "Id ", example = "aId", required = true) final Long id);


	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Ok"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Atualiza um usuário")
	UpdateUserDTO updateUser(@Parameter(description = "Id ", example = "aId", required = true) final Long id,
							 @RequestBody UpdateUserDTO user);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "No Content"),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
	})
	@Operation(summary = "Deleta o usuário por id")
	ResponseEntity<Void> deleteUser(@Parameter(description = "Id ", example = "aId", required = true) final Long id);

}
