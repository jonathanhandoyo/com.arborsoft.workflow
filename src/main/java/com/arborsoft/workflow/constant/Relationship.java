package com.arborsoft.workflow.constant;

public enum Relationship {
    CONTAINS_FUNCTION,  //(:Page) --> (:Function)
    CONTAINS_INTERFACE, //(:Function) --> (:Interface)

    ON_SUBMIT,
    ON_CANCEL,
    ON_FAILURE,
    ;
}
