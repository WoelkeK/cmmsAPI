package pl.medos.cmmsApi.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class MaintenenceEntity {
    @Id
    Long id;
    int amount;
    @Enumerated(EnumType.ORDINAL)
    MaintanacePeriodType maintanacePeriodType;
}
