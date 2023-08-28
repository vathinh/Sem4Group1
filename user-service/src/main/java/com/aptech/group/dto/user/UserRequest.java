package com.aptech.group.dto.user;

import com.aptech.group.model.TitleEntity;
import com.aptech.group.model.UserEntity;
import com.aptech.group.validator.ExistRecord;
import com.aptech.group.validator.NoExistRecord;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

//    @ExistRecord(message = "Manager is not existed", ignoreNull = true, entityName = UserEntity.class)
//    private Integer managerId;
//
//    @ExistRecord(message = "Title is not existed", entityName = TitleEntity.class)
//    private Integer title;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    @NoExistRecord(message = "Email is existed", fieldName = "email", entityName = UserEntity.class)
    private String email;

    @NotBlank
    @NoExistRecord(message = "Phone is existed", fieldName = "phone", entityName = UserEntity.class)
    private String phone;

    private String keycloakId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime commencementDate;
    private Long version;
}
