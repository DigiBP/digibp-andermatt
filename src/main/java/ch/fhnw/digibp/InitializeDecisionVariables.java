package ch.fhnw.digibp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Optional;

public class InitializeDecisionVariables implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
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
