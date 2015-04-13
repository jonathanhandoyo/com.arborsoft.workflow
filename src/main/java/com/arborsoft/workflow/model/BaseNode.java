package com.arborsoft.workflow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseNode {
    protected Long id;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)									return true;
        if (o == null || getClass() != o.getClass())	return false;

        BaseNode _o = (BaseNode) o;
        if (id == null)	return super.equals(o);
        return id.equals(_o.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
}
