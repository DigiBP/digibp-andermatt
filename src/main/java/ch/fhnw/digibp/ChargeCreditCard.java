package ch.fhnw.digibp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class ChargeCreditCard implements JavaDelegate {
    private final static Logger LOG = Logger.getLogger("CHARGING-CREDIT-CARD");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int cardNumber = Integer.parseInt(delegateExecution.getVariable("cardNumber").toString());
        Double amount = Double.parseDouble(delegateExecution.getVariable("totalAmount").toString());
        LOG.info(String.format("Charging the credit card %d with the amount %.2f", cardNumber, amount));
    }
}
