package com.bcu.cqibackend.domain.overview;

import com.bcu.cqibackend.api.sonarqube.UserAPI;
import com.bcu.cqibackend.domain.dto.UserDTO;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserOverview {
    UserAPI userAPI = new UserAPI();
    public ArrayList<String> getUserNames (){
        ArrayList<String> userNames = new ArrayList<>();
        try {
            JSONObject usersEmail = userAPI.getUsersEmail();
            Object authors = usersEmail.get("authors");
            JSONArray arr = (JSONArray) authors;
            for (int i = 0; i < arr.length(); i++) {
                userNames.add(arr.get(i).toString());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return userNames;
    }

    public ArrayList<UserDTO> getUserDetails (){
        ArrayList<UserDTO> userDetails = new ArrayList<>();
        ArrayList<String> userNames = getUserNames();
        for (String userName : userNames) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(userName);
            try {
                JSONObject userIssuesBySeverities = userAPI.getUserIssuesBySeverities(userName);
                JSONArray js = userIssuesBySeverities.getJSONArray("facets");
                JSONObject obj = js.getJSONObject(0);
                JSONArray arr = (JSONArray) obj.get("values");
                for (Object e:arr) {
                    JSONObject val = (JSONObject)e;
                    switch(val.get("val").toString()){
                        case "BLOCKER" :
                            userDTO.setBlocker((int)val.get("count"));break;
                        case "CRITICAL" :
                            userDTO.setCritical((int)val.get("count")); break;
                        case "MAJOR" :
                            userDTO.setMajor((int)val.get("count"));break;
                        case "MINOR":
                            userDTO.setMinor((int)val.get("count"));break;
                        case "INFO":
                            userDTO.setInfo((int)val.get("count"));break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + val.get("val"));
                    }
                }
                Object effortTotal = userIssuesBySeverities.get("effortTotal");
                userDTO.setTechnicalDebt((int)effortTotal);
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            userDetails.add(userDTO);
        }
        return userDetails;
    }
}
