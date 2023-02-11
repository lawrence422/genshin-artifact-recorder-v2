package com.genshin.service.impl;

import com.genshin.entity.Artifact;
import com.genshin.entity.ArtifactInterface;
import com.genshin.repository.ArtifactRepository;
import com.genshin.repository.ArtifactSetRepository;
import com.genshin.repository.ArtifactStatsRepository;
import com.genshin.repository.ArtifactTypeRepository;
import com.genshin.service.ArtifactService;
import com.genshin.utils.ArtifactUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtifactServiceImpl implements ArtifactService {
    private Map<String, String> artifactStatsMap = new ConcurrentHashMap<>();
    private Map<String, String> artifactSetMap = new ConcurrentHashMap<>();
    private Map<String, String> artifactTypeMap = new ConcurrentHashMap<>();
    private final ArtifactRepository artifactRepository;

    private final ArtifactStatsRepository artifactStatsRepository;

    private final ArtifactSetRepository artifactSetRepository;

    private final ArtifactTypeRepository artifactTypeRepository;

    @Override
    public int[] insertArtifact(List<List<String>> rawArtifactList) {
        initialMap();
        List<Artifact> artifactList=new ArrayList<>();
        for (List<String> rawArtifact:rawArtifactList) {
            artifactList.add(ArtifactUtils
                    .artifactParser(artifactSetMap, artifactStatsMap, artifactTypeMap,rawArtifact));
        }
        return null;
    }

    private void initialMap() {
        if (artifactSetMap.size() == 0) {
            artifactSetMap = getMap(artifactSetRepository);
        }
        if (artifactStatsMap.size()==0){
            artifactStatsMap=getMap(artifactStatsRepository);
        }
        if (artifactTypeMap.size()==0){
            artifactTypeMap=getMap(artifactTypeRepository);
        }
    }


    private Map<String, String> getMap(JpaRepository<? extends ArtifactInterface,String> jpaRepository) {
        return jpaRepository
                .findAll()
                .stream()
                .collect(
                        Collectors.toMap(ArtifactInterface::getArtifactName,
                                ArtifactInterface::getArtifactAbbrevation)
                );
    }
}
