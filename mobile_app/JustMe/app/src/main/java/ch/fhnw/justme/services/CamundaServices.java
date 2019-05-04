package ch.fhnw.justme.services;

import ch.fhnw.justme.messages.getvariable.GetVariableResponse;
import ch.fhnw.justme.messages.message.CorrelateMessageRequest;
import ch.fhnw.justme.messages.startprocess.StartProcessFormRequest;
import ch.fhnw.justme.messages.startprocess.StartProcessFormResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CamundaServices {

    @POST("/test")
    Call<StartProcessFormResponse> test(@Body StartProcessFormRequest req);

    @POST("/rest/process-definition/key/{key}/start")
    Call<StartProcessFormResponse> startProcess(@Path("key") String key, @Body StartProcessFormRequest request);

    @GET("/rest/process-instance/{id}/variables?deserializeValues=false")
    Call<GetVariableResponse> getVariables(@Path("id") String processInstanceId);

    @POST("/rest/message")
    Call<Object> correlateMessage(@Body CorrelateMessageRequest correlateMessageRequest);
}
