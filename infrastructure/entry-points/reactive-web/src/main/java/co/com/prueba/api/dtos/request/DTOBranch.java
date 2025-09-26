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
public class DTOBranch {

    @NotBlank(message = "Branch name cannot be blank")
    private String name;
    
    @NotNull(message = "Franchise ID cannot be null")
    @Positive(message = "Franchise ID must be greater than 0")
    private Long franchiseId;
    
}
