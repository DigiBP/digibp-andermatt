package ch.fhnw.digibp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class PaymentToPartner implements JavaDelegate {
    private final static Logger LOG = Logger.getLogger("PAYMENT-TO-PARTNER");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOG.info("Paying our partner its share");
    }
}
