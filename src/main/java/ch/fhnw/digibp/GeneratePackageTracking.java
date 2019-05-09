package ch.fhnw.digibp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.UUID;
import java.util.logging.Logger;

public class GeneratePackageTracking implements JavaDelegate {

    private final static Logger LOG = Logger.getLogger("GeneratePackageTracking");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOG.info("Generating tracking information");

        UUID trackingID = UUID.randomUUID();
        delegateExecution.setVariable("trackingId", trackingID.toString());
    }
}
