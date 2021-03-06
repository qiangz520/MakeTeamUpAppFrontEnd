package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qiang.maketeamapp.KindActivity;
import com.example.qiang.maketeamapp.R;

import java.util.List;

import bean.TeamKind;

/**
 * Created by qiang on 2018/4/5.
 * 点击某一类组队活动时，查看
 */

public class KindAdapter extends RecyclerView.Adapter <KindAdapter.ViewHolder> {
    private Context mContext;
    private List<TeamKind> mTeamKindList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView kindImage;
        TextView kindName;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            kindImage=(ImageView)view.findViewById(R.id.kind_image);
            kindName=(TextView)view.findViewById(R.id.kind_name);
        }
    }
    public KindAdapter(List<TeamKind> kindList){
        mTeamKindList=kindList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewTyoe){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.kind_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                TeamKind team_kind=mTeamKindList.get(position);
                Intent intent=new Intent(mContext,KindActivity.class);
                intent.putExtra(KindActivity.KIND_NAME,team_kind.getkind_name());
                intent.putExtra(KindActivity.KIND_IMAGE_ID,team_kind.getImageId());
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeamKind kind=mTeamKindList.get(position);
        holder.kindName.setText(kind.getkind_name());
        Glide.with(mContext).load(kind.getImageId()).into(holder.kindImage);
    }

    @Override
    public int getItemCount() {
        return mTeamKindList.size();
    }
}
