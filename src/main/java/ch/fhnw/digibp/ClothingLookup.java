package ch.fhnw.digibp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ClothingLookup implements JavaDelegate {
    private final static Logger LOG = Logger.getLogger("CLOTHING-LOOKUP");

    private final static String peterHahnStrickjackeCoral = "https://mosaic03.ztat" +
            ".net/vgs/media/packshot/pdp-zoom/PE/R2/1E/00/KG/11/PER21E00K-G11@10.1.jpg";

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOG.info("I have looked up some clothes");

        List<PictureDescription> singlePossibilities = new LinkedList<PictureDescription>();
        singlePossibilities.add(new PictureDescription(peterHahnStrickjackeCoral, "Peter Hahn Strickjacke Coral"));

        List<PictureDescription> setPossibilities = new LinkedList<PictureDescription>();
        setPossibilities.add(new PictureDescription(peterHahnStrickjackeCoral, "Peter Hahn Strickjacke Coral"));

        if (delegateExecution.getVariable("clothing") == null) {
            // whole set
            delegateExecution.setVariable("possibilities", setPossibilities);
        } else {
            // only one item
            delegateExecution.setVariable("possibilities", singlePossibilities);
        }
        delegateExecution.setVariable("readyForPickup", "true");
    }

    private class PictureDescription {
        private String url;
        private String description;

        public PictureDescription(String url, String description) {
            this.url = url;
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
