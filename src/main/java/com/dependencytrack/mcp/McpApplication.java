package com.dependencytrack.mcp;

import com.dependencytrack.mcp.protocol.McpServer;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

@QuarkusMain
public class McpApplication implements QuarkusApplication {
    
    @Inject
    McpServer mcpServer;

    @Override
    public int run(String... args) {
        mcpServer.run();
        return 0;
    }
}

