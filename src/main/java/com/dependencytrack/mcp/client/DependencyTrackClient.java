package com.dependencytrack.mcp.client;

import com.dependencytrack.mcp.client.model.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient
@RegisterClientHeaders(DependencyTrackHeadersFactory.class)
public interface DependencyTrackClient {
    
    // Read Operations - Projects
    @GET
    @Path("/api/v1/project")
    @Produces(MediaType.APPLICATION_JSON)
    List<Project> listProjects();
    
    @GET
    @Path("/api/v1/project/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    Project getProject(@PathParam("uuid") String uuid);
    
    @GET
    @Path("/api/v1/project/lookup")
    @Produces(MediaType.APPLICATION_JSON)
    Project lookupProject(@QueryParam("name") String name, @QueryParam("version") String version);
    
    // Read Operations - Components
    @GET
    @Path("/api/v1/component/project/{projectUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Component> getProjectComponents(@PathParam("projectUuid") String projectUuid);
    
    // Read Operations - Findings
    @GET
    @Path("/api/v1/finding/project/{projectUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Finding> getProjectFindings(@PathParam("projectUuid") String projectUuid);
    
    // Read Operations - Vulnerabilities
    @GET
    @Path("/api/v1/vulnerability/project/{projectUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Vulnerability> getProjectVulnerabilities(@PathParam("projectUuid") String projectUuid);
    
    // Read Operations - Metrics
    @GET
    @Path("/api/v1/metrics/project/{projectUuid}/current")
    @Produces(MediaType.APPLICATION_JSON)
    ProjectMetrics getProjectMetrics(@PathParam("projectUuid") String projectUuid);
    
    // Write Operations - Projects
    @PUT
    @Path("/api/v1/project")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Project createProject(ProjectCreateRequest request);
    
    @POST
    @Path("/api/v1/project")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Project updateProject(ProjectUpdateRequest request);
    
    @DELETE
    @Path("/api/v1/project/{uuid}")
    void deleteProject(@PathParam("uuid") String uuid);
    
    // Write Operations - BOM
    @PUT
    @Path("/api/v1/bom")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    BomUploadResponse uploadBom(BomUploadRequest request);
    
    // Write Operations - Analysis
    @POST
    @Path("/api/v1/finding/project/{projectUuid}/analyze")
    void analyzeProject(@PathParam("projectUuid") String projectUuid);
}

