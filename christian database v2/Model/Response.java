package com.example.ckraft.myapplication.Model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by theckraft on 9/1/16.
 */
public class Response {

    private Boolean success;
    private ArrayList<String> errors;
    private Object data;

    public Response(){
        errors = new ArrayList<>();
    }

    public Boolean getSuccess(){
        return success;
    }
    public void setSuccess(Boolean val) {
        success = val;
    }
    public ArrayList<String> getErrors(){
        return errors;
    }

    public Object getData(){
        return data;
    }
    public void setData(Object obj){
        data = obj;
    }

    public void HyrdateFromJson(JSONObject json) throws Exception {
        try {
            success = Boolean.parseBoolean(json.getString("success"));

            JSONArray errorsArray = (JSONArray) json.getJSONArray("errors");
            errors = new ArrayList<String>();
            if (errorsArray != null) {
                for (int i=0;i<errorsArray.length();i++){
                    errors.add(errorsArray.get(i).toString());
                }
            }

            try{
                data = json.getJSONObject("data");
            }
            catch (Exception e){
                data = json.getJSONArray("data");
            }

        }
        catch (JSONException e) {
            throw new Exception("Failed to parse JSON response");
        }
    }

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
