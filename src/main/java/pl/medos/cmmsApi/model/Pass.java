package pl.medos.cmmsApi.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pass {

    private Long id;
    @NotEmpty(message = "Pole nie może być puste!")
    private String name;
    @NotEmpty(message = "Pole nie może być puste!")
    private String plates;
    @NotEmpty(message = "Pole nie może być puste!")
    private String phone;
    @NotEmpty(message = "Pole nie może być puste!")
    private String company;
    private String description;
    private byte[] originalImage;
    private byte[] resizedImage;
}
