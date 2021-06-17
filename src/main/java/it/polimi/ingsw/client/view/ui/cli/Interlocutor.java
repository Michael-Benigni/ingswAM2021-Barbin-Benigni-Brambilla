package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.utils.config.StringParser;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Interlocutor {
    private static final int WIDTH_SECTION = 160;
    private static final int MAX_HORIZ_DIVISIONS = 4;
    private final PrintWriter writer;

    public Interlocutor() {
        writer = new PrintWriter (System.out);
    }

    static int getWidthSection() {
        return WIDTH_SECTION;
    }

    static int getMaxHorizDivisions() {
        return MAX_HORIZ_DIVISIONS;
    }

    public synchronized void write(String string) {
        System.out.printf ("\n%s\n", string);
    }

    static String getSectionHeader(String header, String charPadding) {
        StringBuilder upperBorder = new StringBuilder ();
        for (int i = 0; i < WIDTH_SECTION; i++)
            upperBorder.append (charPadding);
        return upperBorder + "\n" + padding (header, charPadding, WIDTH_SECTION) + "\n";
    }

    static String juxtapose(ArrayList<String> elements, int widthEachElem) {
        ArrayList<ArrayList<String>> allLines = new ArrayList<> ();
        StringParser parser = new StringParser ("\n");
        int maxLines = 0;
        for (String element : elements) {
            ArrayList<String> lines = parser.decompose(element);
            allLines.add (lines);
            if (lines.size () > maxLines)
                maxLines = lines.size ();
        }
        StringBuilder result = new StringBuilder ();
        for (int index = 0; index < maxLines; index++) {
            for (ArrayList<String> strings : allLines) {
                try {
                    result.append (padding (strings.get (index), " ", Math.round (widthEachElem)));
                } catch (IndexOutOfBoundsException e) {
                    result.append (padding (" ", " ", Math.round (widthEachElem)));
                }
                // margin
                result.append (" ");
            }
            result/*.append (padding ("", " ", widthEachElem))*/.append ("\n");
        }
        try {
            return widthCheck(result.toString (), allLines.get (0).get (0).length () + 1);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return result.toString ();
        }
    }

    static ArrayList<ArrayList<String>> cut(ArrayList<String> lines, int widthEachElement) {
        int limit = 0;
        int newLines = 0;
        if (!lines.isEmpty ()) {
            newLines = Math.floorDiv (lines.get (0).length (),  WIDTH_SECTION);
            if (lines.get (0).length () % WIDTH_SECTION > 0)
                newLines++;
            limit = ((WIDTH_SECTION - (WIDTH_SECTION % widthEachElement)) / widthEachElement) * widthEachElement;
        }
        ArrayList<ArrayList<String>> text = new ArrayList<> ();
        for (String line : lines) {
            for (int i = 0; i < newLines; i++) {
                text.add (new ArrayList<> ());
                if (line.length () > WIDTH_SECTION) {
                    String firstLine = line.substring (i * limit, (Math.min ((i + 1) * limit, line.length ())));
                    text.get (i).add (firstLine);
                }
                else
                    text.get (i).add (line);
            }
        }
        return text;
    }

    static String widthCheck(String toString, int widthEachElement) {
        StringParser parser = new StringParser ("\n");
        ArrayList<String> lines = parser.decompose(toString);
        StringBuilder result = new StringBuilder ();
        ArrayList<ArrayList<String>> text = cut (lines, widthEachElement);
        for (ArrayList<String> strings : text) {
            for (String string : strings)
                result.append (string).append ("\n");
            result.append ("\n");
        }
        return result.toString ();
    }

    static String encapsulate(String text, int dimension) {
        StringBuilder horizontalEdge = new StringBuilder();
        for (int i = 0; i < dimension; i++)
            horizontalEdge.append ("_");
        StringParser parser = new StringParser ("\n");
        ArrayList<String> strings = parser.decompose (text);
        int maxLength = StringParser.getMaxLength (strings);
        int finalDimension = Math.max (dimension, maxLength);
        String encapsulation = strings.stream()
                .map ((string)-> "|" + padding (string, " ", finalDimension) + "|\n")
                .collect(Collectors.joining());
        return "_" + horizontalEdge + "_\n" + encapsulation + "|" + horizontalEdge + "|\n";
    }

    static String padding(String toPadding, String charPadding, int dimension) {
        if (toPadding.contains ("\n")) {
            ArrayList<String> lines = new StringParser ("\n").decompose (toPadding);
            return lines.stream ().map ((line) -> padding (line, " ", dimension) + "\n").collect (Collectors.joining ());
        }
        if (toPadding.length () % 2 != 0)
            toPadding += " ";
        while (toPadding.length () < dimension) {
            toPadding = charPadding + toPadding + charPadding;
        }
        return toPadding;
    }

    static String colour(Colour colour, String target) {
        StringParser parser = new StringParser ("\n");
        ArrayList<String> lines = parser.decompose(target);
        StringBuilder coloured = new StringBuilder ();
        for (String line : lines) {
            if (colour == null)
                colour = Colour.RESET;
            coloured.append (String.format ("%s%s%s", colour.escape (), line, Colour.RESET.escape ()));
        }
        return coloured.toString ();
    }

}
