package com.aptech.group.mapstruct;

import com.aptech.group.dto.user.UserReferResponse;
import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.dto.user.UserUpdateRequest;
import com.aptech.group.model.UserEntity;
import com.aptech.group.model.UserInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = EntityMapper.class)

public interface UserMapper {
    UserResponse toResponse(UserEntity userEntity);
    UserResponse toResponse(UserReferResponse referResponse);
    UserReferResponse toReferResponse(UserEntity userEntity);

    UserResponse toResponse(UserInfoEntity userInfoEntity);

    UserEntity toEntity(UserRequest userRequest);

    void updateEntity(UserUpdateRequest userUpdateRequest, @MappingTarget UserEntity userEntity);

}
