package com.genshin.utils;

import com.genshin.entity.Artifact;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("rawtypes")
public class ArtifactUtils {

    private ArtifactUtils() {
    }

    public static Artifact artifactParser(Map<String, String> artifactSetMap, Map<String, String> artifactStatsMap, Map<String, String> artifactTypeMap, List<String> rawArtifact) {
        String cursor = rawArtifact.get(0);
        //parse artifactType
        Artifact artifact = new Artifact();
        reflectionInjectFromEnum(
                artifactTypeMap,
                cursor,
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
//        for (Map.Entry<String, String> entry : artifactStatsMap.entrySet()) {
//            if (entry.getValue().equalsIgnoreCase(cursor)) {
//                artifact.setArtifactMainStats(entry.getKey());
//                break;
//            }
//        }
//        artifact.setArtifactSubInitialStats1Value();
        reflectionInjectFromEnum(artifactStatsMap, cursor, artifact, "setArtifactMainStats", String.class);

        //
        int index = 4;
        while (index <= 7) {
            Pattern patternPercentageStats = Pattern.compile("(.*)\\+(.*)%");
            Pattern patternStats = Pattern.compile("(.*)\\+(.*)");
            cursor = rawArtifact.get(index);
            if (cursor.contains("+")) {
                String statsMethodName = String.format("%s%d", "setArtifactSubInitialStats", index - 3);
                String statsValueMethodName = String.format("%s%s", statsMethodName, "Value");
                String artifactStats = "";
                double artifactStatsValue = 0.0;
                if (patternPercentageStats.matcher(cursor).matches()) {
                    Matcher matcher = patternPercentageStats.matcher(cursor);
                    artifactStats = String.format("%s%s", matcher.group(1), "%");
                    artifactStatsValue = Double.parseDouble(matcher.group(2));
                } else if (patternStats.matcher(cursor).matches()) {
                    Matcher matcher = patternStats.matcher(cursor);
                    artifactStats = matcher.group(1);
                    artifactStatsValue = Double.parseDouble(matcher.group(2));
                }
                reflectionInjectFromEnum(artifactStatsMap, artifactStats, artifact, statsMethodName, String.class);
                reflectionInjectByValue(artifactStatsValue, artifact, statsValueMethodName, Double.class);

                index++;
            } else {
                break;
            }
        }

        cursor=rawArtifact.get(index);
        Pattern setPattern=Pattern.compile("(.*):");
        if (setPattern.matcher(cursor).matches()){
            Matcher matcher=setPattern.matcher(cursor);
            String setName=matcher.group(1);
            reflectionInjectFromEnum(artifactSetMap,setName,artifact,"setArtifactSet",String.class);
        }
        return artifact;
    }

    @SneakyThrows
    private static void reflectionInjectFromEnum(Map<String, String> map, String cursor, Artifact artifact, String methodName, Class... inputTypes) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(cursor)) {
                Method method = artifact.getClass().getMethod(methodName, inputTypes);
                method.invoke(artifact, cursor);
                break;
            }
        }
    }

    @SneakyThrows
    private static void reflectionInjectByValue(Double value, Artifact artifact, String methodName, Class... inputTypes) {
        Method method = artifact.getClass().getMethod(methodName, inputTypes);
        method.invoke(artifact, value);
    }


}
