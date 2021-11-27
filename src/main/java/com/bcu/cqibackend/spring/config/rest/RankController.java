package com.bcu.cqibackend.spring.config.rest;

import com.bcu.cqibackend.domain.service.ProjectRankService;
import com.bcu.cqibackend.domain.service.UserRankService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/rank")
public class RankController {
    ProjectRankService ps = new ProjectRankService();
    UserRankService us= new UserRankService();
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/{entity:(?:projects)|(?:developers)}")
    @ResponseBody
    public HashMap<String,Double> getEntityLists (HttpServletRequest request, @PathVariable("entity") String entity){
        if ("projects".equals(entity.toString())) {
           return ps.getRankedProjects();
        } else if ("developers".equals(entity.toString())) {
            return us.getUserPerformance();
        } else {
            return null;
        }
    }
}
