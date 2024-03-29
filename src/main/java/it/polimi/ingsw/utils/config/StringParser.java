package it.polimi.ingsw.utils.config;

import java.util.ArrayList;


/**
 * It's a parser that decompose a String that represents a path of Json nodes: passing through these json properties, the
 * ConfigLoader can create the Java objects from a Json file
 */
public class StringParser {

    /**
     * Separator that will be present in the Strings to parse and divide
     */
    private static String SEPARATOR = "/";


    public StringParser(String SEPARATOR) {
        setSeparator (SEPARATOR);
    }


    /**
     * Extracts all the Strings in a list separated by a SEPARATOR.
     * @param path in this project is the path of a Json properties, collected in a unique String. Nodes are divided by
     *             a separator
     * @return the list of all the nodes as an ArrayList of Strings
     */
    public ArrayList<String> decompose(String path) {
        String[] nodes;
        try {
            nodes = path.split(SEPARATOR);
        } catch (Exception e) {
            return new ArrayList<>(0);
        }
        ArrayList listNodes = new ArrayList(0);
        for(int i = 0; i < nodes.length; i++)
            listNodes.add(nodes[i]);
        return listNodes;
    }


    /**
     * returns a string from an ArrayList of Strings, concatenated with the SEPARATOR of this class.
     * In the project it's used to obtain the path of the Json Tree starting from an ordered Arraylist of Strings that
     * represents a list of Json nodes
     * @param nodes list of nodes of a path (will be a  list of names of Json nodes in this project)
     * @return the concatenation of the nodes with the SEPARATOR -> nodeSEPARATORnodeSEPARATOR..
     */
    String compose (ArrayList<String> nodes) {
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


    /**
     * It's a method that can set another SEPARATOR of the Parser
     * @param sep -> the new Separator
     */
    static void setSeparator(String sep) {
        SEPARATOR = sep;
    }

    public static int getMaxLength(ArrayList<String> strings) {
        ArrayList<Integer> lengths = strings.stream ().map (String::length).collect (ArrayList::new, ArrayList::add, ArrayList::addAll);
        lengths.sort ((integer1, integer2)->{
            if (integer1.equals (integer2))
                return 0;
            else {
                if (integer1 > integer2)
                    return 1;
                return -1;
            }});
        return lengths.get (0);
    }
}
