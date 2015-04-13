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
@Labeled({Label.Function})
public class Function extends BaseNode {
    @RelatedTo(Relationship.CONTAINS_INTERFACE)
    protected Set<Interface> interfaces;

    @RelatedTo(Relationship.ON_SUBMIT)
    protected Page submit;

    @RelatedTo(Relationship.ON_CANCEL)
    protected Page cancel;

    @RelatedTo(Relationship.ON_FAILURE)
    protected Page fail;
}
