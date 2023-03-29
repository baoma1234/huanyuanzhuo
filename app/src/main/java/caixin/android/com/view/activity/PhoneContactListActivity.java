package caixin.android.com.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityBlackFriendListBinding;
import com.caixin.huanyu.databinding.ActivityPhoneContactListBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.adapter.BlackListAdapter;
import caixin.android.com.adapter.PhoneContactAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.BlackFriendEntity;
import caixin.android.com.entity.BlackFriendResponse;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.MyContacts;
import caixin.android.com.utils.PhoneContactUtils;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.ContactViewModel;
import caixin.android.com.viewmodel.HomeViewModel;


public class PhoneContactListActivity extends BaseActivity<ActivityPhoneContactListBinding, ContactViewModel> {

    private static final int ACTION_SELECT_CONTACTS = 1;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_phone_contact_list;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    private static final String TAG = "GroupActivity";
    private PhoneContactAdapter phoneContactAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        
        mBinding.titleBar.ivClose.setOnClickListener(v -> finish());
        mBinding.titleBar.title.setText(getResources().getString(R.string.phone_contact));
        mBinding.list.setLayoutManager(new LinearLayoutManager(PhoneContactListActivity.this));
        myContacts = PhoneContactUtils.getAllContacts(this);
//        myContacts = PhoneContactUtils.getContacts(this);
        phoneContactAdapter = new PhoneContactAdapter(myContacts);
        phoneContactAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.list.setAdapter(phoneContactAdapter);
        phoneContactAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mViewModel.searchFriend(phoneContactAdapter.getData().get(position).getMobile());
            }
        });
        ClassicsHeader classicsHeader = new ClassicsHeader(this);
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
        mBinding.srlRefresh.setRefreshHeader(classicsHeader);
        mBinding.srlRefresh.setHeaderHeight(30);
        mBinding.srlRefresh.setEnableRefresh(true);
        mBinding.srlRefresh.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(200); //这个记得设置，否则一直转圈
            myContacts = PhoneContactUtils.getAllContacts(this);
//            myContacts = PhoneContactUtils.getContacts(this);
            phoneContactAdapter.setNewData(myContacts);
        });

        mBinding.searchBarView.query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneContactAdapter.getFilter().filter(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    private List<MyContacts> myContacts = new ArrayList<>();


    @Override
    protected void onResume() {
        super.onResume();
        mBinding.srlRefresh.autoRefresh();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.searchFriend.observe(this, this::handleSearchFriend);
    }

    private void handleSearchFriend(FriendEntity friendEntity) {
        if (friendEntity == null) {
            showShortToast("搜错出错，请联系客服！");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(AddFriendActivity.TAG_FRIEND, friendEntity);
        startActivity(NewFriendActivity.class, bundle);
    }

    private void getContacts(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case ACTION_SELECT_CONTACTS:
                    myContacts = PhoneContactUtils.getAllContacts(this);
                    phoneContactAdapter.setNewData(myContacts);
                    break;
                default:
                    break;
            }
        }
    }

}
