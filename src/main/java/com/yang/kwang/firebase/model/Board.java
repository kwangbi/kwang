package com.yang.kwang.firebase.model;

import lombok.Data;

import java.util.ArrayList;


@Data
public class Board {
    String docId;
    String userId;
    String userName;
    String password;
    String title;
    String content;
    String regDtm;

    private ArrayList<String> checkList;
}
