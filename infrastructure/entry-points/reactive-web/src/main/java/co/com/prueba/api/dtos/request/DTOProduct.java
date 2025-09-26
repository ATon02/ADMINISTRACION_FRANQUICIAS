package co.com.prueba.api.dtos.request;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DTOProduct {

    @NotNull(message = "Product name cannot be null")
    @NotBlank(message = "Product name cannot be blank")
    private String name;
    
    @NotNull(message = "Stock cannot be null")
    @Positive(message = "Stock must be greater than 0")
    private Long stock;
    
    @NotNull(message = "Branch ID cannot be null")
    @Positive(message = "Branch ID must be greater than 0")
    private Long branchId;
    
}
