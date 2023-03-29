/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package caixin.android.com.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityNewFriendsMsgBinding;

import caixin.android.com.adapter.NotificationAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.entity.NotificationEntity;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.viewmodel.NewFriendsMsgViewModel;

import java.util.List;


/**
 * Application and notification
 */
public class NewFriendsMsgActivity extends BaseActivity<ActivityNewFriendsMsgBinding, NewFriendsMsgViewModel> {
    NotificationAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_new_friends_msg;
    }

    @Override
    public NewFriendsMsgViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(NewFriendsMsgViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.init();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.list.setLayoutManager(new LinearLayoutManager(NewFriendsMsgActivity.this));
        mBinding.titleBar.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.titleBar.title.setText("消息与通知");
        adapter = new NotificationAdapter();
        View empty = LayoutInflater.from(NewFriendsMsgActivity.this).inflate(R.layout.empty_layout, mBinding.list, false);
        adapter.setEmptyView(empty);
        mBinding.list.setAdapter(adapter);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.init.observe(this,this::handleInit);
    }

    private void handleInit(Object o) {
        if (o == null){
            adapter.setNewData(null);
            return;
        }
        List<NotificationEntity> userResponseList = JSONObject.parseArray( o.toString(), NotificationEntity.class);
        adapter.setNewData(userResponseList);
    }
}
