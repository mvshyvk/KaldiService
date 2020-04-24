/*
 * Kaldi speach recognition REST API
 * Simple REST interface for posting tasks for non realtime speach recognition
 *
 * OpenAPI spec version: 0.9.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.mvshyvk.kaldi.service.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mvshyvk.kaldi.service.models.Segment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

/**
 * Result
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")public class Result   {
  @JsonProperty("taskId")
  private String taskId = null;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    DONE("Done"),
    
    CANCELED("Canceled");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("status")
  private StatusEnum status = null;

  @JsonProperty("text")
  private String text = null;

  @JsonProperty("textChunks")
  private List<Segment> textChunks = null;

  public Result taskId(String taskId) {
    this.taskId = taskId;
    return this;
  }

  /**
   * Get taskId
   * @return taskId
   **/
  @JsonProperty("taskId")
  @Schema(example = "h6Js2Lpd7", required = true, description = "")
  @NotNull
  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public Result status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @JsonProperty("status")
  @Schema(example = "Done", required = true, description = "")
  @NotNull
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Result text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
   **/
  @JsonProperty("text")
  @Schema(example = "This is some recognized text from a wave file", required = true, description = "")
  @NotNull
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Result textChunks(List<Segment> textChunks) {
    this.textChunks = textChunks;
    return this;
  }

  public Result addTextChunksItem(Segment textChunksItem) {
    if (this.textChunks == null) {
      this.textChunks = new ArrayList<>();
    }
    this.textChunks.add(textChunksItem);
    return this;
  }

  /**
   * Get textChunks
   * @return textChunks
   **/
  @JsonProperty("textChunks")
  @Schema(description = "")
  public List<Segment> getTextChunks() {
    return textChunks;
  }

  public void setTextChunks(List<Segment> textChunks) {
    this.textChunks = textChunks;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Result result = (Result) o;
    return Objects.equals(this.taskId, result.taskId) &&
        Objects.equals(this.status, result.status) &&
        Objects.equals(this.text, result.text) &&
        Objects.equals(this.textChunks, result.textChunks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskId, status, text, textChunks);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Result {\n");
    
    sb.append("    taskId: ").append(toIndentedString(taskId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    textChunks: ").append(toIndentedString(textChunks)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}