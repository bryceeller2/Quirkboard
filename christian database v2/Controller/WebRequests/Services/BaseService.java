package com.example.ckraft.myapplication.Controller.WebRequests.Services;

import com.example.ckraft.myapplication.Controller.WebRequests.BaseServiceSettings;
import com.example.ckraft.myapplication.Model.BaseClass;
import com.example.ckraft.myapplication.View.AppContext;
import com.example.ckraft.myapplication.Controller.WebRequests.RequestHandler;

import java.util.HashMap;

/**
 * Created by ckraft on 8/26/2016.
 */
public class BaseService<T extends BaseClass> {

    public String url = "";

    public BaseService(BaseClass obj) {
        url = AppContext.getBASE_URL() + "/Data/" + obj.getURLName() + "/";
    }

    public RequestHandler GetAll()
    {
        BaseServiceSettings settings = new BaseServiceSettings();
        settings.setMethod("POST");

        settings.setAction(url + "getAll.php");

        HashMap<String, String> headers = new HashMap<>();

        settings.setHeaders(headers);
        return new RequestHandler(settings);
    }

    public RequestHandler Save(int id, String json){
        BaseServiceSettings settings = new BaseServiceSettings();
        settings.setMethod("POST");
        settings.setAction(url + "save.php");
        settings.setBody(json);
        return new RequestHandler(settings);
    }

    public RequestHandler Get(int id){
        BaseServiceSettings settings = new BaseServiceSettings();
        settings.setMethod("GET");
        settings.setAction(url + "get.php?id=" + id);
        return new RequestHandler(settings);
    }
}
