CREATE VIEW IF NOT EXISTS `genshin_artifact`.`artifact_set_rate` AS
SELECT (COUNT(1) / (SELECT COUNT(0)
                    FROM `genshin_artifact`.`artifact`
                    WHERE ((`genshin_artifact`.`artifact`.`artifact_set` = '追忆')
                        OR (genshin_artifact.artifact.artifact_set = '绝缘')))) AS 绝缘比例
FROM genshin_artifact.artifact
WHERE (genshin_artifact.artifact.artifact_set = '绝缘');

CREATE VIEW IF NOT EXISTS `genshin_artifact`.`double_critical` AS
select (select count(0)
        from `genshin_artifact`.`artifact`
        where ((('暴击' in (`genshin_artifact`.`artifact`.`artifact_main_stats`,
                            `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_1`,
                            `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_2`,
                            `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_3`)) or ('暴伤' in
                                                                                               (`genshin_artifact`.`artifact`.`artifact_main_stats`,
                                                                                                `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_1`,
                                                                                                `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_2`,
                                                                                                `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_3`))) and
               (`genshin_artifact`.`artifact`.`artifact_sub_initial_stats_4` is null)))                                                                                         AS `initial_single_critical`,
       (select cast((sum(`initial_single_critical`) /
                     (select count(0) from `genshin_artifact`.`artifact`)) as decimal(5, 4)))                                                                                   AS `initial_single_critical_rate`,
       (select count(0)
        from `genshin_artifact`.`artifact`
        where (('暴击' in (`genshin_artifact`.`artifact`.`artifact_main_stats`,
                           `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_1`,
                           `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_2`,
                           `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_3`,
                           `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_4`)) and ('暴伤' in
                                                                                               (`genshin_artifact`.`artifact`.`artifact_main_stats`,
                                                                                                `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_1`,
                                                                                                `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_2`,
                                                                                                `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_3`,
                                                                                                `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_4`))))                AS `initial_double_critical`,
       (select cast((`initial_double_critical` /
                     (select count(0) from `genshin_artifact`.`artifact`)) as decimal(5, 4)))                                                                                   AS `initial_double_critical_rate`,
       (select count(0)
        from (`genshin_artifact`.`artifact` join `genshin_artifact`.`artifact_enchanting` on ((
                `genshin_artifact`.`artifact`.`artifact_id` = `genshin_artifact`.`artifact_enchanting`.`artifact_id`)))
        where ((('暴击' in (`genshin_artifact`.`artifact`.`artifact_main_stats`,
                            `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_1`,
                            `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_2`,
                            `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_3`)) and
                (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_1` = '暴伤')) or (('暴伤' in
                                                                                                  (`genshin_artifact`.`artifact`.`artifact_main_stats`,
                                                                                                   `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_1`,
                                                                                                   `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_2`,
                                                                                                   `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_3`)) and
                                                                                                 (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_1` = '暴击')))) AS `complement_double_critical`,
       (select cast((`complement_double_critical` /
                     (select count(0) from `genshin_artifact`.`artifact`)) as decimal(5, 4)))                                                                                   AS `complement_double_critical_rate`,
       (select cast(((select sum(`complement_double_critical`)) / (select count(0)
                                                                   from `genshin_artifact`.`artifact`
                                                                   where ((('暴击' in
                                                                            (`genshin_artifact`.`artifact`.`artifact_main_stats`,
                                                                             `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_1`,
                                                                             `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_2`,
                                                                             `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_3`)) or
                                                                           ('暴伤' in
                                                                            (`genshin_artifact`.`artifact`.`artifact_main_stats`,
                                                                             `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_1`,
                                                                             `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_2`,
                                                                             `genshin_artifact`.`artifact`.`artifact_sub_initial_stats_3`))) and
                                                                          (`genshin_artifact`.`artifact`.`artifact_sub_initial_stats_4` is null)))) as decimal(5, 4)))          AS `complement_success_rate`,
       (select cast(((sum(`complement_double_critical`) + sum(`initial_double_critical`)) /
                     (select count(0) from `genshin_artifact`.`artifact`)) as decimal(5, 4)))                                                                                   AS `total_double_critical_rate`,
       (select count(0) from `genshin_artifact`.`artifact`);
AS `total_artifact`

CREATE VIEW IF NOT EXISTS `genshin_artifact`.`double_critical_enchanting_rate` AS
select ((select sum(`a`.`total2`)
         from (select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_1`) AS `total2`
               from `genshin_artifact`.`artifact_enchanting`
               where ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_1` is not null) and
                      ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_1` = '暴击') or
                       (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_1` = '暴伤')))
               union
               select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_2`) AS `count(artifact_enchanting_2)`
               from `genshin_artifact`.`artifact_enchanting`
               where ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_2` is not null) and
                      ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_2` = '暴击') or
                       (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_2` = '暴伤')))
               union
               select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_3`) AS `count(artifact_enchanting_3)`
               from `genshin_artifact`.`artifact_enchanting`
               where ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_3` is not null) and
                      ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_3` = '暴击') or
                       (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_3` = '暴伤')))
               union
               select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_4`) AS `count(artifact_enchanting_4)`
               from `genshin_artifact`.`artifact_enchanting`
               where ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_4` is not null) and
                      ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_4` = '暴击') or
                       (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_4` = '暴伤')))
               union
               select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_5`) AS `count(artifact_enchanting_5)`
               from `genshin_artifact`.`artifact_enchanting`
               where ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_5` is not null) and
                      ((`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_5` = '暴击') or
                       (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_5` = '暴伤')))) `A`) /
        sum(`b`.`total`)) AS `double_critical_enchant_rate`
from (select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_1`) AS `total`
      from `genshin_artifact`.`artifact_enchanting`
      where (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_1` is not null)
      union
      select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_2`) AS `count(artifact_enchanting_2)`
      from `genshin_artifact`.`artifact_enchanting`
      where (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_2` is not null)
      union
      select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_3`) AS `count(artifact_enchanting_3)`
      from `genshin_artifact`.`artifact_enchanting`
      where (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_3` is not null)
      union
      select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_4`) AS `count(artifact_enchanting_4)`
      from `genshin_artifact`.`artifact_enchanting`
      where (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_4` is not null)
      union
      select count(`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_5`) AS `count(artifact_enchanting_5)`
      from `genshin_artifact`.`artifact_enchanting`
      where (`genshin_artifact`.`artifact_enchanting`.`artifact_enchanting_5` is not null)) `B`
