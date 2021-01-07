package com.yang.kwang.firebase.controller;

import com.yang.kwang.firebase.model.Member;
import com.yang.kwang.firebase.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firebase")
public class testController {
    @Autowired
    TestService testService;

    @GetMapping("/getMemberDetail")
    public Member getMemberDetail(@RequestParam String id) throws Exception{
        return testService.getMemberDetail(id);
    }

    @PostMapping("/insertMember")
    public String insertMember(@RequestBody Member member) throws Exception{
        return testService.insertMember(member);
    }

    @PostMapping("/updateMember")
    public String updateMember(@RequestBody Member member) throws Exception{
        return testService.updateMember(member);
    }

    @GetMapping("/deleteMember")
    public String deleteMember(@RequestParam String id) throws Exception{
        return testService.deleteMember(id);
    }

    @GetMapping("getCollection")
    public String getCollection() throws Exception{
        return testService.getCollection();
    }

    @GetMapping("/getSubCollection")
    public String getSubCollection() throws Exception{
        return testService.getSubCollection();
    }

    @PostMapping("/getTest")
    public String getTest(@RequestBody Member member) throws Exception{
        return testService.getTest(member);
    }
}
