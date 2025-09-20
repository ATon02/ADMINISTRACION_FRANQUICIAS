package co.com.prueba.api.franchise;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestFranchise {
    @Bean
    public RouterFunction<ServerResponse> routerFunctionFranchise(HandlerFranchise handler) {
        return route(POST("/api/franchises"), handler::save)
                .andRoute(PATCH("/api/franchises/update-name/{id}"), handler::updateName);
    }
}
