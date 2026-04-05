package org.ksga.pvh_group_03_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface UserAchievementRepository {

    @Insert("""
            INSERT INTO app_user_achievements (app_user_id, achievement_id)
            VALUES (#{appUserId}, #{achievementId})
            """)
    void insertUserAchievement(UUID appUserId, UUID achievementId);

    @Select("""
            SELECT COUNT(*) > 0 FROM app_user_achievements
            WHERE app_user_id = #{appUserId} AND achievement_id = #{achievementId}
            """)
    boolean hasUserEarnedAchievement(UUID appUserId, UUID achievementId);
}

