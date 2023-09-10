package com.aptech.group.dto.user;

import com.aptech.group.dto.PagingRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCriteria implements PagingRequest {

    private int id;
    private Integer managerId;
    private Integer title;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String keycloakId;

    private String search;
    private int size;
    private int page;
    private String lastedValue = "";
    private int lastedPage;
    private String firstValue = "";
    private List<String> sort;
}
