package com.dependencytrack.mcp.protocol;

import com.dependencytrack.mcp.service.ToolHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class McpServer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Inject
    ToolHandler toolHandler;

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    JsonRpcRequest request = objectMapper.readValue(line, JsonRpcRequest.class);
                    JsonRpcResponse response = handleRequest(request);
                    
                    if (response != null && request.id != null) {
                        String responseJson = objectMapper.writeValueAsString(response);
                        System.out.println(responseJson);
                        System.out.flush();
                    }
                } catch (Exception e) {
                    System.err.println("Error processing request: " + e.getMessage());
                    e.printStackTrace(System.err);
                }
            }
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private JsonRpcResponse handleRequest(JsonRpcRequest request) {
        try {
            switch (request.method) {
                case "initialize":
                    return handleInitialize(request);
                case "tools/list":
                    return handleToolsList(request);
                case "tools/call":
                    return handleToolsCall(request);
                case "ping":
                    return new JsonRpcResponse(request.id, Map.of("status", "ok"));
                default:
                    return new JsonRpcResponse(request.id, 
                        new JsonRpcResponse.JsonRpcError(-32601, "Method not found: " + request.method));
            }
        } catch (Exception e) {
            return new JsonRpcResponse(request.id,
                new JsonRpcResponse.JsonRpcError(-32603, "Internal error", e.getMessage()));
        }
    }

    private JsonRpcResponse handleInitialize(JsonRpcRequest request) {
        Map<String, Object> result = Map.of(
            "protocolVersion", "2024-11-05",
            "capabilities", Map.of(
                "tools", Map.of()
            ),
            "serverInfo", Map.of(
                "name", "dependency-track-mcp-server",
                "version", "1.0.0"
            )
        );
        return new JsonRpcResponse(request.id, result);
    }

    private JsonRpcResponse handleToolsList(JsonRpcRequest request) {
        return new JsonRpcResponse(request.id, Map.of("tools", toolHandler.listTools()));
    }

    private JsonRpcResponse handleToolsCall(JsonRpcRequest request) {
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) request.params;
        String toolName = (String) params.get("name");
        @SuppressWarnings("unchecked")
        Map<String, Object> arguments = (Map<String, Object>) params.get("arguments");
        
        try {
            Object result = toolHandler.handleTool(toolName, arguments);
            return new JsonRpcResponse(request.id, Map.of("content", List.of(
                Map.of("type", "text", "text", objectMapper.writeValueAsString(result))
            )));
        } catch (IllegalArgumentException e) {
            return new JsonRpcResponse(request.id,
                new JsonRpcResponse.JsonRpcError(-32602, "Invalid params", e.getMessage()));
        } catch (Exception e) {
            return new JsonRpcResponse(request.id,
                new JsonRpcResponse.JsonRpcError(-32603, "Internal error", e.getMessage()));
        }
    }
}

