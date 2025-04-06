package com.simplesdental.product.controller;


import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.simplesdental.product.exception.NotFoundException;
import com.simplesdental.product.exception.UserRoleException;
import com.simplesdental.product.validation.GenericErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE) // Definindo a ordem de prioridade
public class ExceptionControllerAdvice {
	private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public GenericErrors handlePedidoNotFoundException(NotFoundException ex) {
		log.error(ex.getMessage());
		return new GenericErrors(ex.getMessage());
	}

	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public GenericErrors handleTransactionSystemException(TransactionSystemException ex) {
		log.error(ex.getMessage());
		return new GenericErrors(ex.getMessage());
	}

	@ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	GenericErrors handleAuthenticationException(Exception ex) {
		log.error(ex.getMessage());
		return new GenericErrors(ex.getMessage());
	}

	@ExceptionHandler(InsufficientAuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	GenericErrors handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
		log.error(ex.getMessage());
		return new GenericErrors(ex.getMessage());
	}

	@ExceptionHandler(AccountStatusException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	GenericErrors handleAccountStatusException(AccountStatusException ex) {
		log.error(ex.getMessage());
		return new GenericErrors(ex.getMessage());
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	GenericErrors handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
		log.error(ex.getMessage());
		return new GenericErrors("Usuário inexistente ou senha inválida");
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	GenericErrors handleNoHandlerFoundException(NoHandlerFoundException ex) {
		log.error(ex.getMessage());
		return new GenericErrors(ex.getMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	GenericErrors handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		log.error(ex.getMessage());
		return new GenericErrors("Ocorreu algum erro de consulta, verifique os campos corretamente");
	}

	@ExceptionHandler(UserRoleException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	GenericErrors handleUserRoleExeception(UserRoleException ex) {
		log.error(ex.getMessage());
		return new GenericErrors(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GenericErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getAllErrors().stream().map(erro -> erro.getDefaultMessage())
				.collect(Collectors.toList());
		log.error(errors.toString());
		return new GenericErrors(errors);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	GenericErrors handleIllegalArgumentException(IllegalArgumentException ex) {
		log.error(ex.getMessage());
		return new GenericErrors(ex.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GenericErrors handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

		if (ex.getCause() instanceof ValueInstantiationException && ex.getCause().getMessage().contains("UserRole")) {
			return new GenericErrors(ex.getMessage().substring(102));
		}
		log.error(ex.getMessage());
		return new GenericErrors(
				"Não foi possível ler a mensagem HTTP recebida. Verifique se a estrutura e os dados da requisição estão corretos.");
	}

}
