package it.polimi.ingsw.model.config;

import java.util.ArrayList;

/**
 * It's a parser that decompose a String that represents a path of Json nodes: passing through these json properties, the
 * ConfigLoader can create the Java objects from a Json file
 */
class ConfigParser {
    private static String SEPARATOR = "/";

    static ArrayList<String> decompose(String filePath) {
        String[] nodes = filePath.split(SEPARATOR);
        ArrayList listNodes = new ArrayList(0);
        for(int i = 0; i < nodes.length; i++)
            listNodes.add(nodes[i]);
        return listNodes;
    }

    static void setSeparator(char sep) {
        SEPARATOR = String.valueOf(sep);
    }
}
