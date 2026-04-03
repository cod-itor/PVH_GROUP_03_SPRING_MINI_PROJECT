package org.ksga.pvh_group_03_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.ksga.pvh_group_03_spring_mini_project.beanConfig.UUIDTypeHandler;
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
}
