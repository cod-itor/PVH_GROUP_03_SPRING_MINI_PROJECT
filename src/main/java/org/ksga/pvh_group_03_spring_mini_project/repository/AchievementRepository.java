package org.ksga.pvh_group_03_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Achievement;
import org.ksga.pvh_group_03_spring_mini_project.model.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Mapper
public interface AchievementRepository {

    @Results(id = "achievementMapper", value = {
            @Result(property = "achievementId", column = "achievement_id"),
            @Result(property = "xpRequired", column = "xp_required")
    })

    @Select("select * from achievements offset #{offset} limit #{size}")
    List<Achievement> getAllAchievement(int offset, Integer size);

    @ResultMap(("achievementMapper"))
    @Select("""
            SELECT a.* FROM achievements a 
            INNER JOIN app_user_achievements ua ON a.achievement_id = ua.achievement_id 
            WHERE ua.app_user_id = #{appUserId} 
            ORDER BY ua.achieved_at DESC
            """)
//    List<Achievement> getUserAchievements(UUID appUserId);
    List<Achievement> getUserAchievements(UUID appUserId, Integer offset, Integer size);
}
