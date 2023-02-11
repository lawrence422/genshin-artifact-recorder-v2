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
@Table(name = "artifact_set_type_enum")
public class ArtifactSet implements Serializable, ArtifactInterface {
    @Id
    @Column(name = "artifact_set_type_abbrevation")
    private String artifactSetTypeAbbrevation;

    @Column(name = "artifact_set_type_name")
    private String artifactSetTypeName;

    @Override
    public String getArtifactName() {
        return artifactSetTypeName;
    }

    @Override
    public String getArtifactAbbrevation() {
        return artifactSetTypeAbbrevation;
    }
}
