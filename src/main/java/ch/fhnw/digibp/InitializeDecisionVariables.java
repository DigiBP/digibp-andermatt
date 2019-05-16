package ch.fhnw.digibp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Optional;

public class InitializeDecisionVariables implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String occasion             = (String) Optional.ofNullable(delegateExecution.getVariable("occasion")).orElse("");
        String preferredPartner     = (String) Optional.ofNullable(delegateExecution.getVariable("preferredPartner")).orElse("");
        String priceRange           = (String) Optional.ofNullable(delegateExecution.getVariable("priceRange")).orElse("");
        String sexAndSize           = (String) Optional.ofNullable(delegateExecution.getVariable("sexAndSize")).orElse("");
        String latestDeliveryDate   = (String) Optional.ofNullable(delegateExecution.getVariable("latestDeliveryDate")).orElse("");
        Boolean brand = (Boolean) Optional.ofNullable(delegateExecution.getVariable("brand")).orElse(false);

        delegateExecution.setVariable("occasion",           occasion);
        delegateExecution.setVariable("preferredPartner",   preferredPartner);
        delegateExecution.setVariable("priceRange",         priceRange);
        delegateExecution.setVariable("sexAndSize",         sexAndSize);
        delegateExecution.setVariable("latestDeliveryDate", latestDeliveryDate);
        delegateExecution.setVariable("brand",              brand);
    }
}
