package co.com.prueba.r2dbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchProductDTO {
    @Column("branch_name")
    private String branchName;
    
    @Column("product_id")
    private Long productId;
    
    @Column("product_name")
    private String productName;
    
    @Column("product_stock")
    private Long productStock;
}