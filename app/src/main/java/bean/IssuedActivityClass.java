package bean;

/**
 * Created by qiang on 2018/5/17.
 * RecyclerView的item信息包装为一个类
 */

public class IssuedActivityClass {
    private String who_issue;
    private String title;
    private String description;
    private String place;
    private String demand;
    private String startTime;
    private String contactMethod;
    private String joinMessage;

    public String getWho_issue() {
        return who_issue;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getPlace() {
        return place;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public String getDescription() {
        return description;
    }

    public String getDemand() {
        return demand;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWho_issue(String who_issue) {
        this.who_issue = who_issue;
    }
}
