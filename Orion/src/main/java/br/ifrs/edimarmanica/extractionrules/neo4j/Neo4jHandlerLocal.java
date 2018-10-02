/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifrs.edimarmanica.extractionrules.neo4j;

import br.ifrs.edimarmanica.config.Configurations;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.StringLogger;

/**
 *
 * @author edimar
 */
public class Neo4jHandlerLocal extends Neo4jHandler {

    private final static String DB_FILE_NAME = "graph.db";
    private ExecutionEngine engine;

    public Neo4jHandlerLocal(String siteID) {
        super(siteID);

        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(getDBPath());
        engine = new ExecutionEngine(graphDb, StringLogger.SYSTEM_ERR);

        registerShutdownHook(graphDb);
    }

    public static void deleteDatabase(String siteID) {
        try {
            Runtime.getRuntime().exec("rm -rf " + getDBPath(siteID));
        } catch (IOException ex) {
            Logger.getLogger(Neo4jHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getDBPath() {
        return getDBPath(siteID);
    }

    private static String getDBPath(String siteID) {
        return Configurations.GRAPH_PATH + "/" + siteID + "/" + DB_FILE_NAME;
    }

    @Override
    public Iterator<Map<String, Object>> executeCypher(String cypher) {
        return (Iterator<Map<String, Object>>) engine.execute(cypher).javaIterator();
    }

    @Override
    public void shutdown() {
        graphDb.shutdown();
    }

    @Override
    public Iterator<Map<String, Object>> executeCypher(String cypher, Map<String, Object> params) {
        return (Iterator<Map<String, Object>>) engine.execute(cypher, params).javaIterator();
    }

    public static void main(String[] args) {
        String siteID = "";
        Neo4jHandler neo4j = new Neo4jHandlerLocal(siteID);
        Map<String, Object> params = new HashMap<>();
        params.put("value", ".*'.*");
        List<Object> results = neo4j.querySingleColumn("MATCH n WHERE n.VALUE =~ {value} RETURN n.VALUE as value LIMIT 3", params, "value");
        for (Object result : results) {
            System.out.println(result.toString());
        }
    }
}
