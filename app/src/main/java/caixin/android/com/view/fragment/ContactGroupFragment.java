package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentContactGroupBinding;

import java.util.ArrayList;

import caixin.android.com.adapter.ContactGroupAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.entity.ContactGroupEntity;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.viewmodel.ContactViewModel;

public class ContactGroupFragment extends BaseFragment<FragmentContactGroupBinding, ContactViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_contact_group;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    private ContactGroupAdapter mContactGroupAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
//        mViewModel.getFriends();
        initContactGroup();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void initContactGroup() {
        ArrayList<ContactGroupEntity> gData = new ArrayList<>(); //分组
        ArrayList<ArrayList<FriendEntity>> iData = new ArrayList<>(); //长链表
        for (int i = 0; i < 4; i++) {
            gData.add(new ContactGroupEntity("这是第" + i + "组", i));
            ArrayList<FriendEntity> friendEntities = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                FriendEntity friendEntity = new FriendEntity();
                friendEntity.setNikeName("这是第" + i + "组的第" + j + "个对象");
                friendEntities.add(friendEntity);
            }
            iData.add(friendEntities);
        }
        mContactGroupAdapter = new ContactGroupAdapter(gData, iData, getContext());
        mBinding.elvGroup.setAdapter(mContactGroupAdapter);
        mBinding.elvGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.e(TAG, "onChildClick: groupPosition = " + groupPosition + ",childPosition = " + childPosition);
                return true;
            }
        });
    }

    @Override
    public void initViewObservable() {


    }

    private static final String TAG = "ContactFragment";


}
