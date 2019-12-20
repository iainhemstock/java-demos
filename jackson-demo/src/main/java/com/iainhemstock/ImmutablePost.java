package com.iainhemstock;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public final class ImmutablePost {
    private final int theId;

    /**
     * An immutable object is typically initialized through a constructor so we need to tell Jackson
     * that this is the constructor to use when mapping. We do that with @JsonCreater. Next we have
     * to tell Jackson which member variable to map the json field to. We do that with @JsonProperty.
     */
    @JsonCreator
    public ImmutablePost(@JsonProperty("id") int theId) {
        this.theId = theId;
    }
}
