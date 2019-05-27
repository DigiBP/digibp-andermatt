package ch.fhnw.digibp;

import ch.fhnw.digibp.model.PictureDescription;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

public class ClothingLookup implements JavaDelegate {
    private final static Logger LOG = Logger.getLogger("CLOTHING-LOOKUP");
    private final static ArrayList<String> femalePictures;
    private final static ArrayList<String> malePictures;
    private final static ArrayList<String> sustainability;
    private final static Random rand = new Random();

    static {
        femalePictures = new ArrayList<>();
        femalePictures.add("https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/PE/R2/1E/00/KG/11/PER21E00K-G11@10.1.jpg");
        femalePictures.add("https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/SA/32/1D/03/CG/11/SA321D03C-G11@6.png");
        femalePictures.add("https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/PE/R2/1E/00/KG/11/PER21E00K-G11@10.1.jpg");
        femalePictures.add("https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/JC/42/1A/00/UG/11/JC421A00U-G11@4.jpg");
        femalePictures.add("https://mosaic03.ztat.net/vgs/media/pdp-zoom/SE/32/1G/00/EO/11/SE321G00E-O11@7.jpg");
        femalePictures.add("https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/SE/32/1C/02/KC/11/SE321C02K-C11@2.jpg");
        femalePictures.add("https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/M0/Q2/1G/05/HQ/11/M0Q21G05H-Q11@14.jpg");
        femalePictures.add("https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/FP/02/1G/01/5T/11/FP021G015-T11@17.jpg");
        femalePictures.add("https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/RA/L2/1E/00/DQ/11/RAL21E00D-Q11@8.png");

        malePictures = new ArrayList<>();
        malePictures.add("https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/C1/Q2/2D/00/3K/11/C1Q22D003-K11@8.jpg");
        malePictures.add("https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/BO/H2/2E/01/IG/11/BOH22E01I-G11@2.jpg");
        malePictures.add("https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/SU/O2/2O/00/0Q/11/SUO22O000-Q11@4.jpg");
        malePictures.add("https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/PI/92/2B/A1/8Q/11/PI922BA18-Q11@12.jpg");
        malePictures.add("https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/TO/72/2D/0E/QK/11/TO722D0EQ-K11@12.jpg");
        malePictures.add("https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/PI/92/2D/05/VT/11/PI922D05V-T11@8.jpg");
        malePictures.add("https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/EL/92/2S/00/UK/11/EL922S00U-K11@10.jpg");
        malePictures.add("https://mosaic03.ztat.net/vgs/media/packshot/pdp-zoom/TO/72/2O/0J/RQ/11/TO722O0JR-Q11@10.jpg");

        sustainability = new ArrayList<>();
        sustainability.add("Sustainability: low");
        sustainability.add("Sustainability: medium");
        sustainability.add("Sustainability: high");
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOG.info("Looking up some clothes");

        String partner = (String) Optional.ofNullable(delegateExecution.getVariable("partner")).orElse("");
        String producer = (String) Optional.ofNullable(delegateExecution.getVariable("producer")).orElse("");
        String clothing = (String) Optional.ofNullable(delegateExecution.getVariable("clothing")).orElse("");
        String sexAndSize = (String) Optional.ofNullable(delegateExecution.getVariable("sexAndSize")).orElse("");

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
            if (!s.isEmpty()) {
                PictureDescription item = new PictureDescription(
                        getRandomPicture(sexAndSize),
                        sustainability.get(rand.nextInt(sustainability.size())),
                        17.50,
                        s);
                possibilities.add(item);
            }
        }

        if ("Just-me Production".equals(producer) || "Just-me Tailor".equals(producer)) {
            PictureDescription item = new PictureDescription(
                    "https://mosaic04.ztat.net/vgs/media/packshot/pdp-zoom/LG/52/2A/00/2K/11/LG522A002-K11@36.jpg",
                    "Sustainability: high",
                    175.00,
                    producer);
            possibilities.add(item);
        }

        ObjectValue items =
                Variables.objectValue(possibilities).serializationDataFormat("application/json").create();

        delegateExecution.setVariable("possibilities", items);

        // dedicated flag indicating that the variables are ready for pickup
        delegateExecution.setVariable("readyForPickup", "true");
    }

    private String getRandomPicture(String sex) {
        if (sex.startsWith("Male")) {
            return malePictures.get(rand.nextInt(malePictures.size()));
        } else {
            return femalePictures.get(rand.nextInt(femalePictures.size()));
        }
    }
}
