package adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiang.maketeamapp.MainActivity;
import com.example.qiang.maketeamapp.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
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
 * Created by qiang on 2018/5/17.
 * 在分类详情页中的RecycleView中作为已发布活动的适配器
 */

public class IssuedActivityAdapter extends RecyclerView.Adapter<IssuedActivityAdapter.ViewHolder>{
    private List<IssuedActivityClass> issuedActivityList;
    private Context mContext;
    private Handler mHandler_UpdateData;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_who_issue;
        TextView tv_activity_id;
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
            tv_activity_id=(TextView)itemView.findViewById(R.id.invisible_activity_id);
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
        final ViewHolder holder = new ViewHolder(view);
        holder.button_join.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                int position = holder.getAdapterPosition();
                IssuedActivityClass activityClass=issuedActivityList.get(position);
                String Update;
                String activityID=activityClass.getActivityID();
                final String Add="2";   //增加参与人数请求
                final String Minus="3";//取消参与

                if(holder.button_join.getText().toString().equals("立即参与")) {
                    Update=Add;
                }
                else {
                    Update=Minus;
                }

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
                            String responseData = response.body().string();
                            Message message = new Message();
                            message.obj = responseData;
                            mHandler_UpdateData.sendMessage(message);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler_UpdateData = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String updateResponseStr=msg.obj.toString();
                        Gson gson = new Gson();
                        ResponseState updateState=gson.fromJson(updateResponseStr, ResponseState.class);
                        if(updateState.getMsg().equals("参与成功！"))holder.button_join.setText("取消参与");
                        if(updateState.getMsg().equals("你已经取消参与了！")) holder.button_join.setText("立即参与");
                        Toast.makeText(v.getContext(),updateState.getMsg(),Toast.LENGTH_SHORT).show();
//                        swipeRefresh.setRefreshing(false);
                    }
                };
//
//

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
