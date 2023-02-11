package com.genshin.repository;

import com.genshin.entity.ArtifactStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactStatsRepository extends JpaRepository<ArtifactStats,String> {
}
