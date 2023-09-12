package com.aptech.group.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class UserServiceEndpoints {
    public static final String USER_SERVICE_PATH = "/api/user";

    public static final String TITLE_PATH = USER_SERVICE_PATH + "/title";

    public static final String ACCOUNT_PATH = USER_SERVICE_PATH + "/account";


}
