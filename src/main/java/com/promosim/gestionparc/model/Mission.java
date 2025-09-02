package com.promosim.gestionparc.model;

import java.time.LocalDate;

import com.promosim.gestionparc.enums.MissionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "missions")
@Data
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = true)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = true)
    private Driver driver;

    private String name;

    private String destination;
    private String departureLocation;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private String missionType;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MissionStatus status = MissionStatus.ONGOING;

    @Column(length = 1000)
    private String tasks;

}
