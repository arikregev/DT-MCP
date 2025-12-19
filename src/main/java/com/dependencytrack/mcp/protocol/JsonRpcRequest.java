package com.dependencytrack.mcp.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonRpcRequest {
    @JsonProperty("jsonrpc")
    public String jsonrpc = "2.0";
    
    public String id;
    public String method;
    public Object params;

    public JsonRpcRequest() {}

    public JsonRpcRequest(String id, String method, Object params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }
}

