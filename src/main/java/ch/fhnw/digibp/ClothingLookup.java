package ch.fhnw.digibp;

import ch.fhnw.digibp.model.PictureDescription;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ClothingLookup implements JavaDelegate {
    private final static Logger LOG = Logger.getLogger("CLOTHING-LOOKUP");

    private PictureDescription peterHahnStrickJackeCoral = new PictureDescription(
                "https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/PE/R2/1E/00/KG/11/PER21E00K-G11@10.1.jpg",
                "Peter Hahn Strickjacke",
                17.50);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOG.info("Looking up some clothes");

        List<PictureDescription> singlePossibilities = new LinkedList<>();
        singlePossibilities.add(peterHahnStrickJackeCoral);
        singlePossibilities.add(peterHahnStrickJackeCoral);

        List<PictureDescription> setPossibilities = new LinkedList<>();
        setPossibilities.add(peterHahnStrickJackeCoral);
        setPossibilities.add(peterHahnStrickJackeCoral);

        ObjectValue singleItems =
                Variables.objectValue(singlePossibilities).serializationDataFormat("application/json").create();

        ObjectValue setItems =
                Variables.objectValue(setPossibilities).serializationDataFormat("application/json").create();

        if (delegateExecution.getVariable("clothing") == null) {
            // whole set
            delegateExecution.setVariable("possibilities", setItems);
        } else {
            // only one item
            delegateExecution.setVariable("possibilities", singleItems);
        }

        // dedicated flag indicating that the variables are ready for pickup
        delegateExecution.setVariable("readyForPickup", "true");
    }
}
