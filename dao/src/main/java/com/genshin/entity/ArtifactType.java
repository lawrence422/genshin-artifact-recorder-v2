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
@Table(name = "artifact_type_enum")
public class ArtifactType implements Serializable, ArtifactInterface{
    @Id
    @Column(name = "artifact_type_abbrevation")
    private String artifactTypeAbbrevation;
    @Column(name = "artifact_type_name")
    private String artifactTypeName;

    @Override
    public String getArtifactName() {
        return artifactTypeName;
    }

    @Override
    public String getArtifactAbbrevation() {
        return artifactTypeAbbrevation;
    }
}

