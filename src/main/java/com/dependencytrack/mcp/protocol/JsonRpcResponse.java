package com.dependencytrack.mcp.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonRpcResponse {
    @JsonProperty("jsonrpc")
    public String jsonrpc = "2.0";
    
    public String id;
    public Object result;
    public JsonRpcError error;

    public JsonRpcResponse() {}

    public JsonRpcResponse(String id, Object result) {
        this.id = id;
        this.result = result;
    }

    public JsonRpcResponse(String id, JsonRpcError error) {
        this.id = id;
        this.error = error;
    }

    public static class JsonRpcError {
        public int code;
        public String message;
        public Object data;

        public JsonRpcError() {}

        public JsonRpcError(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public JsonRpcError(int code, String message, Object data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }
    }
}

