package caixin.android.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;

import java.util.List;

import caixin.android.com.entity.chatroom.RedPackInformationResponse;
import caixin.android.com.utils.ImgLoader;

/**
 * Created by cxf on 2018/11/21.
 */

public class RedPackResultAdapter extends RecyclerView.Adapter<RedPackResultAdapter.Vh> {

    private List<RedPackInformationResponse.GrabBean> mList;
    private LayoutInflater mInflater;

    public RedPackResultAdapter(Context context, List<RedPackInformationResponse.GrabBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_red_pack_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position) {
        vh.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView mAvatar;
        TextView mName;
        TextView mTime;
        TextView mWinCoin;

        public Vh(View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.avatar);
            mName = itemView.findViewById(R.id.name);
            mTime = itemView.findViewById(R.id.time);
            mWinCoin = itemView.findViewById(R.id.win_coin);
        }

        void setData(RedPackInformationResponse.GrabBean bean) {
            ImgLoader.GlideLoadCircle(mAvatar, bean.getUser().getImg(), R.mipmap.img_user_head);
            mName.setText(bean.getUser().getNikeName());
            mTime.setText(bean.getAddtime());
            mWinCoin.setText(bean.getMoney());
        }
    }
}
