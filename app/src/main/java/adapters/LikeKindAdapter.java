package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiang.maketeamapp.R;
import bean.TeamKind;

import java.util.List;

/**
 * Created by qiang on 2018/5/9.
 */

public class LikeKindAdapter extends RecyclerView.Adapter<LikeKindAdapter.ViewHolder>{
    private List<TeamKind> mLikeList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View likeView;
        TextView kindName;
        ImageView likeIcon;
        public ViewHolder(View view){
            super(view);
            likeView=view;
            kindName=(TextView)view.findViewById(R.id.tv_like_kind_name);
            likeIcon=(ImageView)view.findViewById(R.id.iv_like_img);
        }

    }

    public LikeKindAdapter(List<TeamKind> likeList){
        mLikeList=likeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylike_kind_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TeamKind tk=mLikeList.get(position);
                holder.likeIcon.setImageResource(R.drawable.ic_like_touched);
                Toast.makeText(v.getContext(),"你关注了"+tk.getkind_name(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeamKind teamK=mLikeList.get(position);
        holder.kindName.setText(teamK.getkind_name());
        holder.likeIcon.setImageResource(teamK.getImageId());
    }

    @Override
    public int getItemCount() {
        return mLikeList.size();
    }
}
