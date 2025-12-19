package com.dependencytrack.mcp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMetrics {
    public String lastOccurrence;
    public String inheritedRiskScore;
    public String riskScore;
    public String critical;
    public String high;
    public String medium;
    public String low;
    public String unassigned;
    public String vulnerabilities;
    public String vulnerableComponents;
    public String components;
    public String suppressions;
    public String findingsTotal;
    public String findingsAudited;
    public String findingsUnaudited;
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

