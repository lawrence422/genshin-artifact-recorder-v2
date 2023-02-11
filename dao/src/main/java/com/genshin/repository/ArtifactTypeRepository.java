package com.genshin.repository;

import com.genshin.entity.ArtifactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactTypeRepository extends JpaRepository<ArtifactType,String> {
}
