package com.example.ckraft.myapplication.Model;

/**
 * Created by theckraft on 8/24/16.
 */
public class User extends BaseClass{
    private int cumulativeStickiness;

    public int getCumulativeStickiness() {
        return cumulativeStickiness;
    }

    public void setCumulativeStickiness(int cumulativeStickiness) {
        this.cumulativeStickiness = cumulativeStickiness;
    }

    @Override
    public String getURLName() {
        return "User";
    }
}
