package co.com.prueba.api.dtos.request;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class DTOProduct {

    private String name;
    private Long stock;
    private Long branchId;
    
}
