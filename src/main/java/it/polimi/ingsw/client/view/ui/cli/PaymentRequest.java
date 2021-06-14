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
        MessageWriter toPay = new MessageWriter ();
        try {
            resource.addProperty ("resourceType", payment.get (0));
            resource.addProperty ("amount", Integer.parseInt (payment.get (1)));
            toPay.addProperty ("resource", resource.getInfo ());
            toPay.addProperty ("fromWhere", payment.get (2));
            if (payment.get (2).equals (""))
                toPay.addProperty ("depotIdx", Integer.parseInt (payment.get (3)));
            ArrayList<Object> paymentArray = new ArrayList<> ();
            paymentArray.add (toPay.getInfo ());
            writer.addProperty ("payActions", paymentArray);
        } catch (Exception e) {
            System.out.printf ("Error: %s\n", e.getMessage ());
            throw new IllegalInputException ();
        }
        return writer;
    }
}
