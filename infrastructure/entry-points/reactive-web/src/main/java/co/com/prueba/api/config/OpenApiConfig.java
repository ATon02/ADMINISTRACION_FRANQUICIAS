package co.com.prueba.api.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.PathParameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import co.com.prueba.api.dtos.request.DTOFranchise;
import co.com.prueba.api.dtos.response.DTOFranchiseResponse;
import co.com.prueba.api.dtos.response.DTOProductResponse;
import co.com.prueba.api.dtos.request.DTOBranch;
import co.com.prueba.api.dtos.response.DTOBranchResponse;
import co.com.prueba.api.dtos.request.DTOProduct;
import co.com.prueba.model.branchproduct.BranchProduct;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi publicApi(OpenApiCustomizer customizer) {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .addOpenApiCustomizer(customizer)
                .build();
    }

    @Bean
    @Primary
    public OpenApiCustomizer customizer() {
        return openApi -> {
            openApi.getComponents();
            
            // FRANCHISE ENDPOINTS
            PathItem franchisePath = new PathItem()
                    .post(new Operation()
                            .operationId("createFranchise")
                            .tags(List.of("Franchise"))
                            .summary("Crea una nueva franquicia")
                            .description("Endpoint para crear una nueva franquicia en el sistema")
                            .requestBody(new RequestBody()
                                    .description("DTO para crear una franquicia")
                                    .required(true)
                                    .content(new Content()
                                            .addMediaType("application/json",
                                                    new io.swagger.v3.oas.models.media.MediaType()
                                                            .schema(new Schema<>().$ref(
                                                                    "#/components/schemas/DTOFranchise")))))
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("Franquicia creada exitosamente")
                                            .content(new Content()
                                                    .addMediaType("application/json",
                                                            new io.swagger.v3.oas.models.media.MediaType()
                                                                    .schema(new Schema<>().$ref(
                                                                            "#/components/schemas/DTOFranchiseResponse")))))
                                    .addApiResponse("400", new ApiResponse()
                                            .description("Error en los datos de entrada"))));

            PathItem franchiseUpdateNamePath = new PathItem()
                    .patch(new Operation()
                            .operationId("updateFranchiseName")
                            .tags(List.of("Franchise"))
                            .summary("Actualiza el nombre de una franquicia")
                            .description("Endpoint para actualizar el nombre de una franquicia existente")
                            .addParametersItem(new PathParameter()
                                    .name("id")
                                    .description("ID de la franquicia a actualizar")
                                    .required(true)
                                    .schema(new IntegerSchema().format("int64")))
                            .addParametersItem(new QueryParameter()
                                    .name("name")
                                    .description("Nuevo nombre para la franquicia")
                                    .required(true)
                                    .schema(new StringSchema()))
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("Nombre de franquicia actualizado exitosamente")
                                            .content(new Content()
                                                    .addMediaType("application/json",
                                                            new io.swagger.v3.oas.models.media.MediaType()
                                                                    .schema(new Schema<>().$ref(
                                                                            "#/components/schemas/DTOFranchiseResponse")))))
                                    .addApiResponse("400", new ApiResponse()
                                            .description("Error en los datos de entrada"))
                                    .addApiResponse("404", new ApiResponse()
                                            .description("Franquicia no encontrada"))));

            openApi.path("/api/franchises", franchisePath);
            openApi.path("/api/franchises/update-name/{id}", franchiseUpdateNamePath);

            // BRANCH ENDPOINTS
            PathItem branchPath = new PathItem()
                    .post(new Operation()
                            .operationId("createBranch")
                            .tags(List.of("Branch"))
                            .summary("Crea una nueva sucursal")
                            .description("Endpoint para crear una nueva sucursal en el sistema")
                            .requestBody(new RequestBody()
                                    .description("DTO para crear una sucursal")
                                    .required(true)
                                    .content(new Content()
                                            .addMediaType("application/json",
                                                    new io.swagger.v3.oas.models.media.MediaType()
                                                            .schema(new Schema<>().$ref(
                                                                    "#/components/schemas/DTOBranch")))))
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("Sucursal creada exitosamente")
                                            .content(new Content()
                                                    .addMediaType("application/json",
                                                            new io.swagger.v3.oas.models.media.MediaType()
                                                                    .schema(new Schema<>().$ref(
                                                                            "#/components/schemas/DTOBranchResponse")))))
                                    .addApiResponse("400", new ApiResponse()
                                            .description("Error en los datos de entrada"))));

            PathItem branchUpdateNamePath = new PathItem()
                    .patch(new Operation()
                            .operationId("updateBranchName")
                            .tags(List.of("Branch"))
                            .summary("Actualiza el nombre de una sucursal")
                            .description("Endpoint para actualizar el nombre de una sucursal existente")
                            .addParametersItem(new PathParameter()
                                    .name("id")
                                    .description("ID de la sucursal a actualizar")
                                    .required(true)
                                    .schema(new IntegerSchema().format("int64")))
                            .addParametersItem(new QueryParameter()
                                    .name("name")
                                    .description("Nuevo nombre para la sucursal")
                                    .required(true)
                                    .schema(new StringSchema()))
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("Nombre de sucursal actualizado exitosamente")
                                            .content(new Content()
                                                    .addMediaType("application/json",
                                                            new io.swagger.v3.oas.models.media.MediaType()
                                                                    .schema(new Schema<>().$ref(
                                                                            "#/components/schemas/DTOBranchResponse")))))
                                    .addApiResponse("400", new ApiResponse()
                                            .description("Error en los datos de entrada"))
                                    .addApiResponse("404", new ApiResponse()
                                            .description("Sucursal no encontrada"))));

            openApi.path("/api/branches", branchPath);
            openApi.path("/api/branches/update-name/{id}", branchUpdateNamePath);

            // PRODUCT ENDPOINTS
            PathItem productPath = new PathItem()
                    .post(new Operation()
                            .summary("Crear un nuevo producto")
                            .description("Crea un nuevo producto en una sucursal específica")
                            .tags(List.of("Products"))
                            .requestBody(new RequestBody()
                                    .required(true)
                                    .content(new Content()
                                            .addMediaType("application/json",
                                                    new io.swagger.v3.oas.models.media.MediaType()
                                                            .schema(new Schema<>().$ref("#/components/schemas/DTOProduct")))))
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("Producto creado exitosamente")
                                            .content(new Content()
                                                    .addMediaType("application/json",
                                                            new io.swagger.v3.oas.models.media.MediaType()
                                                                    .schema(new Schema<>().$ref(
                                                                            "#/components/schemas/DTOProductResponse")))))
                                    .addApiResponse("400", new ApiResponse()
                                            .description("Error en los datos de entrada"))
                                    .addApiResponse("404", new ApiResponse()
                                            .description("Sucursal no encontrada"))));

            PathItem productDeletePath = new PathItem()
                    .delete(new Operation()
                            .summary("Eliminar un producto")
                            .description("Elimina un producto existente por su ID")
                            .tags(List.of("Products"))
                            .addParametersItem(new PathParameter()
                                    .name("id")
                                    .description("ID del producto a eliminar")
                                    .required(true)
                                    .schema(new IntegerSchema().format("int64")))
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("Producto eliminado exitosamente"))
                                    .addApiResponse("404", new ApiResponse()
                                            .description("Producto no encontrado"))));

            PathItem productMaxStockPath = new PathItem()
                    .get(new Operation()
                            .summary("Obtener productos con mayor stock por franquicia")
                            .description("Obtiene el producto con mayor stock de cada sucursal de una franquicia")
                            .tags(List.of("Products"))
                            .addParametersItem(new PathParameter()
                                    .name("franchiseId")
                                    .description("ID de la franquicia")
                                    .required(true)
                                    .schema(new IntegerSchema().format("int64")))
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("Lista de productos con mayor stock por sucursal")
                                            .content(new Content()
                                                    .addMediaType("application/json",
                                                            new io.swagger.v3.oas.models.media.MediaType()
                                                                    .schema(new ArraySchema()
                                                                            .items(new Schema<>().$ref(
                                                                                    "#/components/schemas/BranchProduct"))))))
                                    .addApiResponse("404", new ApiResponse()
                                            .description("Franquicia no encontrada"))));

            PathItem productUpdateNamePath = new PathItem()
                    .patch(new Operation()
                            .summary("Actualizar nombre de producto")
                            .description("Actualiza el nombre de un producto existente")
                            .tags(List.of("Products"))
                            .addParametersItem(new PathParameter()
                                    .name("id")
                                    .description("ID del producto a actualizar")
                                    .required(true)
                                    .schema(new IntegerSchema().format("int64")))
                            .addParametersItem(new QueryParameter()
                                    .name("name")
                                    .description("Nuevo nombre para el producto")
                                    .required(true)
                                    .schema(new StringSchema()))
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("Nombre de producto actualizado exitosamente")
                                            .content(new Content()
                                                    .addMediaType("application/json",
                                                            new io.swagger.v3.oas.models.media.MediaType()
                                                                    .schema(new Schema<>().$ref(
                                                                            "#/components/schemas/DTOProductResponse")))))
                                    .addApiResponse("400", new ApiResponse()
                                            .description("Error en los datos de entrada"))
                                    .addApiResponse("404", new ApiResponse()
                                            .description("Producto no encontrado"))));

            PathItem productUpdateStockPath = new PathItem()
                    .patch(new Operation()
                            .summary("Actualizar stock de producto")
                            .description("Actualiza el stock de un producto existente")
                            .tags(List.of("Products"))
                            .addParametersItem(new PathParameter()
                                    .name("id")
                                    .description("ID del producto a actualizar")
                                    .required(true)
                                    .schema(new IntegerSchema().format("int64")))
                            .addParametersItem(new QueryParameter()
                                    .name("stock")
                                    .description("Nuevo stock para el producto")
                                    .required(true)
                                    .schema(new IntegerSchema().format("int64")))
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("Stock de producto actualizado exitosamente")
                                            .content(new Content()
                                                    .addMediaType("application/json",
                                                            new io.swagger.v3.oas.models.media.MediaType()
                                                                    .schema(new Schema<>().$ref(
                                                                            "#/components/schemas/DTOProductResponse")))))
                                    .addApiResponse("400", new ApiResponse()
                                            .description("Error en los datos de entrada"))
                                    .addApiResponse("404", new ApiResponse()
                                            .description("Producto no encontrado"))));

            openApi.path("/api/product", productPath);
            openApi.path("/api/product/{id}", productDeletePath);
            openApi.path("/api/product/max-product/{franchiseId}", productMaxStockPath);
            openApi.path("/api/product/update-name/{id}", productUpdateNamePath);
            openApi.path("/api/product/update-stock/{id}", productUpdateStockPath);

            // SCHEMAS
            openApi.getComponents()
                    .addSchemas("DTOFranchise", new Schema<DTOFranchise>()
                            .addProperty("name", new StringSchema()
                                    .description("Nombre de la franquicia")
                                    .example("McDonald's Colombia")))
                    .addSchemas("DTOFranchiseResponse", new Schema<DTOFranchiseResponse>()
                            .addProperty("id", new IntegerSchema().format("int64")
                                    .description("ID único de la franquicia")
                                    .example(1L))
                            .addProperty("name", new StringSchema()
                                    .description("Nombre de la franquicia")
                                    .example("McDonald's Colombia")))
                    .addSchemas("DTOBranch", new Schema<DTOBranch>()
                            .addProperty("name", new StringSchema()
                                    .description("Nombre de la sucursal")
                                    .example("Sucursal Centro"))
                            .addProperty("franchiseId", new IntegerSchema().format("int64")
                                    .description("ID de la franquicia a la que pertenece")
                                    .example(1L)))
                    .addSchemas("DTOBranchResponse", new Schema<DTOBranchResponse>()
                            .addProperty("id", new IntegerSchema().format("int64")
                                    .description("ID único de la sucursal")
                                    .example(1L))
                            .addProperty("name", new StringSchema()
                                    .description("Nombre de la sucursal")
                                    .example("Sucursal Centro"))
                            .addProperty("franchiseId", new IntegerSchema().format("int64")
                                    .description("ID de la franquicia a la que pertenece")
                                    .example(1L)))
                    .addSchemas("DTOProduct", new Schema<DTOProduct>()
                            .addProperty("name", new StringSchema()
                                    .description("Nombre del producto")
                                    .example("Big Mac"))
                            .addProperty("stock", new IntegerSchema().format("int64")
                                    .description("Cantidad en stock del producto")
                                    .example(100L))
                            .addProperty("branchId", new IntegerSchema().format("int64")
                                    .description("ID de la sucursal a la que pertenece el producto")
                                    .example(1L)))
                    .addSchemas("DTOProductResponse", new Schema<DTOProductResponse>()
                            .addProperty("id", new IntegerSchema().format("int64")
                                    .description("ID único del producto")
                                    .example(1L))
                            .addProperty("name", new StringSchema()
                                    .description("Nombre del producto")
                                    .example("Big Mac"))
                            .addProperty("stock", new IntegerSchema().format("int64")
                                    .description("Cantidad en stock del producto")
                                    .example(100L)))
                    .addSchemas("BranchProduct", new Schema<BranchProduct>()
                            .description("Modelo de dominio que representa un producto con mayor stock en una sucursal específica. " +
                                       "Optimizado para consultas reactivas con JOIN desde la base de datos")
                            .addProperty("branchName", new StringSchema()
                                    .description("Nombre de la sucursal")
                                    .example("Sucursal Centro"))
                            .addProperty("productId", new IntegerSchema().format("int64")
                                    .description("ID único del producto")
                                    .example(1L))
                            .addProperty("productName", new StringSchema()
                                    .description("Nombre del producto")
                                    .example("Big Mac"))
                            .addProperty("productStock", new IntegerSchema().format("int64")
                                    .description("Cantidad en stock del producto")
                                    .example(100L)));
        };
    }

}

