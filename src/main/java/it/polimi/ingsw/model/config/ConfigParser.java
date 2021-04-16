package it.polimi.ingsw.model.config;

import java.util.ArrayList;

/**
 * It's a parser that decompose a String that represents a path of Json nodes: passing through these json properties, the
 * ConfigLoader can create the Java objects from a Json file
 */
class ConfigParser {
    private static String SEPARATOR = "/";

    static ArrayList<String> decompose(String filePath) {
        String[] nodes = new String[] {};
        try {
            nodes = filePath.split(SEPARATOR);
        } catch (Exception e) {
            return new ArrayList<>(0);
        }
        ArrayList listNodes = new ArrayList(0);
        for(int i = 0; i < nodes.length; i++)
            listNodes.add(nodes[i]);
        return listNodes;
    }

    static String compose (ArrayList<String> nodes) {
        String path;
        try {
            path = nodes.get(0);
            nodes.remove(path);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        for (String node : nodes) {
            path = path.concat(SEPARATOR + node);
        }
        return path;
    }

    static void setSeparator(String sep) {
        SEPARATOR = sep;
    }
}
