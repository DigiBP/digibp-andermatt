package ch.fhnw.digibp;

import ch.fhnw.digibp.model.OrderItem;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class ProcessData implements JavaDelegate {

    private final static Logger LOG = Logger.getLogger("ProcessData");
    private boolean hasMassmarket = false;
    private boolean hasJustMeProduction = false;
    private boolean hasJustMeTailor = false;
    List<String> externalPartnerList = new ArrayList<>();

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOG.info("what do I have here?" + delegateExecution.getVariables().toString());

        String orderId = UUID.randomUUID().toString();

        delegateExecution.setVariable("orderId", orderId);

        LOG.info(String.format("cart object debugging: %s", delegateExecution.getVariables().toString()));

        ArrayList<LinkedHashMap> list = (ArrayList) delegateExecution.getVariable("cart");

        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (LinkedHashMap map : list) {
            OrderItem item = new OrderItem();

            item.setOrderId(orderId);
            item.setDescription((String) map.get("description"));
            item.setUrl((String) map.get("url"));
            item.setPrice(Double.parseDouble(map.get("price").toString()));
            item.setCount(Integer.parseInt(map.get("count").toString()));
            item.setPartner((String) map.get("partner"));

            hasJustMeProduction = hasJustMeProduction || "Just-me Production".equals(item.getPartner());
            hasJustMeTailor = hasJustMeTailor || "Just-me Tailor".equals(item.getPartner());

            if (!("Just-me Production".equals(item.getPartner()) && "Just-me Tailor".equals(item.getPartner()))) {
                hasMassmarket = true;
                externalPartnerList.add(item.getPartner());
            }


            orderItemList.add(item);
        }

        LOG.info(String.format("external partner list: %s", externalPartnerList.toString()));

        ObjectValue serializedOrderItems =
                Variables.objectValue(orderItemList).serializationDataFormat("application/json").create();

        delegateExecution.setVariable("orderItems", serializedOrderItems);
        delegateExecution.setVariable("hasJustMeProduction", hasJustMeProduction);
        delegateExecution.setVariable("hasJustMeTailor", hasJustMeTailor);
        delegateExecution.setVariable("hasMassmarket", hasMassmarket);
        delegateExecution.setVariable("externalPartnerList", externalPartnerList);
    }
}
