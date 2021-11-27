package com.bcu.cqibackend.api.sonarqube;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class UserAPI {

    public JSONObject getUsersEmail() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("http://localhost:9000/api/issues/authors")
                .header("accept", "application/json")
                .basicAuth("c5b319835e7cb1f54dcca39e91f12b1b7d12663d", "")
                .asJson();
        return jsonResponse.getBody().getObject();
    }

    public JSONObject getUserIssuesBySeverities(String name) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("http://localhost:9000/api/issues/search?facets=severities&author="+name)
                .header("accept", "application/json")
                .basicAuth("c5b319835e7cb1f54dcca39e91f12b1b7d12663d", "")
                .asJson();
        return jsonResponse.getBody().getObject();
    }
    public JSONObject getUserTechnicalDebt() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("http://localhost:9000/api/issues/search?facets=severities&componentKeys=calculator")
                .header("accept", "application/json")
                .basicAuth("c5b319835e7cb1f54dcca39e91f12b1b7d12663d", "")
                .asJson();
        return jsonResponse.getBody().getObject();
    }
}
