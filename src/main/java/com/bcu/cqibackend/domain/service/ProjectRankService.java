package com.bcu.cqibackend.domain.service;

import com.bcu.cqibackend.domain.dto.ProjectDTO;
import com.bcu.cqibackend.domain.overview.ProjectOverview;

import java.util.*;

public class ProjectRankService {
    ProjectOverview ps = new ProjectOverview();

    public HashMap<String,Double> getRankedProjects (){
        ArrayList<ProjectDTO> projectIssuesByName = ps.getProjectIssuesByName();
        
        HashMap<String, Double> rankedProjects = new HashMap<>();


        for (ProjectDTO project:projectIssuesByName) {
            double codeQualityBySeverities = getCodeQualityBySeverities(project.getBlocker(), project.getCritical(), project.getMajor(), project.getMinor(), project.getInfo(), project.getLinesOfCode());
            double codeQualityBySeveritiesWeighted = getCodeQualityBySeveritiesWeighted(project.getBlocker(), project.getCritical(), project.getMajor(), project.getMinor(), project.getInfo(), project.getLinesOfCode());
            double codeQualityByDebt = getCodeQualityByDebt(project.getTechnicalDebt(), project.getLinesOfCode());

//            rankedProjects.put(project.getProjectName(),codeQualityBySeverities);
//            rankedProjects.put(project.getProjectName(),codeQualityBySeveritiesWeighted);
            rankedProjects.put(project.getProjectName(),codeQualityByDebt);
        }


        for (Map.Entry<String, Double> entry: rankedProjects.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        HashMap<String, Double> stringDoubleHashMap = sortByValue(rankedProjects);
        for (Map.Entry<String, Double> entry: stringDoubleHashMap.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        return stringDoubleHashMap;
    }

    public double getCodeQualityBySeverities(int blocker,int critical, int major, int minor, int info, int loc){
        return (double)(blocker + critical + major + minor +info) / loc;
    }

    public double getCodeQualityBySeveritiesWeighted (int blocker,int critical, int major, int minor, int info, int loc){
        final int w_Blocker = 8, w_Critical = 6, w_Major = 5, w_Minor = 3, w_info = 2;
        return (double)((blocker*w_Blocker) + (critical*w_Critical) + (major*w_Major) + (minor*w_Minor) +(info*w_info)) / loc;
    }
    public double getCodeQualityByDebt(int technicalDebt, int loc){
        return (double)technicalDebt/loc;
    }

    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String, Double> >(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
