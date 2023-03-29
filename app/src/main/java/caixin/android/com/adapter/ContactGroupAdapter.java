package caixin.android.com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caixin.huanyu.R;

import java.util.ArrayList;

import caixin.android.com.constant.Contact;
import caixin.android.com.entity.ContactGroupEntity;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.utils.ViewSetUtil;
import caixin.android.com.widget.MyImageView;

public class ContactGroupAdapter extends BaseExpandableListAdapter {

    private ArrayList<ContactGroupEntity> gData; //分组
    private ArrayList<ArrayList<FriendEntity>> iData; //长链表
    private Context mContext;

    public ContactGroupAdapter(ArrayList<ContactGroupEntity> gData, ArrayList<ArrayList<FriendEntity>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }


    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ViewHolderGroup groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contact_group, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.mGroupState = convertView.findViewById(R.id.iv_state);
            groupHolder.mGroupName = convertView.findViewById(R.id.name);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        //groupHolder.mGroupImage.setImageResource(gData.get(i).getmGroupImage());
        groupHolder.mGroupName.setText(gData.get(groupPosition).getGroupName());

        if (isExpanded) {
            groupHolder.mGroupState.setImageResource(R.mipmap.jiantou_down);
        } else {
            groupHolder.mGroupState.setImageResource(R.mipmap.jiantou_right);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem itemHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
            itemHolder = new ViewHolderItem();
            itemHolder.avatar = convertView.findViewById(R.id.avatar);
            itemHolder.nameTextView = convertView.findViewById(R.id.name);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ViewHolderItem) convertView.getTag();
        }
        ViewSetUtil.setUserNick(iData.get(groupPosition).get(childPosition), itemHolder.nameTextView);
        ViewSetUtil.setUserAvatar(iData.get(groupPosition).get(childPosition), itemHolder.avatar);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private static class ViewHolderGroup {
        private ImageView mGroupState;
        private TextView mGroupName; //分组名
    }

    private static class ViewHolderItem {
        MyImageView avatar;
        TextView nameTextView;
    }
}
