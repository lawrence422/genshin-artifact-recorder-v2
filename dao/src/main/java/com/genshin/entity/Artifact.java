package com.genshin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "artifact")
public class Artifact implements Serializable {

    public Artifact(String artifactSet, String artifactType, String artifactMainStats, String artifactSubInitialStats1, double artifactSubInitialStats1Value, String artifactSubInitialStats2, double artifactSubInitialStats2Value, String artifactSubInitialStats3, double artifactSubInitialStats3Value, String artifactSubInitialStats4, double artifactSubInitialStats4Value) {
        this.artifactSet = artifactSet;
        this.artifactType = artifactType;
        this.artifactMainStats = artifactMainStats;
        this.artifactSubInitialStats1 = artifactSubInitialStats1;
        this.artifactSubInitialStats1Value = artifactSubInitialStats1Value;
        this.artifactSubInitialStats2 = artifactSubInitialStats2;
        this.artifactSubInitialStats2Value = artifactSubInitialStats2Value;
        this.artifactSubInitialStats3 = artifactSubInitialStats3;
        this.artifactSubInitialStats3Value = artifactSubInitialStats3Value;
        this.artifactSubInitialStats4 = artifactSubInitialStats4;
        this.artifactSubInitialStats4Value = artifactSubInitialStats4Value;
    }

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
