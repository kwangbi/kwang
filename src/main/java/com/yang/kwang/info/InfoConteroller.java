package com.yang.kwang.info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
@RequestMapping("/info")
public class InfoConteroller {
    @Value("${config.healthmsg:'NO_DEFINE'}")
    String healthMsg;

    @GetMapping("/info")
    public String getSystemInfo(){
        String returnMsg;

        try{
            returnMsg = new StringBuilder("Hostname : ")
                    .append(InetAddress.getLocalHost().getHostName())
                    .append(", IP Address : ")
                    .append(InetAddress.getLocalHost().getHostAddress())
                    .toString();

        }catch (Exception e){
            returnMsg = new StringBuilder("Error : ")
                    .append(e.getMessage())
                    .toString();
        }

        return returnMsg;
    }

    @GetMapping("/health")
    public String checkHealth(){
        return healthMsg;
    }

}
