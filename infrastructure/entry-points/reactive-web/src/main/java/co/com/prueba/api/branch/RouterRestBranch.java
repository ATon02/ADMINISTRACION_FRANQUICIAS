package co.com.prueba.api.branch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.prueba.api.branch.HandlerBranch;
import co.com.prueba.api.franchise.HandlerFranchise;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestBranch {
    @Bean
    public RouterFunction<ServerResponse> routerFunctionBranch(HandlerBranch handler) {
        return route(POST("/api/branches"), handler::save)
                .andRoute(PATCH("/api/branches/update-name/{id}"), handler::updateName);
    }
}
