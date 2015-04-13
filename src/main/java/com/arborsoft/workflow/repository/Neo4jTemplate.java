package com.arborsoft.workflow.repository;

import com.arborsoft.workflow.annotation.Labeled;
import com.arborsoft.workflow.annotation.RelatedTo;
import com.arborsoft.workflow.converter.NodeConverter;
import com.arborsoft.workflow.model.BaseNode;
import com.arborsoft.workflow.util.ReflectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.cypherdsl.grammar.Execute;
import org.neo4j.graphdb.Node;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class Neo4jTemplate {
    private static final Logger LOG = LoggerFactory.getLogger(Neo4jTemplate.class);

    @Autowired private RestGraphDatabase database;
    @Autowired private RestCypherQueryEngine engine;

    public <N extends BaseNode> N save(N node) throws IllegalAccessException {
        Assert.notNull(node, "Node is null");

        Node _node = (node.getId() != null ? this.database.getNodeById(node.getId()) : null);
        if (_node == null) {
            _node = this.database.createNode(ReflectionUtils.getTypeAnnotation(node.getClass(), Labeled.class).value());
            Assert.state(_node != null, "Unable to create node");

            node.setId(_node.getId());
            Assert.state(node.getId() != null, "Unable to set node.id");
        }

        _node.setProperty("__type__", node.getClass().getName());
        for (Field field: ReflectionUtils.getFields(node.getClass(), true)) {
            if (field.isAnnotationPresent(RelatedTo.class)) continue;
            if (field.get(node) != null) {
                if (Map.class.isAssignableFrom(field.getType())) {
                    try {
                        _node.setProperty(field.getName(), new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(field.get(node)));
                    } catch (Exception e) {
                        LOG.error("unable to save JSON string representation of Map for Node[" + node.getId() + "] on key[" + field.getName() + "]");
                    }
                }
                _node.setProperty(field.getName(), field.get(node));
            } else {
                _node.removeProperty(field.getName());
            }
        }

        return node;
    }

    public <N extends BaseNode> N findById(Class<N> type, long id) {
        return new NodeConverter<N>().map(this.database.getNodeById(id));
    }

    public QueryResult<Map<String, Object>> query(Execute executable, Map<String, Object> parameter) {
        return this.engine.query(executable.toString(), parameter);
    }
}
