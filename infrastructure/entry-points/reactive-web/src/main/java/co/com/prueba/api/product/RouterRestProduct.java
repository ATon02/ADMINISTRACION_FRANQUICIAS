package co.com.prueba.api.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.prueba.api.product.HandlerProduct;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestProduct {
    @Bean
    public RouterFunction<ServerResponse> routerFunctionProduct(HandlerProduct handler) {
        return route(POST("/api/product"), handler::createProduct)
                .andRoute(DELETE("/api/product/{id}"), handler::deleteProduct)
                .andRoute(GET("/api/product/max-product/{franchiseId}"), handler::getProductMaxStock)
                .andRoute(PATCH("/api/product/update-name/{id}"), handler::updateName)
                .andRoute(PATCH("/api/product/update-stock/{id}"), handler::updateStock);
    }
}
