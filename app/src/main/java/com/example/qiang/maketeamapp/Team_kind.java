package com.example.qiang.maketeamapp;

/**
 * Created by qiang on 2018/4/5.
 */

public class Team_kind {
    private String kind_name;
   // private String team_category;
    private int imageId;
    public Team_kind(String kind_name, int imageId){
        this.kind_name=kind_name;
        this.imageId=imageId;
    }
    public String getkind_name(){
        return kind_name;
    }
    public int getImageId(){
        return imageId;
    }
}
