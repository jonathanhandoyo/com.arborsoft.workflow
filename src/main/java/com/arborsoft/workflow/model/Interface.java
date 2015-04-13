package com.arborsoft.workflow.model;

import com.arborsoft.workflow.annotation.Labeled;
import com.arborsoft.workflow.constant.Label;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Labeled({Label.Interface})
public class Interface extends BaseNode {
    protected Type type;

    public enum Type {
        REST,
        JDBC,
    }
}
