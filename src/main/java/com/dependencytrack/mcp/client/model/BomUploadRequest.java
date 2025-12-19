package com.dependencytrack.mcp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BomUploadRequest {
    public String project;
    public String projectName;
    public String projectVersion;
    public Boolean autoCreate;
    public String bom;
}

