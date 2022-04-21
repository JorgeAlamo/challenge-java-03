package com.business.service.payment.routers;

import com.business.service.payment.core.exceptions.HttpError;
import com.business.service.payment.entities.Servicing;
import com.business.service.payment.entities.Transaction;
import com.business.service.payment.handlers.ServicingHandler;
import com.business.service.payment.handlers.TransactionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
public class RouterConfiguration {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/services",
                    produces = { APPLICATION_JSON_VALUE },
                    method = RequestMethod.GET,
                    beanClass = ServicingHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            summary = "List services",
                            description="List all services in general",
                            operationId = "findAll",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful operation",
                                            content = @Content(
                                                    array = @ArraySchema(
                                                            schema = @Schema(
                                                                    implementation = Servicing.class)))),
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "No content found",
                                            content = @Content(schema = @Schema(hidden = true))),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "Authentication required",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class)))
                            })
            ),
            @RouterOperation(
                    path = "/services/{code}",
                    produces = { APPLICATION_JSON_VALUE },
                    method = RequestMethod.GET,
                    beanClass = ServicingHandler.class,
                    beanMethod = "findByCode",
                    operation = @Operation(
                            summary = "Get service by code",
                            description="Return service referenced by code",
                            operationId = "findByCode",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Servicing.class))),
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "No content found",
                                            content = @Content(schema = @Schema(hidden = true))),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "Authentication required",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class)))
                            },
                            parameters = {
                                    @Parameter(
                                            in = ParameterIn.PATH, name = "code")})
            ),
            @RouterOperation(
                    path = "/services/channel/{channelCod}",
                    produces = { APPLICATION_JSON_VALUE },
                    method = RequestMethod.GET,
                    beanClass = ServicingHandler.class,
                    beanMethod = "findByChannel",
                    operation = @Operation(
                            summary = "List services by channel code",
                            description="List services associated with a channel",
                            operationId = "findByChannel",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful operation",
                                            content = @Content(
                                                    array = @ArraySchema(
                                                            schema = @Schema(
                                                                    implementation = Servicing.class)))),
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "No content found",
                                            content = @Content(schema = @Schema(hidden = true))),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "Authentication required",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class)))
                            },
                            parameters = {
                                    @Parameter(
                                            in = ParameterIn.PATH, name = "channelCod")})
            ),
            @RouterOperation(
                    path = "/services",
                    produces = { APPLICATION_JSON_VALUE },
                    method = RequestMethod.POST,
                    beanClass = ServicingHandler.class,
                    beanMethod = "save",
                    operation = @Operation(
                            summary = "Register new service",
                            description="Create a new service",
                            operationId = "save",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200", description = "Successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Servicing.class))),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "Authentication required",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class)))
                            },
                            requestBody = @RequestBody(
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Servicing.class)))
                    )
            )
    })
    public RouterFunction<ServerResponse> servicingRoutes(ServicingHandler servicingHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/service-payment/services"),
                RouterFunctions
                        .route(GET(""), servicingHandler::findAll)
                        .andRoute(GET("/{code}"), servicingHandler::findByCode)
                        .andRoute(GET("/channel/{channelCod}"), servicingHandler::findByChannel)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), servicingHandler::save));
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/transactions",
                    produces = { APPLICATION_JSON_VALUE },
                    method = RequestMethod.GET,
                    beanClass = TransactionHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            summary = "List transactions",
                            description="List all transactions in general",
                            operationId = "findAll",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful operation",
                                            content = @Content(
                                                    array = @ArraySchema(
                                                            schema = @Schema(
                                                                    implementation = Transaction.class)))),
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "No content found",
                                            content = @Content(schema = @Schema(hidden = true))),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "Authentication required",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class)))
                            })
            ),
            @RouterOperation(
                    path = "/transactions",
                    produces = { APPLICATION_JSON_VALUE },
                    method = RequestMethod.POST,
                    beanClass = TransactionHandler.class,
                    beanMethod = "save",
                    operation = @Operation(
                            summary = "Register new transaction",
                            description="Create a new transaction",
                            operationId = "save",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200", description = "Successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Transaction.class))),
                                    @ApiResponse(
                                            responseCode = "401",
                                            description = "Authentication required",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class))),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = HttpError.class)))
                            },
                            requestBody = @RequestBody(
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Transaction.class)))
                    )
            )
    })
    public RouterFunction<ServerResponse> transactionRoutes(TransactionHandler transactionHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/service-payment/transactions"),
                RouterFunctions
                        .route(GET(""), transactionHandler::findAll)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), transactionHandler::save));
    }

}
