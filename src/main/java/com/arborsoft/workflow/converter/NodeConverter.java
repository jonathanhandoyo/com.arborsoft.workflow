package com.arborsoft.workflow.converter;

import com.arborsoft.workflow.annotation.RelatedTo;
import com.arborsoft.workflow.model.BaseNode;
import com.arborsoft.workflow.repository.Neo4jTemplate;
import com.arborsoft.workflow.util.ReflectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import org.neo4j.graphdb.Node;
import org.neo4j.rest.graphdb.util.ResultConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class NodeConverter<N extends BaseNode> implements Function<Map<String, Object>, N>, Converter<Map<String, Object>, N>, ResultConverter<Map<String, Object>, N> {
    private static final Logger LOG = LoggerFactory.getLogger(NodeConverter.class);

    @Autowired protected Neo4jTemplate template;
    protected String identifier;

    @Override
    public N convert(Map<String, Object> map) {
        try {
            Node node = (Node) map.get(this.identifier);
            Assert.notNull(node, "Node is null");

            return this.map(node);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public N apply(Map<String, Object> map) {
        try {
            Node node = (Node) map.get(this.identifier);
            Assert.notNull(node, "Node is null");

            return this.map(node);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public N convert(Map<String, Object> map, Class<N> aClass) {
        try {
            Node node = (Node) map.get(this.identifier);
            Assert.notNull(node, "Node is null");

            return this.map(node);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    public N map(Node node) {
        try {
            Class<?> captured = Class.forName("" + node.getProperty("__type__"));
            N result = (N) captured.newInstance();
            result.setId(node.getId());

            for (String key: node.getPropertyKeys()) {
                if ("__type__".equals(key)) continue;

                Field field = ReflectionUtils.getField(captured, key, true);
                if (field == null) continue;
                if (field.isAnnotationPresent(RelatedTo.class)) continue;

                if (field.getType().equals(Date.class)) {
                    field.set(result, new Date((long) node.getProperty(key)));
                } else if (field.getType().isEnum()) {
                    field.set(result, Enum.valueOf(field.getType().asSubclass(Enum.class), node.getProperty(key).toString()));
                } else if (Number.class.isAssignableFrom(field.getType())) {
                    field.set(result, field.getType().getMethod("valueOf", String.class).invoke(field, node.getProperty(key).toString()));
                } else if (field.getType().equals(Class.class)) {
                    field.set(result, Class.forName((String) node.getProperty(key)));
                } else if (Collection.class.isAssignableFrom(field.getType())) {
                    // TODO
                } else if (Map.class.isAssignableFrom(field.getType())) {
                    if ("tier".equalsIgnoreCase(field.getName())) {
                        field.set(result, new ObjectMapper().readValue(node.getProperty(key).toString(), new TypeReference<TreeMap<Long, Double>>() {})); //using tree map to ensure sequence of keys
                    }
                } else {
                    field.set(result, node.getProperty(key));
                }

            }
            return result;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
