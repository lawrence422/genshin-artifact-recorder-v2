package com.genshin.utils;

import com.genshin.entity.Artifact;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("rawtypes")
public class ArtifactUtils {

    private ArtifactUtils() {
    }

    @SneakyThrows
    public static Artifact artifactParser(Map<String, String> artifactSetMap, Map<String, String> artifactStatsMap, Map<String, String> artifactTypeMap, List<String> rawArtifact) {
        String cursor = rawArtifact.get(0);
        //parse artifactType
        Class artifactClass = Class.forName(Artifact.class.getName());
        Constructor constructor = artifactClass.getConstructor();
        Object artifact = constructor.newInstance();
        reflectionInjectFromEnum(
                artifactTypeMap,
                cursor,
                artifactClass,
                artifact,
                "setArtifactType",
                String.class
        );

        //detect percentage sign
        final var temp = rawArtifact.get(2);
        Pattern pattern = Pattern.compile(".*%");
        if (pattern.matcher(temp).matches()) {
            cursor = String.format("%s%s", rawArtifact.get(1), "%");
        } else {
            cursor = rawArtifact.get(1);
        }

        //parser artifactMainStats
        reflectionInjectFromEnum(artifactStatsMap, cursor, artifactClass, artifact, "setArtifactMainStats", String.class);

        //parser artifactSubStats
        int index = 4;
        while (index <= 7) {
            cursor = rawArtifact.get(index);
            Pattern patternPercentageStats = Pattern.compile("(.*)\\+(.*)%");
            Matcher percentagePatternMatcher = patternPercentageStats.matcher(cursor);
            Pattern patternStats = Pattern.compile("(.*)\\+(.*)");
            Matcher patternMatcher = patternStats.matcher(cursor);

            if (cursor.contains("+")) {
                String statsMethodName = String.format("%s%d", "setArtifactSubInitialStats", index - 3);
                String statsValueMethodName = String.format("%s%s", statsMethodName, "Value");
                String artifactStats = "";
                double artifactStatsValue = 0.0;
                if (percentagePatternMatcher.find()) {
                    String tempStats=percentagePatternMatcher.group(1);
                    List<String>tempList=new ArrayList<>(List.of("攻击力","防御力","生命值"));
                    if (tempList.contains(tempStats)) {
                        artifactStats = String.format("%s%s",tempStats, "%");
                    }else {
                        artifactStats=tempStats;
                    }
                    artifactStatsValue = Double.parseDouble(percentagePatternMatcher.group(2));

                } else if (patternMatcher.find()) {
                    artifactStats = patternMatcher.group(1);
                    artifactStatsValue = Double.parseDouble(patternMatcher.group(2));

                }
                if (!"".equalsIgnoreCase(artifactStats) && artifactStatsValue != 0.0) {
                    reflectionInjectFromEnum(artifactStatsMap, artifactStats, artifactClass, artifact, statsMethodName, String.class);
                    reflectionInjectByValue(artifactStatsValue, artifactClass, artifact, statsValueMethodName, Double.class);
                }

                index++;
            } else {
                break;
            }
        }

        cursor = rawArtifact.get(index);
        Pattern setPattern = Pattern.compile("(.*):");
        Matcher matcher = setPattern.matcher(cursor);
        if (matcher.find()) {
            String setName = matcher.group(1);
            reflectionInjectFromEnum(artifactSetMap, setName, artifactClass, artifact, "setArtifactSet", String.class);
        }
        return (Artifact) artifact;
    }

    @SneakyThrows
    private static void reflectionInjectFromEnum(Map<String, String> map, String cursor, Class artifactClass, Object artifact, String methodName, Class... inputTypes) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(cursor)) {
                Method method = artifactClass.getMethod(methodName, inputTypes);
                method.invoke(artifact, entry.getValue());
                break;
            }
        }
    }

    @SneakyThrows
    private static void reflectionInjectByValue(Double value, Class artifactClass, Object artifact, String methodName, Class... inputTypes) {
        Method method = artifactClass.getMethod(methodName, inputTypes);
        method.invoke(artifact, value);
    }


}
