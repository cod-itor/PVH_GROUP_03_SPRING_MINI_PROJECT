package org.ksga.pvh_group_03_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.ksga.pvh_group_03_spring_mini_project.beanConfig.UUIDTypeHandler;
import org.ksga.pvh_group_03_spring_mini_project.model.request.ProfileRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.response.AppUserResponse;

import java.util.UUID;


@Mapper
public interface ProfileRepository {
    @Results(id = "User-Profile", value = {
            @Result(property = "appUserId", column = "app_user_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "profileImageUrl", column = "profile_image"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "createdAt", column = "created_at")
    })
    @Select("""
            select * from app_users where app_user_id= #{userUUID};
            """)
    AppUserResponse getUserProfile(@Param("userUUID") UUID userUUID);

    @Select("""
        UPDATE app_users SET username = #{req.username}, profile_image = #{req.profileImageUrl} WHERE app_user_id = #{appUserId} RETURNING *
    """)
    @ResultMap("User-Profile")
    AppUserResponse updateProfile(UUID appUserId,@Param("req") ProfileRequest profileRequest);

    @Delete("""
        DELETE FROM app_users WHERE app_user_id = #{appUserId};
    """)
    void deleteProfile(UUID appUserId);
}
