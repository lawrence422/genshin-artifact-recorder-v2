package com.genshin.controller;


import com.genshin.service.ArtifactService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SuppressWarnings("rawtypes")
@RequestMapping("/artifact")
public class ArtifactController {
    @Autowired
    private ArtifactService artifactService;

    @PostMapping("/insert")
    public int[] insertArtifact(@RequestBody @NonNull List<List<String>> artifactStringList) {
        System.out.println(artifactStringList);
        return artifactService.insertArtifact(artifactStringList);
    }
}
