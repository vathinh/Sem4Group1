package com.aptech.group.dto.account;

import com.aptech.group.model.UserEntity;
import com.aptech.group.validator.ExistRecord;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    @NotBlank
    String password;

    @NotNull
    @ExistRecord(entityName = UserEntity.class)
    Integer userId;
}
