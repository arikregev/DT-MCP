# Dependency Track MCP Server

A Model Context Protocol (MCP) server for Dependency Track that enables AI assistants to interact with Dependency Track for vulnerability management and SBOM analysis.

## Features

- **Read Operations**: List projects, get project details, retrieve components, findings, vulnerabilities, and metrics
- **Write Operations**: Create/update/delete projects, upload BOMs, trigger analysis
- **Read-Only Mode**: Configurable read-only mode to prevent accidental modifications
- **Stdio Transport**: Uses standard input/output for JSON-RPC communication

## Prerequisites

- Java 21
- Maven 3.8+
- Dependency Track instance (v4.11+)

## Configuration

Edit `src/main/resources/application.properties` or set environment variables:

```properties
# Dependency Track API URL
mcp.dt.url=http://localhost:8081

# Dependency Track API Key (required)
mcp.dt.api-key=your-api-key-here

# Read-Only Mode (set to true to disable write operations)
mcp.read-only=false
```

### Environment Variables

You can also configure via environment variables:

- `MCP_DT_URL`: Dependency Track API URL
- `MCP_DT_API_KEY`: Dependency Track API Key
- `MCP_READ_ONLY`: Set to `true` for read-only mode

## Building

```bash
mvn clean package
```

## Running

```bash
java -jar target/dt-mcp-server-1.0.0-SNAPSHOT-runner.jar
```

The server reads JSON-RPC requests from stdin and writes responses to stdout. All logs go to stderr.

## Docker

### Build Docker Image

```bash
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t dt-mcp-server:latest .
```

### Run Docker Container

```bash
docker run -i \
  -e MCP_DT_URL=http://your-dt-instance:8081 \
  -e MCP_DT_API_KEY=your-api-key \
  -e MCP_READ_ONLY=false \
  dt-mcp-server:latest
```

### Push to Docker Hub

```bash
docker tag dt-mcp-server:latest yourusername/dt-mcp-server:latest
docker push yourusername/dt-mcp-server:latest
```

## Available Tools

### Read Operations

- `list_projects`: List all projects
- `get_project`: Get project by UUID
- `lookup_project`: Lookup project by name and version
- `get_project_components`: Get components for a project
- `get_project_findings`: Get findings (vulnerabilities) for a project
- `get_project_vulnerabilities`: Get raw vulnerabilities for a project
- `get_project_metrics`: Get risk metrics for a project

### Write Operations (disabled in read-only mode)

- `create_project`: Create a new project
- `update_project`: Update an existing project
- `delete_project`: Delete a project
- `upload_bom`: Upload a CycloneDX BOM (Base64 encoded)
- `analyze_project`: Trigger analysis on a project

## API Key Permissions

Ensure your Dependency Track API key has the following permissions:

**For Read Operations:**
- `VIEW_PORTFOLIO`
- `VULNERABILITY_ANALYSIS`

**For Write Operations:**
- `PORTFOLIO_MANAGEMENT`
- `BOM_UPLOAD`
- `VULNERABILITY_ANALYSIS`

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

