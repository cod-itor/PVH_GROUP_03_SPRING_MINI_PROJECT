package org.ksga.pvh_group_03_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.ksga.pvh_group_03_spring_mini_project.beanConfig.UUIDTypeHandler;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.Achievement;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.AppUser;
import org.ksga.pvh_group_03_spring_mini_project.model.request.AppUserRequest;

import java.util.UUID;

@Mapper
public interface AppUserRepository {


    @Select("""
                SELECT * FROM app_users
                WHERE email = #{identifier} OR username = #{identifier}
            """)
    @Results(id = "userMapper", value = {
            @Result(property = "appUserId", column = "app_user_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "profileImageUrl", column = "profile_image"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    AppUser findUserByIdentifier(String identifier);

    @Select("""
                insert into app_users(username, email, password, profile_image)
                values (#{user.username},#{user.email},  #{user.password},#{user.profileImageUrl})
                returning *;
            """)
    @ResultMap("userMapper")
    AppUser registerAppUser(@Param("user") AppUserRequest appUserRequest);

    @Select("""
                SELECT app_user_id, username, email, level, xp, profile_image, is_verified, created_at FROM app_users
                WHERE app_user_id=#{userUUID}
            """)
    @ResultMap("userMapper")
    AppUser findUserByUUID(@Param("userUUID") UUID userUUID);

    @Update("""
            update app_users set is_verified = #{isVerified} where email =#{email}
            """)
    boolean updateVerifyAppUser(String email, boolean isVerified);

    @Update("UPDATE app_users SET xp = #{xp}, level = #{level} WHERE app_user_id = #{userUUID}")
    void updateUserXp(@Param("userUUID") UUID userUUID, @Param("xp") int xp, @Param("level") int level);

    @Select("SELECT xp from app_users where app_user_id = #{userUUID}")
    Integer getUserXp(@Param("userUUID") UUID userUUID);

    Achievement getUserAchievement(int offset, Integer size, UUID achievementId);
}
