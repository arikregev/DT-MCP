package com.dependencytrack.mcp.service;

import com.dependencytrack.mcp.client.DependencyTrackClient;
import com.dependencytrack.mcp.client.model.*;
import com.dependencytrack.mcp.config.McpConfig;
import com.dependencytrack.mcp.protocol.McpTool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.*;
import java.util.UUID;

@ApplicationScoped
public class ToolHandler {
    
    @Inject
    @RestClient
    DependencyTrackClient dtClient;
    
    @Inject
    McpConfig config;

    public List<McpTool> listTools() {
        List<McpTool> tools = new ArrayList<>();
        
        // Read tools
        tools.add(createTool("list_projects", 
            "List all projects in Dependency Track",
            Map.of("type", "object", "properties", Map.of())));
        
        tools.add(createTool("get_project",
            "Get project details by UUID",
            Map.of("type", "object", "properties", Map.of(
                "uuid", Map.of("type", "string", "description", "Project UUID")
            ), "required", List.of("uuid"))));
        
        tools.add(createTool("lookup_project",
            "Lookup project by name and version",
            Map.of("type", "object", "properties", Map.of(
                "name", Map.of("type", "string", "description", "Project name"),
                "version", Map.of("type", "string", "description", "Project version")
            ), "required", List.of("name", "version"))));
        
        tools.add(createTool("get_project_components",
            "Get all components for a specific project",
            Map.of("type", "object", "properties", Map.of(
                "projectUuid", Map.of("type", "string", "description", "Project UUID")
            ), "required", List.of("projectUuid"))));
        
        tools.add(createTool("get_project_findings",
            "Get all findings (vulnerabilities) for a specific project",
            Map.of("type", "object", "properties", Map.of(
                "projectUuid", Map.of("type", "string", "description", "Project UUID")
            ), "required", List.of("projectUuid"))));
        
        tools.add(createTool("get_project_vulnerabilities",
            "Get raw vulnerabilities for a specific project",
            Map.of("type", "object", "properties", Map.of(
                "projectUuid", Map.of("type", "string", "description", "Project UUID")
            ), "required", List.of("projectUuid"))));
        
        tools.add(createTool("get_project_metrics",
            "Get current metrics (risk score, vulnerability counts) for a project",
            Map.of("type", "object", "properties", Map.of(
                "projectUuid", Map.of("type", "string", "description", "Project UUID")
            ), "required", List.of("projectUuid"))));
        
        // Write tools
        tools.add(createTool("create_project",
            "Create a new project in Dependency Track",
            Map.of("type", "object", "properties", Map.of(
                "name", Map.of("type", "string", "description", "Project name"),
                "version", Map.of("type", "string", "description", "Project version"),
                "description", Map.of("type", "string", "description", "Project description"),
                "classifier", Map.of("type", "string", "description", "Project classifier"),
                "active", Map.of("type", "boolean", "description", "Whether project is active"),
                "tags", Map.of("type", "array", "items", Map.of("type", "string"), "description", "Project tags")
            ), "required", List.of("name", "version"))));
        
        tools.add(createTool("update_project",
            "Update an existing project",
            Map.of("type", "object", "properties", Map.of(
                "uuid", Map.of("type", "string", "description", "Project UUID"),
                "name", Map.of("type", "string", "description", "Project name"),
                "version", Map.of("type", "string", "description", "Project version"),
                "description", Map.of("type", "string", "description", "Project description"),
                "active", Map.of("type", "boolean", "description", "Whether project is active"),
                "tags", Map.of("type", "array", "items", Map.of("type", "string"), "description", "Project tags")
            ), "required", List.of("uuid"))));
        
        tools.add(createTool("delete_project",
            "Delete a project",
            Map.of("type", "object", "properties", Map.of(
                "uuid", Map.of("type", "string", "description", "Project UUID")
            ), "required", List.of("uuid"))));
        
        tools.add(createTool("upload_bom",
            "Upload a CycloneDX BOM (Base64 encoded)",
            Map.of("type", "object", "properties", Map.of(
                "projectUuid", Map.of("type", "string", "description", "Project UUID"),
                "bom", Map.of("type", "string", "description", "Base64 encoded BOM content"),
                "autoCreate", Map.of("type", "boolean", "description", "Auto-create project if it doesn't exist")
            ), "required", List.of("projectUuid", "bom"))));
        
        tools.add(createTool("analyze_project",
            "Trigger analysis on a project",
            Map.of("type", "object", "properties", Map.of(
                "projectUuid", Map.of("type", "string", "description", "Project UUID")
            ), "required", List.of("projectUuid"))));
        
        return tools;
    }

