package bean;

/**
 * Created by qiang on 2018/4/5.
 * 主界面的活动分类，包括图片和活动名称
 */

public class TeamKind {
    private String kind_name;
   // private String team_category;
    private int imageId;
    public TeamKind(String kind_name, int imageId){
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
