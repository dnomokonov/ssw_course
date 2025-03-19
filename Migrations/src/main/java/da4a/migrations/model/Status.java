package da4a.migrations.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("available")
    AVAILABLE,

    @JsonProperty("pending")
    PENDING,

    @JsonProperty("sold")
    SOLD,

    @JsonProperty("none")
    NONE
}