    public Object handleTool(String toolName, Map<String, Object> arguments) throws Exception {
        // Check read-only mode for write operations
        Set<String> writeTools = Set.of("create_project", "update_project", "delete_project", 
                                        "upload_bom", "analyze_project");
        if (writeTools.contains(toolName) && config.readOnly()) {
            throw new IllegalStateException("Operation not allowed in Read-Only mode. Set mcp.read-only=false to enable write operations.");
        }
        
        return switch (toolName) {
            case "list_projects" -> handleListProjects();
            case "get_project" -> handleGetProject(arguments);
            case "lookup_project" -> handleLookupProject(arguments);
            case "get_project_components" -> handleGetProjectComponents(arguments);
            case "get_project_findings" -> handleGetProjectFindings(arguments);
            case "get_project_vulnerabilities" -> handleGetProjectVulnerabilities(arguments);
            case "get_project_metrics" -> handleGetProjectMetrics(arguments);
            case "create_project" -> handleCreateProject(arguments);
            case "update_project" -> handleUpdateProject(arguments);
            case "delete_project" -> handleDeleteProject(arguments);
            case "upload_bom" -> handleUploadBom(arguments);
            case "analyze_project" -> handleAnalyzeProject(arguments);
            default -> throw new IllegalArgumentException("Unknown tool: " + toolName);
        };
    }

    private McpTool createTool(String name, String description, Map<String, Object> inputSchema) {
        return new McpTool(name, description, inputSchema);
    }

    private Object handleListProjects() {
        return dtClient.listProjects();
    }

    private Object handleGetProject(Map<String, Object> args) {
        String uuid = (String) args.get("uuid");
        if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("uuid is required");
        }
        return dtClient.getProject(uuid);
    }

    private Object handleLookupProject(Map<String, Object> args) {
        String name = (String) args.get("name");
        String version = (String) args.get("version");
        if (name == null || name.isEmpty() || version == null || version.isEmpty()) {
            throw new IllegalArgumentException("name and version are required");
        }
        return dtClient.lookupProject(name, version);
    }

    private Object handleGetProjectComponents(Map<String, Object> args) {
        String projectUuid = (String) args.get("projectUuid");
        if (projectUuid == null || projectUuid.isEmpty()) {
            throw new IllegalArgumentException("projectUuid is required");
        }
        return dtClient.getProjectComponents(projectUuid);
    }

    private Object handleGetProjectFindings(Map<String, Object> args) {
        String projectUuid = (String) args.get("projectUuid");
        if (projectUuid == null || projectUuid.isEmpty()) {
            throw new IllegalArgumentException("projectUuid is required");
        }
        return dtClient.getProjectFindings(projectUuid);
    }

    private Object handleGetProjectVulnerabilities(Map<String, Object> args) {
        String projectUuid = (String) args.get("projectUuid");
        if (projectUuid == null || projectUuid.isEmpty()) {
            throw new IllegalArgumentException("projectUuid is required");
        }
        return dtClient.getProjectVulnerabilities(projectUuid);
    }

    private Object handleGetProjectMetrics(Map<String, Object> args) {
        String projectUuid = (String) args.get("projectUuid");
        if (projectUuid == null || projectUuid.isEmpty()) {
            throw new IllegalArgumentException("projectUuid is required");
        }
        return dtClient.getProjectMetrics(projectUuid);
    }

    private Object handleCreateProject(Map<String, Object> args) {
        ProjectCreateRequest request = new ProjectCreateRequest();
        request.name = (String) args.get("name");
        request.version = (String) args.get("version");
        request.description = (String) args.get("description");
        request.classifier = (String) args.get("classifier");
        request.active = args.get("active") != null ? (Boolean) args.get("active") : true;
        @SuppressWarnings("unchecked")
        List<String> tags = (List<String>) args.get("tags");
        request.tags = tags;
        
        if (request.name == null || request.name.isEmpty() || 
            request.version == null || request.version.isEmpty()) {
            throw new IllegalArgumentException("name and version are required");
        }
        
        return dtClient.createProject(request);
    }

    private Object handleUpdateProject(Map<String, Object> args) {
        ProjectUpdateRequest request = new ProjectUpdateRequest();
        request.uuid = UUID.fromString((String) args.get("uuid"));
        request.name = (String) args.get("name");
        request.version = (String) args.get("version");
        request.description = (String) args.get("description");
        request.active = (Boolean) args.get("active");
        @SuppressWarnings("unchecked")
        List<String> tags = (List<String>) args.get("tags");
        request.tags = tags;
        
        if (request.uuid == null) {
            throw new IllegalArgumentException("uuid is required");
        }
        
        return dtClient.updateProject(request);
    }

    private Object handleDeleteProject(Map<String, Object> args) {
        String uuid = (String) args.get("uuid");
        if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("uuid is required");
        }
        dtClient.deleteProject(uuid);
        return Map.of("success", true, "message", "Project deleted");
    }

    private Object handleUploadBom(Map<String, Object> args) {
        BomUploadRequest request = new BomUploadRequest();
        request.project = (String) args.get("projectUuid");
        request.bom = (String) args.get("bom");
        request.autoCreate = args.get("autoCreate") != null ? (Boolean) args.get("autoCreate") : false;
        
        if (request.project == null || request.project.isEmpty() || 
            request.bom == null || request.bom.isEmpty()) {
            throw new IllegalArgumentException("projectUuid and bom are required");
        }
        
        return dtClient.uploadBom(request);
    }

    private Object handleAnalyzeProject(Map<String, Object> args) {
        String projectUuid = (String) args.get("projectUuid");
        if (projectUuid == null || projectUuid.isEmpty()) {
            throw new IllegalArgumentException("projectUuid is required");
        }
        dtClient.analyzeProject(projectUuid);
        return Map.of("success", true, "message", "Analysis triggered");
    }
}

