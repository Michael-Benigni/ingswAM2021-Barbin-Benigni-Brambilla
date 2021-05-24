package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.config.StringParser;
import java.util.ArrayList;

public class PaymentRequest extends Request {
    private String separator = " ";

    protected PaymentRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    protected MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer) {
        super.handleInput (interlocutor, interpreter, writer);
        StringParser parser = new StringParser (separator);
        ArrayList<String> payment = parser.decompose(interpreter.listen ());
        MessageWriter resource = new MessageWriter ();
        resource.addProperty ("type", payment.get (0));
        resource.addProperty ("amount", payment.get (1));
        writer.addProperty ("resource", resource);
        writer.addProperty ("fromWhere", payment.get (2));
        if (payment.size () > 3)
            writer.addProperty ("depotIdx", payment.get (3));
        return writer;
    }
}
