package com.dependencytrack.mcp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectCreateRequest {
    public String name;
    public String version;
    public String description;
    public String classifier;
    public Boolean active;
    public List<String> tags;
    public UUID parent;
    public UUID group;
    public String cpe;
    public String purl;
    public String swidTagId;
}

