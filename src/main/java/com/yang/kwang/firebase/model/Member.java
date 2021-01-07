package com.yang.kwang.firebase.model;

import lombok.Data;

@Data
public class Member {
    String docId;
    String userId;
    String userName;
    String password;
    String title;
    String content;
    String regDtm;
}
