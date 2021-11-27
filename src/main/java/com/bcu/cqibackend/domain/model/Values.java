package com.bcu.cqibackend.domain.model;

import java.util.ArrayList;

public class Values {
    private ArrayList<IssueTypes> issueTypes;
    private String property;


    public ArrayList<IssueTypes> getIssueTypes() {
        return issueTypes;
    }

    public void setIssueTypes(ArrayList<IssueTypes> issueTypes) {
        this.issueTypes = issueTypes;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
