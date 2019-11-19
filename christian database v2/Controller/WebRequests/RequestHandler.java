package com.example.ckraft.myapplication.Controller.WebRequests;

/**
 * Created by ckraft on 8/29/2016.
 */
public class RequestHandler {

    BaseServiceSettings serviceSettings;

    public RequestHandler(BaseServiceSettings serviceSettings) {
        this.serviceSettings = serviceSettings;
    }

    public void run(UniversalRequest.TaskListener listener){

        new UniversalRequest(serviceSettings, listener).execute();

    }


}
