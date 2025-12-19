package com.dependencytrack.mcp.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class McpTool {
    public String name;
    public String description;
    @JsonProperty("inputSchema")
    public Map<String, Object> inputSchema;

    public McpTool() {}

    public McpTool(String name, String description, Map<String, Object> inputSchema) {
        this.name = name;
        this.description = description;
        this.inputSchema = inputSchema;
    }
}

