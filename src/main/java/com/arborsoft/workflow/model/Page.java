package com.arborsoft.workflow.model;

import com.arborsoft.workflow.annotation.Labeled;
import com.arborsoft.workflow.annotation.RelatedTo;
import com.arborsoft.workflow.constant.Label;
import com.arborsoft.workflow.constant.Relationship;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Labeled({Label.Page})
public class Page extends BaseNode {
    protected Type type;

    @RelatedTo(Relationship.CONTAINS_FUNCTION)
    protected Set<Function> functions;

    public enum Type {
        ENTRY,
        FLOW,
        ;
    }
}
