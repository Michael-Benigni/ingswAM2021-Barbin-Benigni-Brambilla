package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.UserInput;
import it.polimi.ingsw.utils.config.StringParser;
import java.util.ArrayList;

public class PaymentRequest extends Request {
    private String separator = " ";

    protected PaymentRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    protected UserInput handleInput(String string) {
        StringParser parser = new StringParser (separator);
        ArrayList<String> payment = parser.decompose(string);
        UserInput paymentAsInput = new UserInput ();
        UserInput resource = new UserInput ();
        resource.addProperty ("type", payment.get (0));
        resource.addProperty ("amount", payment.get (1));
        paymentAsInput.addProperty ("resource", resource);
        paymentAsInput.addProperty ("fromWhere", payment.get (2));
        if (payment.size () > 3)
            paymentAsInput.addProperty ("depotIdx", payment.get (3));
        return paymentAsInput;
    }
}
