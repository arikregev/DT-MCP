package com.dependencytrack.mcp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Finding {
    public UUID project;
    public String projectName;
    public String projectVersion;
    public UUID component;
    public String componentName;
    public String componentVersion;
    public String componentPurl;
    public UUID vulnerability;
    public String vulnerabilityTitle;
    public String vulnerabilitySource;
    public String vulnerabilityVulnId;
    public String severity;
    public String cvssV2Vector;
    public String cvssV2BaseScore;
    public String cvssV2ImpactSubScore;
    public String cvssV2ExploitabilitySubScore;
    public String cvssV3Vector;
    public String cvssV3BaseScore;
    public String cvssV3ImpactSubScore;
    public String cvssV3ExploitabilitySubScore;
    public String cvssV31Vector;
    public String cvssV31BaseScore;
    public String cvssV31ImpactSubScore;
    public String cvssV31ExploitabilitySubScore;
    public String owaspRRVector;
    public String owaspRRScore;
    public String owaspRRLikelihoodScore;
    public String owaspRRTechnicalImpactScore;
    public String owaspRRBusinessImpactScore;
    public String matrix;
    public String cweId;
    public String cweName;
    public String description;
    public String recommendation;
    public String references;
    public String credits;
    public String created;
    public String published;
    public String updated;
    public String vulnStatus;
    public String analysisState;
    public String analysisJustification;
    public String analysisResponse;
    public String analysisDetails;
    public String analysisSuppressed;
    public String analysisComments;
    public String analysisCommenter;
    public String analysisTimestamp;
    public String attributedOn;
    public String uuid;
}

