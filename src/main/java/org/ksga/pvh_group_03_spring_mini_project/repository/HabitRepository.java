package org.ksga.pvh_group_03_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.ksga.pvh_group_03_spring_mini_project.beanConfig.UUIDTypeHandler;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Habit;
import org.ksga.pvh_group_03_spring_mini_project.model.request.HabitRequest;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitRepository {
    @Results(id = "habitMapper", value = {
            @Result(property = "habitId", column = "habit_id"),
            @Result(property = "isActive", column = "is_active"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "appUserResponse", column = "app_user_id",
                    one = @One(select = "org.ksga.pvh_group_03_spring_mini_project.repository.ProfileRepository.getUserProfile"
            )),

    })
    @Select("""
                SELECT * FROM habits WHERE app_user_id = #{userUUID} OFFSET #{offset} LIMIT #{size};
            """)
    List<Habit> getAllHabits(Integer offset, Integer size, UUID userUUID);


    @ResultMap("habitMapper")
    @Select("""
                INSERT INTO habits (title, description, frequency, app_user_id) VALUES (#{req.title}, #{req.description}, #{req.frequency}, #{appUserId}) RETURNING *;
            """)
    Habit createHabit(@Param("req") HabitRequest request, UUID appUserId);


    @ResultMap("habitMapper")
    @Select("""
        SELECT * FROM habits WHERE habit_id = #{habitId};
    """)
    Habit getHabitById(UUID habitId, UUID appUserId);


    @ResultMap("habitMapper")
    @Delete("""
        DELETE FROM habits WHERE habit_id = #{habitId} AND app_user_id = #{appUserId};
    """)
    void deleteHabitById(UUID habitId, UUID appUserId);


    @ResultMap("habitMapper")
    @Select("""
                UPDATE habits SET title = #{req.title}, description = #{req.description}, frequency = #{req.frequency}
                WHERE habit_id = #{habitId} AND app_user_id = #{appUserId};
            """)
    Habit updateHabitById(UUID habitId,@Param("req") HabitRequest request, UUID appUserId);
}

















