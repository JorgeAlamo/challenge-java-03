package com.experience.integration.service.payment.core.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Slf4j
@Configuration
@AllArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();

        if (ex instanceof GenericBaseException internalException) {
            exchange.getResponse().setStatusCode(internalException.getStatus());
            DataBuffer dataBuffer = null;

            try {
                dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(
                        new HttpError(internalException.getMessage())));
            } catch (JsonProcessingException e) {
                dataBuffer = bufferFactory.wrap("".getBytes());
            }
            exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);

            return exchange.getResponse().writeWith(Mono.just(dataBuffer));

        }

        if (AuthenticationException.class.isAssignableFrom(ex.getClass())) {
            exchange.getResponse().setStatusCode(UNAUTHORIZED);
            DataBuffer dataBuffer = null;

            try {
                dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(
                        new HttpError("Unauthorized")));
            } catch (JsonProcessingException e) {
                dataBuffer = bufferFactory.wrap("".getBytes());
            }
            exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);

            return exchange.getResponse().writeWith(Mono.just(dataBuffer));

        }

        log.info("ERROR ex = " + ex);
        exchange.getResponse().setStatusCode(INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(TEXT_PLAIN);
        DataBuffer dataBuffer = bufferFactory.wrap("Unknown error".getBytes());

        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

}
