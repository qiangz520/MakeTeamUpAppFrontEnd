package adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.qiang.maketeamapp.R;

import java.util.List;

import bean.IssuedActivityClass;

/**
 * Created by qiang on 2018/5/17.
 * 在分类详情页中的RecycleView中作为已发布活动的适配器
 */

public class IssuedActivityAdapter extends RecyclerView.Adapter<IssuedActivityAdapter.ViewHolder>{
    private List<IssuedActivityClass> issuedActivityList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_who_issue;
        TextView tv_activity_title;
        TextView tv_activity_des;
        TextView tv_activity_place;
        TextView tv_activity_demand;
        TextView tv_activity_time;
        TextView tv_contact_method;
        TextView tv_number_message;
        Button button_join;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView;
            tv_who_issue=(TextView)itemView.findViewById(R.id.who_issued_activity);
            tv_activity_title=(TextView)itemView.findViewById(R.id.issued_activity_title);
            tv_activity_des=(TextView)itemView.findViewById(R.id.issued_activity_description);
            tv_activity_place=(TextView)itemView.findViewById(R.id.issued_activity_place);
            tv_activity_demand=(TextView)itemView.findViewById(R.id.issued_activity_demand);
            tv_activity_time=(TextView)itemView.findViewById(R.id.issued_activity_time);
            tv_contact_method=(TextView)itemView.findViewById(R.id.activity_contactMethod);
            tv_number_message=(TextView)itemView.findViewById(R.id.activity_number_message);
            button_join=(Button)itemView.findViewById(R.id.join_button);
        }
    }

    public IssuedActivityAdapter(List<IssuedActivityClass> issuedActivityList) {
        this.issuedActivityList = issuedActivityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.issued_activity_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IssuedActivityClass issuedActivityClass=issuedActivityList.get(position);
        holder.tv_who_issue.setText(issuedActivityClass.getWho_issue());
        holder.tv_activity_title.setText(issuedActivityClass.getTitle());
        holder.tv_activity_des.setText(issuedActivityClass.getDescription());
        holder.tv_activity_place.setText(issuedActivityClass.getPlace());
        holder.tv_activity_demand.setText(issuedActivityClass.getDemand());
        holder.tv_activity_time.setText(issuedActivityClass.getStartTime());
        holder.tv_contact_method.setText(issuedActivityClass.getContactMethod());
        holder.tv_number_message.setText(issuedActivityClass.getJoinMessage());
    }

    @Override
    public int getItemCount() {
        return issuedActivityList.size();
    }



}
