package co.com.prueba.api.dtos.response;

import java.util.List;

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
public class ResponseDataList<T> {

    private int code;
    private List<T> data;

}
