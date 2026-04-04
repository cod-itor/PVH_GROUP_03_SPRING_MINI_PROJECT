package org.ksga.pvh_group_03_spring_mini_project.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.UUID;
@Mapper
public interface UserAchievementRepository {
    //    insert table app_user_achievements
    @Insert("""
            INSERT INTO app_user_achievements (app_user_id, achievement_id)
            VALUES (#{appUserId}, #{achievementId})
            """)
    void insertUserAchievement(UUID appUserId, UUID achievementId);
}
