package com.bcu.cqibackend.domain.service;

import com.bcu.cqibackend.api.sonarqube.UserAPI;
import com.bcu.cqibackend.domain.dto.UserDTO;
import com.bcu.cqibackend.domain.overview.UserOverview;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRankService {
    UserOverview us = new UserOverview();
    public HashMap<String,Double> getUserPerformance (){
        ArrayList<UserDTO> userDetails = us.getUserDetails();

        HashMap<String, Double> userList = new HashMap<>();
        for (UserDTO user: userDetails) {
            int severities = getSeverities(user.getBlocker(), user.getCritical(), user.getMajor(), user.getMinor(), user.getInfo());
            int severitiesWeighted = getSeveritiesWeighted(user.getBlocker(), user.getCritical(), user.getMajor(), user.getMinor(), user.getInfo());
            userList.put(user.getUserName(),(double)severities);
        }
        return userList;
    }

    public int getSeverities(int blocker,int critical, int major, int minor, int info){
        return (blocker + critical + major + minor +info);
    }

    public int getSeveritiesWeighted (int blocker,int critical, int major, int minor, int info){
        final int w_Blocker = 8, w_Critical = 6, w_Major = 5, w_Minor = 3, w_info = 2;
        return ((blocker*w_Blocker) + (critical*w_Critical) + (major*w_Major) + (minor*w_Minor) +(info*w_info));
    }

}
