package co.com.prueba.model.branchproduct;
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
public class BranchProduct {
    private String branchName;
    private Long productId;
    private String productName;
    private Long productStock;
}
