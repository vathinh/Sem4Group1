package com.aptech.group.dto.account;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCriteria implements PagingAccountRequest{

    private String search;
    private int first;
    private int max;

}
