package ch.fhnw.digibp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Optional;
import java.util.logging.Logger;

public class InitializeDecisionVariables implements JavaDelegate {
    private final static Logger LOG = Logger.getLogger("INIT");

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOG.info(String.format("Variables before init: %s", delegateExecution.getVariables()));

        String sexAndSize   = (String) Optional.ofNullable(delegateExecution.getVariable("sexAndSize")).orElse("");
        String delivery     = (String) Optional.ofNullable(delegateExecution.getVariable("delivery")).orElse("");
        String priceClass   = (String) Optional.ofNullable(delegateExecution.getVariable("priceClass")).orElse(  "");
        String occasion     = (String) Optional.ofNullable(delegateExecution.getVariable("occasion")).orElse("");

        delegateExecution.setVariable("sexAndSize", sexAndSize);
        delegateExecution.setVariable("delivery",   delivery);
        delegateExecution.setVariable("priceClass", priceClass);
        delegateExecution.setVariable("occasion",   occasion);
    }
}
