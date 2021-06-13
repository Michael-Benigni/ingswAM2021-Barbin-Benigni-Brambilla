package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.config.StringParser;
import java.util.ArrayList;

public class PaymentRequest extends Request {
    private String separator = " ";

    public PaymentRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    public MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer) throws IllegalInputException {
        super.handleInput (interlocutor, interpreter, writer);
        StringParser parser = new StringParser (separator);
        ArrayList<String> payment = parser.decompose(interpreter.listen ());
        MessageWriter resource = new MessageWriter ();
        try {
            resource.addProperty ("resourceType", payment.get (0));
            resource.addProperty ("amount", payment.get (1));
            writer.addProperty ("resource", resource.getInfo ());
            writer.addProperty ("fromWhere", payment.get (2));
            if (payment.get (2).equals (""))
                writer.addProperty ("depotIdx", payment.get (3));
        } catch (Exception e) {
            System.out.printf ("Error: %s\n", e.getMessage ());
            throw new IllegalInputException ();
        }
        return writer;
    }
}
