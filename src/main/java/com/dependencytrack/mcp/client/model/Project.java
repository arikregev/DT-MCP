package com.dependencytrack.mcp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
    public UUID uuid;
    public String name;
    public String version;
    public String description;
    public String classifier;
    public Boolean active;
    public List<String> tags;
    public String lastBomImport;
    public String lastBomImportFormat;
    public String lastInheritedRiskScore;
    public String lastRiskScore;
    public String swidTagId;
    public UUID parent;
    public UUID group;
    public String cpe;
    public String purl;
    public String purlCoordinates;
    public String versionCount;
    public String lastMeasurement;
    public String lastScanImport;
    public String lastScanImportFormat;
    public String directDependencies;
    public String vulnerabilities;
    public String critical;
    public String high;
    public String medium;
    public String low;
    public String unassigned;
    public String findingsTotal;
    public String findingsAudited;
    public String findingsUnaudited;
    public String inheritedRiskScore;
    public String policyViolationsTotal;
    public String policyViolationsFail;
    public String policyViolationsWarn;
    public String policyViolationsInfo;
    public String policyViolationsAudited;
    public String policyViolationsUnaudited;
    public String policyViolationsSecurityTotal;
    public String policyViolationsSecurityFail;
    public String policyViolationsSecurityWarn;
    public String policyViolationsLicenseTotal;
    public String policyViolationsLicenseFail;
    public String policyViolationsLicenseWarn;
    public String policyViolationsOperationalTotal;
    public String policyViolationsOperationalFail;
    public String policyViolationsOperationalWarn;
}

