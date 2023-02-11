package com.genshin.repository;

import com.genshin.entity.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtifactRepository extends JpaRepository<Artifact,Integer> {

}
