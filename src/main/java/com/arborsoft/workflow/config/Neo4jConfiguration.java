package com.arborsoft.workflow.config;

import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(Neo4jConfiguration.class);

    private RestGraphDatabase database;
    private RestCypherQueryEngine engine;

    @Bean
    public RestGraphDatabase database() {
        String uri = "http://localhost:7474/db/data/";
        LOG.info(">> neo4j graph database @ " + uri);
        this.database = new RestGraphDatabase(uri, null, null);
        return this.database;
    }

    @Bean
    public RestCypherQueryEngine engine() {
        if (this.engine == null) {
            this.engine = new RestCypherQueryEngine(this.database.getRestAPI());
        }
        LOG.info(">> neo4j cypher engine");
        return this.engine;
    }
}
