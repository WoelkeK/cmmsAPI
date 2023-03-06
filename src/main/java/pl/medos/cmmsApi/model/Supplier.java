package pl.medos.cmmsApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    private Long id;
    private String name;
    private String taxNb;
    private Adrress adrress;
    private Contact contact;
 }
