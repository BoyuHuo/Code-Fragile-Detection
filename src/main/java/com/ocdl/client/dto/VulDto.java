package com.ocdl.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class VulDto {

    @JsonProperty("ocdl_project_refid")
    @SerializedName("ocdl_project_refid")
    private String projectRefId;

    @JsonProperty("project_name")
    @SerializedName("project_name")
    private String projectName;

    @JsonProperty("git_url")
    @SerializedName("git_url")
    private String gitUrl;

    @JsonProperty("branch")
    @SerializedName("branch")
    private String branch;

    @JsonProperty("code_url")
    @SerializedName("code_url")
    private String codeUrl;

    @JsonProperty("model_path")
    @SerializedName("model_path")
    private String modelPath;

    @JsonProperty("data")
    @SerializedName("data")
    private Object data;

    @JsonProperty("create_at")
    @SerializedName("create_at")
    private String createAt;
}
