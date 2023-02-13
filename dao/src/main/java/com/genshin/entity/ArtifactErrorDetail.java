package com.genshin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ArtifactErrorDetail implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    public ArtifactErrorDetail(List<String> rawArtifact){
        this.rawArtifact=rawArtifact.toString();
    }
    private String rawArtifact;



}
