package com.bcu.cqibackend.domain.overview;

import com.bcu.cqibackend.api.sonarqube.ProjectsAPI;

import com.bcu.cqibackend.domain.dto.ProjectDTO;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class ProjectOverview {
    ProjectsAPI projects = new ProjectsAPI();


    public ArrayList<String> getProjectNames(){
        ArrayList<String> projectKeys = new ArrayList<>();
        try {
            JSONObject n = projects.getProjectNames();
            JSONArray components = n.getJSONArray("components");
            for (int i = 0; i < components.length(); i++) {
                JSONObject jsonObject = components.getJSONObject(i);
                Object name = jsonObject.get("name");
                projectKeys.add(name.toString());
            }
        } catch (UnirestException  e) {
            e.printStackTrace();
        }
        return projectKeys;
    }

    public ArrayList<ProjectDTO> getProjectIssuesByName(){
        ArrayList<String> projectNames = getProjectNames();
        ArrayList<ProjectDTO> projectDetails = new ArrayList<>();

        for (String projectName:projectNames) {
            try {
                ProjectDTO projectDetail = new ProjectDTO();
                projectDetail.setProjectName(projectName);
                JSONObject ob = projects.getProjectIssuesBySeverities(projectName);
                JSONArray js = ob.getJSONArray("facets");
                JSONObject obj = js.getJSONObject(0);
                projectDetail.setProjectFacets(obj);
                JSONArray arr = (JSONArray) obj.get("values");
                for (Object e:arr) {
                    JSONObject val = (JSONObject) e;
                    switch(val.get("val").toString()){
                        case "BLOCKER" :
                            projectDetail.setBlocker((int)val.get("count"));break;
                        case "CRITICAL" :
                            projectDetail.setCritical((int)val.get("count"));break;
                        case "MAJOR" :
                            projectDetail.setMajor((int)val.get("count"));break;
                        case "MINOR":
                            projectDetail.setMinor((int)val.get("count"));break;
                        case "INFO":
                            projectDetail.setInfo((int)val.get("count"));break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + val.get("val"));
                    }
                }

                JSONObject projectTechnicalDebt = projects.getProjectTechnicalDebt(projectName);
                Object effortTotal = projectTechnicalDebt.get("effortTotal");
                projectDetail.setTechnicalDebt((int)effortTotal);

                JSONObject projectLinesOfCode = projects.getProjectLinesOfCode(projectName);
                JSONObject component1 = projectLinesOfCode.getJSONObject("component");
                JSONArray measures = component1.getJSONArray("measures");
                JSONObject metric = measures.getJSONObject(0);
                projectDetail.setLinesOfCode((Integer.parseInt(metric.get("value").toString())));
                projectDetails.add(projectDetail);
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
        return projectDetails;
    }
}
