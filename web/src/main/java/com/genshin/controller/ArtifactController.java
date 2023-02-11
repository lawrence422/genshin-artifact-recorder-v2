package com.genshin.controller;

import com.genshin.entity.Artifact;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SuppressWarnings("rawtypes")
@RequestMapping("/artifact")
public class ArtifactController {

    @PostMapping("/insert")
    public List<Integer> artifactParser(@RequestBody List<String> artifactStringList){

    }
}
