package com.business.service.favorites.routers;

import com.business.service.favorites.core.exceptions.HttpError;
import com.business.service.favorites.entities.Favorite;
import com.business.service.favorites.handlers.FavoriteHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
                    path = "/favorites",
                    produces = { APPLICATION_JSON_VALUE },
                    method = RequestMethod.GET,
                    beanClass = FavoriteHandler.class,
                    beanMethod = "findAll",
                    operation = @Operation(
                            summary = "List favorites",
                            description="List services marked as favorites",
                            operationId = "findAll",
                            security = {
                                    @SecurityRequirement(
                                            name = "bearerToken")
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful operation",
                                            content = @Content(
                                                    array = @ArraySchema(
                                                            schema = @Schema(
                                                                    implementation = Favorite.class)))),
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
                    path = "/favorites",
                    produces = { APPLICATION_JSON_VALUE },
                    method = RequestMethod.POST,
                    beanClass = FavoriteHandler.class,
                    beanMethod = "save",
                    operation = @Operation(
                            summary = "Register new favorite",
                            description="Mark service as favorite",
                            operationId = "save",
                            security = {
                                    @SecurityRequirement(
                                            name = "bearerToken")
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200", description = "Successful operation",
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = Favorite.class))),
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
                                                    implementation = Favorite.class)))
                    )
            )
    })
    public RouterFunction<ServerResponse> favoriteRoutes(FavoriteHandler favoriteHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/favorites"),
                RouterFunctions
                        .route(GET(""), favoriteHandler::findAll)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), favoriteHandler::save));
    }

}
