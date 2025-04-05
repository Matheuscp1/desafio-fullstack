package com.simplesdental.product.swagger.annotations;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
        in = ParameterIn.QUERY,
        name = "page",
        description = "Número da paǵina (0..N)",
        schema = @Schema(type = "interger", defaultValue = "0")
)
@Parameter(
        in = ParameterIn.QUERY,
        name = "size",
        description = "Quantidade de elementos por página",
        schema = @Schema(type = "interger", defaultValue = "10")
)
@Parameter(
        in = ParameterIn.QUERY,
        name = "sort",
        description = "Critério de ordenação: propriedade,(asc|desc)",
        schema = @Schema(type = "array"),
        examples = {
                @ExampleObject("id"),
                @ExampleObject("id,asc"),
                @ExampleObject("id,desc")
        }
)
public @interface PageableParameter {

}
