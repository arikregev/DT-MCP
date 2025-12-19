package com.dependencytrack.mcp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Component {
    public UUID uuid;
    public String name;
    public String group;
    public String version;
    public String purl;
    public String purlCoordinates;
    public String cpe;
    public String swidTagId;
    public Boolean internal;
    public String description;
    public String copyright;
    public String license;
    public String licenseExpression;
    public String licenseUrl;
    public String filename;
    public String extension;
    public String md5;
    public String sha1;
    public String sha256;
    public String sha512;
    public String sha3_256;
    public String sha3_512;
    public String blake2b_256;
    public String blake2b_512;
    public String blake3;
    public String integrityCheckValue;
    public String integrityCheckAlgorithm;
    public String lastInheritedRiskScore;
    public String lastRiskScore;
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

