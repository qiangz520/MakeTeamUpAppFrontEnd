package adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiang.maketeamapp.AddTeam;
import com.example.qiang.maketeamapp.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import HttpTool.HttpUtil;
import bean.IssuedActivityClass;
import bean.ResponseState;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static classes.Constant.URL_UpdateData;

/**
 * Created by qiang on 2018/5/21.
 */

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.ViewHolder>{
    private List<IssuedActivityClass> issuedActivityList;
    private Context mContext;
//    private Handler mHandler_delete;
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
            button_join=(Button)itemView.findViewById(R.id.join_button);//改为删除操作按钮
        }
    }

    public MyTeamAdapter(List<IssuedActivityClass> issuedActivityList) {
        this.issuedActivityList = issuedActivityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.issued_activity_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.button_join.setText("点击删除");
        holder.button_join.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(R.drawable.ic_smile)//这里是显示提示框的图片信息，我这里使用的默认androidApp的图标
                        .setTitle("删除发布")
                        .setMessage("您确定要删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.button_join.setText("已删除");
                                int position = holder.getAdapterPosition();
                                IssuedActivityClass activityClass=issuedActivityList.get(position);
                                String Update;
                                String activityID=activityClass.getActivityID();
                                final String Delete="4";//删除活动
                                Update=Delete;
                                String originAddress=URL_UpdateData;
                                RequestBody requestBody=new FormBody.Builder()
                                        .add("activityID",activityID)
                                        .add("update",Update)
                                        .build();
                                try {
                                    HttpUtil.sendOkHttpRequestPost(originAddress,requestBody, new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {

                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {

                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();


            }
        });
        return holder;
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
