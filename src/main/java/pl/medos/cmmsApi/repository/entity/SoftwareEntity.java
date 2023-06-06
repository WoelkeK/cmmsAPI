package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SOFTWARES")
public class SoftwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String number;
    private String version;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate activateDate;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "hardware_id")
//    private HardwareEntity hardware;

//    @ManyToMany(mappedBy = "software")
//    private Set<HardwareEntity> hardwares = new HashSet<>();
}
