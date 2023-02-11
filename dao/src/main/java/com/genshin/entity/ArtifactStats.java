package com.genshin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "artifact_stats_type_enum")
public class ArtifactStats implements Serializable, ArtifactInterface{
    @Id
    @Column(name = "artifact_stats_type_abbrevation")
    private String artifactStatsTypeAbbrevation;

    @Column(name = "artifact_stats_type_name")
    private String artifactStatsTypeName;

    @Override
    public String getArtifactName() {
        return artifactStatsTypeName;
    }

    @Override
    public String getArtifactAbbrevation() {
        return artifactStatsTypeAbbrevation;
    }
}
