package com.experience.integration.service.payment.routers;

import com.experience.integration.service.payment.handlers.PaymentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> paymentRoutes(PaymentHandler paymentHandler) {
        return RouterFunctions.nest(RequestPredicates.path("/payments"),
                RouterFunctions
                        .route(GET("/services/channel/{code}"), paymentHandler::findByChannel)
                        .andRoute(POST("").and(contentType(APPLICATION_JSON)), paymentHandler::execute));
    }

}
