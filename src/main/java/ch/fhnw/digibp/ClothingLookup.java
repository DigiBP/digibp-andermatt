package ch.fhnw.digibp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class ClothingLookup implements JavaDelegate {
    private final static Logger LOG = Logger.getLogger("CLOTHING-LOOKUP");

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOG.info("I have looked up some clothes");

        delegateExecution.setVariable("readyForPickup", "true");
    }
}
