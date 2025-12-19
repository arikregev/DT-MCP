package com.dependencytrack.mcp.client;

import com.dependencytrack.mcp.config.McpConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

@ApplicationScoped
public class DependencyTrackHeadersFactory implements ClientHeadersFactory {
    
    @Inject
    McpConfig config;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                   MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
        String apiKey = config.dt().apiKey();
        if (apiKey != null && !apiKey.isEmpty()) {
            result.add("X-Api-Key", apiKey);
        }
        return result;
    }
}

