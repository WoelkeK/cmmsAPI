package pl.medos.cmmsApi.model;

import lombok.*;
import pl.medos.cmmsApi.repository.entity.DepartmentEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    private Long id;
    private Department source;
    private Department destination;
    private Boolean confirmed;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
}
