package org.ksga.pvh_group_03_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.HabitLog;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitLogRepository {
    
    @Insert("INSERT INTO habit_logs (habit_log_id, log_date, status, habit_id, xp_earned) " +
            "VALUES (#{habitLogId}, #{logDate}, #{status}, #{HabitId}, #{xpEarned})")
    void insertHabitLogRecord(HabitLog habitLog);
    
    @Select("SELECT hl.* FROM habit_logs hl WHERE hl.habit_log_id = #{habitLogId}")
    HabitLog findByHabitLogId(UUID habitLogId);
    
    @Select("SELECT hl.* FROM habit_logs hl WHERE hl.habit_id = #{habitId}")
    List<HabitLog> findByHabitId(UUID habitId);
}
