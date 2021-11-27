package com.bcu.cqibackend.api.sonarqube;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class ProjectsAPI {

    public JSONObject getProjectNames() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("http://localhost:9000/api/projects/search")
                .header("accept", "application/json")
                .basicAuth("c5b319835e7cb1f54dcca39e91f12b1b7d12663d", "")
                .asJson();
        return jsonResponse.getBody().getObject();
    }

    public JSONObject getProjectIssuesBySeverities(String key) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("http://localhost:9000/api/issues/search?facets=severities&componentKeys="+key)
                .header("accept", "application/json")
                .basicAuth("c5b319835e7cb1f54dcca39e91f12b1b7d12663d", "")
                .asJson();
        return jsonResponse.getBody().getObject();
    }

    public JSONObject getProjectLinesOfCode(String projectName) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("http://localhost:9000/api/measures/component?component="+projectName+"&metricKeys=ncloc")
                .header("accept", "application/json")
                .basicAuth("c5b319835e7cb1f54dcca39e91f12b1b7d12663d", "")
                .asJson();
        return jsonResponse.getBody().getObject();
    }

    public JSONObject getProjectTechnicalDebt(String projectName) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("http://localhost:9000/api/issues/search?facets=resolutions&componentKeys="+projectName)
                .header("accept", "application/json")
                .basicAuth("c5b319835e7cb1f54dcca39e91f12b1b7d12663d", "")
                .asJson();
        return jsonResponse.getBody().getObject();
    }
}
