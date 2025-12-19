package com.dependencytrack.mcp.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "mcp")
public interface McpConfig {
    
    Dt dt();
    
    @WithDefault("false")
    boolean readOnly();
    
    interface Dt {
        @WithDefault("http://localhost:8081")
        String url();
        
        @WithDefault("")
        String apiKey();
    }
}

