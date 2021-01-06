package com.yang.kwang.firebase.model;

import lombok.Data;

@Data
public class Board {
    Long idx;
    String userId;
    String userName;
    String password;
    String title;
    String content;
    String regDtm;
}
