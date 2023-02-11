package com.genshin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "artifact")
public class Artifact implements Serializable {
    @Id
    @Column(name = "artifact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artifactId;
    @Column(name = "artifact_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp artifactDate;

    @Column(name = "artifact_set")
    private String artifactSet;
    @Column(name = "artifact_type")
    private String artifactType;

    @Column(name = "artifact_main_stats")
    private String artifactMainStats;

    @Column(name = "artifact_sub_initial_stats_1")
    private String artifactSubInitialStats1;

    @Column(name = "artifact_sub_initial_stats_1_value")
    private double artifactSubInitialStats1Value;

    @Column(name = "artifact_sub_initial_stats_2")
    private String artifactSubInitialStats2;

    @Column(name = "artifact_sub_initial_stats_2_value")
    private double artifactSubInitialStats2Value;

    @Column(name = "artifact_sub_initial_stats_3")
    private String artifactSubInitialStats3;

    @Column(name = "artifact_sub_initial_stats_3_value")
    private double artifactSubInitialStats3Value;

    @Column(name = "artifact_sub_initial_stats_4")
    private String artifactSubInitialStats4;

    @Column(name = "artifact_sub_initial_stats_4_value")
    private double artifactSubInitialStats4Value;


}
