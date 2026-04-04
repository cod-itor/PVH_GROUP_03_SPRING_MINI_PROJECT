package org.ksga.pvh_group_03_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.ksga.pvh_group_03_spring_mini_project.beanConfig.UUIDTypeHandler;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Habit;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.HabitLog;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitLogRepository {



    @Results(id = "habitLogMapper", value = {
            @Result(property = "habitLogId", column = "habit_log_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "logDate", column = "log_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "xpEarned", column = "xp_earned"),
            @Result(property = "habitId", column = "habit_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "habit", column = "habit_id",
                    one = @One(select = "org.ksga.pvh_group_03_spring_mini_project.repository.HabitRepository.getHabitById"))
    })
    @Select("INSERT INTO habit_logs (habit_log_id, log_date, status, habit_id, xp_earned) " +
            "VALUES (#{habitLogId}, #{logDate}, #{status}, #{habitId}, #{xpEarned}) RETURNING *")
    HabitLog insertHabitLogRecord(HabitLog habitLog);

    @ResultMap("habitLogMapper")
    @Select("SELECT hl.habit_log_id, hl.log_date, hl.status, hl.xp_earned, hl.habit_id FROM habit_logs hl WHERE hl.habit_log_id = #{habitLogId}")
    HabitLog findByHabitLogId(UUID habitLogId);
    
    @ResultMap("habitLogMapper")
    @Select("SELECT hl.habit_log_id, hl.log_date, hl.status, hl.xp_earned, hl.habit_id FROM habit_logs hl WHERE hl.habit_id = #{habitId}")
    List<HabitLog> findByHabitId(UUID habitId);
}
