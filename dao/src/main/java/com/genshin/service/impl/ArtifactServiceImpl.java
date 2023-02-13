package com.genshin.service.impl;

import com.genshin.entity.Artifact;
import com.genshin.entity.ArtifactErrorDetail;
import com.genshin.entity.ArtifactInterface;
import com.genshin.repository.ArtifactErrorDetailRepository;
import com.genshin.repository.ArtifactRepository;
import com.genshin.repository.ArtifactSetRepository;
import com.genshin.repository.ArtifactStatsRepository;
import com.genshin.repository.ArtifactTypeRepository;
import com.genshin.service.ArtifactService;
import com.genshin.utils.ArtifactUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ArtifactServiceImpl implements ArtifactService {
    private Map<String, String> artifactStatsMap = new ConcurrentHashMap<>();
    private Map<String, String> artifactSetMap = new ConcurrentHashMap<>();
    private Map<String, String> artifactTypeMap = new ConcurrentHashMap<>();
    private final ArtifactRepository artifactRepository;

    private final ArtifactStatsRepository artifactStatsRepository;

    private final ArtifactSetRepository artifactSetRepository;

    private final ArtifactTypeRepository artifactTypeRepository;

    private final ArtifactErrorDetailRepository artifactErrorDetailRepository;



    @Override
    public int[] insertArtifact(List<List<String>> rawArtifactList) {
        initialMap();
        List<Artifact> artifactList = new ArrayList<>();
        int[] parserResult = new int[rawArtifactList.size()];
        int index = 0;
        for (List<String> rawArtifact : rawArtifactList) {
            try {
                Artifact artifact = ArtifactUtils.artifactParser(artifactSetMap, artifactStatsMap, artifactTypeMap, rawArtifact);
                artifactList.add(artifact);
                parserResult[index]++;
            } catch (Exception exception) {
                log.error("Parse Error:" + rawArtifact.toString());
                artifactErrorDetailRepository.save(new ArtifactErrorDetail(rawArtifact));
            }
            index++;
        }
//        System.out.println(artifactList);
        artifactRepository.saveAll(artifactList);
        return parserResult;
    }

    private void initialMap() {
        if (artifactSetMap.size() == 0) {
            artifactSetMap = getMap(artifactSetRepository);
        }
        if (artifactStatsMap.size() == 0) {
            artifactStatsMap = getMap(artifactStatsRepository);
        }
        if (artifactTypeMap.size() == 0) {
            artifactTypeMap = getMap(artifactTypeRepository);
        }
    }


    private Map<String, String> getMap(JpaRepository<? extends ArtifactInterface, String> jpaRepository) {
        return jpaRepository
                .findAll()
                .stream()
                .collect(
                        Collectors.toMap(ArtifactInterface::getArtifactName,
                                ArtifactInterface::getArtifactAbbrevation)
                );
    }
}
