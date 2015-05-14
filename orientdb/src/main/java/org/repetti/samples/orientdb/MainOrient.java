package org.repetti.samples.orientdb;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

/**
 * Created on 14/05/15.
 */
public class MainOrient {
    public static void main(String[] args) {
        OrientGraphFactory factory = new OrientGraphFactory("memory:MyDb");
        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {
            String me = "me";
            String you = "you";
            String have = "have";
            OrientVertex out = graph.addVertex(me);
            OrientVertex in = graph.addVertex(you);
            OrientEdge e = graph.addEdge(have, out, in, "blabla");
            System.out.println(graph.getFeatures());
            System.out.println(e);
            System.out.println(in);
            System.out.println(out);
            for (Edge o : graph.getEdges()) {
                System.out.println(o.getLabel());
                System.out.println(o.getVertex(Direction.IN));
                System.out.println(o.getVertex(Direction.OUT));
//                System.out.println(o.getVertex(Direction.BOTH));
            }
            System.out.println();

        } finally {
            graph.shutdown();
        }
    }
}
