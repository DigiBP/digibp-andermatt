package ch.fhnw.digibp;

import ch.fhnw.digibp.model.PictureDescription;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ClothingLookup implements JavaDelegate {
    private final static Logger LOG = Logger.getLogger("CLOTHING-LOOKUP");

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOG.info("Looking up some clothes");

        String partner = (String) Optional.ofNullable(delegateExecution.getVariable("partner")).orElse("");
        String producer = (String) Optional.ofNullable(delegateExecution.getVariable("producer")).orElse("");
        String clothing = (String) Optional.ofNullable(delegateExecution.getVariable("clothing")).orElse("");

        String[] partnerList = partner.split(",");


        LOG.info(
                String.format(
                    "partner: %s, producer: %s, clothing: %s",
                    partner,
                    producer,
                    clothing
        ));

        List<PictureDescription> possibilities = new ArrayList<>();

        for (String s : partnerList) {
            //TODO: add some more items and distinguish via "clothing"
            PictureDescription item = new PictureDescription(
                    "https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/PE/R2/1E/00/KG/11/PER21E00K-G11@10.1.jpg",
                    "Strickjacke",
                    17.50,
                    s);
            possibilities.add(item);
        }

        if ("Just-me Production".equals(producer) || "Just-me Tailor".equals(producer)) {
            PictureDescription item = new PictureDescription(
                    "https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/PE/R2/1E/00/KG/11/PER21E00K-G11@10.1.jpg",
                    "Strickjacke",
                    17.50,
                    producer);
            possibilities.add(item);
        }

        ObjectValue items =
                Variables.objectValue(possibilities).serializationDataFormat("application/json").create();

        delegateExecution.setVariable("possibilities", items);

        // dedicated flag indicating that the variables are ready for pickup
        delegateExecution.setVariable("readyForPickup", "true");
    }
}
