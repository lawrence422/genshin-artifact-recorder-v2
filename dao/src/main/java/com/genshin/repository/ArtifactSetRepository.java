package com.genshin.repository;

import com.genshin.entity.ArtifactSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactSetRepository extends JpaRepository<ArtifactSet,String> {
}
