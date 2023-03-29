package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityRedPackSendBinding;

import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.entity.MyMoneyResponse;
import caixin.android.com.entity.SendRedPackMoneyCountLimitResponse;
import caixin.android.com.utils.ClickUtil;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.ToastUtils;
import caixin.android.com.viewmodel.SendRedPackViewModel;
import caixin.android.com.widget.paypassword.PayDialog;

import static caixin.android.com.view.activity.ChatRoomActivity.TYPE_FRIEND;
import static caixin.android.com.view.activity.ChatRoomActivity.TYPE_GROUP;

public class SendRedPackActivity extends BaseActivity<ActivityRedPackSendBinding, SendRedPackViewModel> {
    private String money;
    private boolean isExclusive;

    public static void navTo(Context context, int type, int id) {
        Intent intent = new Intent(context, SendRedPackActivity.class);
        intent.putExtra(Extras.TYPE, type);
        intent.putExtra(Extras.ID, id);
        context.startActivity(intent);
    }

    public static void navTo(Context context, int type, int id, boolean isExclusive) {
        Intent intent = new Intent(context, SendRedPackActivity.class);
        intent.putExtra(Extras.TYPE, type);
        intent.putExtra(Extras.ID, id);
        intent.putExtra(Extras.IS_EXCLUSIVE, isExclusive);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_red_pack_send;
    }

    @Override
    public SendRedPackViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SendRedPackViewModel.class);
    }

    private int mType;
    private int mToId;

    @Override
    public void initData(Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Extras.TYPE, TYPE_GROUP);
        mToId = getIntent().getIntExtra(Extras.ID, -1);
        isExclusive = getIntent().getBooleanExtra(Extras.IS_EXCLUSIVE, false);
        if (mType == TYPE_FRIEND) {
            mBinding.rlRedCount.setVisibility(View.GONE);
            mBinding.tvRedCountIntroduce.setVisibility(View.GONE);
        } else {
            mBinding.rlRedCount.setVisibility(View.VISIBLE);
            mBinding.tvRedCountIntroduce.setVisibility(View.VISIBLE);
            mViewModel.getGroupMember(mToId);
        }

        if (isExclusive) {
            mBinding.rlSelectedPerson.setVisibility(View.VISIBLE);
            mBinding.rlRedCount.setVisibility(View.GONE);
            mBinding.tvRedCountIntroduce.setVisibility(View.GONE);
            mBinding.rlGreetings.setVisibility(View.GONE);
            mBinding.tvGreetingsIntroduce.setVisibility(View.GONE);
        } else {
            mBinding.rlSelectedPerson.setVisibility(View.GONE);
        }
        mBinding.editCoinPj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSendRed();
                //删除“.”后面超过2位后的数据
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2 + 1);
                        mBinding.editCoinPj.setText(s);
                        mBinding.editCoinPj.setSelection(s.length()); //光标移到最后
                    }
                }
                //如果"."在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mBinding.editCoinPj.setText(s);
                    mBinding.editCoinPj.setSelection(2);
                }
                //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mBinding.editCoinPj.setText(s.subSequence(0, 1));
                        mBinding.editCoinPj.setSelection(1);
                        return;
                    }
                }
                if (mBinding.editCoinPj.getText().equals("") || mBinding.editCoinPj.getText().length() == 0)
                    mBinding.textMoney.setText("0.00");
                else
                    mBinding.textMoney.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.editCountPj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSendRed();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.rlSendRed.setOnClickListener(v -> {
            sendRedPack();
        });
        mBinding.titleBar.setLeftLayoutClickListener(v -> finish());
        mBinding.titleBar.setTitle("发红包");
        mViewModel.getUserMoney();
        mViewModel.getMoneyCountLimit();

        mBinding.rlSelectedPerson.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                SelectMemberActivity.navToForResult(SendRedPackActivity.this, groupMemberEntities, selectedMemberEntity, mToId);
            }
        });
    }

    public void updateSendRed() {
        if (mType == TYPE_FRIEND) {
            if (mBinding.editCoinPj.getText().length() > 0
                    && !TextUtils.isEmpty(mBinding.editCoinPj.getText())) {
                mBinding.rlSendRed.setBackground(getResources().getDrawable(R.drawable.bg_red_pack_send_action));
            } else {
                mBinding.rlSendRed.setBackground(getResources().getDrawable(R.drawable.bg_red_pack_send_unaction));
            }
        } else {
            if (!TextUtils.isEmpty(mBinding.editCoinPj.getText())
                    && mBinding.editCoinPj.getText().length() > 0) {
                if (!isExclusive) {
                    if (!TextUtils.isEmpty(mBinding.editCountPj.getText())
                            && mBinding.editCountPj.getText().length() > 0) {
                        mBinding.rlSendRed.setBackground(getResources().getDrawable(R.drawable.bg_red_pack_send_action));
                    } else {
                        mBinding.rlSendRed.setBackground(getResources().getDrawable(R.drawable.bg_red_pack_send_unaction));
                    }
                } else {
                    mBinding.rlSendRed.setBackground(getResources().getDrawable(R.drawable.bg_red_pack_send_action));
                }
            } else {
                mBinding.rlSendRed.setBackground(getResources().getDrawable(R.drawable.bg_red_pack_send_unaction));
            }
        }
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getUserMoney.observe(this, this::handleGetMoney);
        mViewModel.uc.sendRedPackToGroup.observe(this, this::handleSendRedPack);
        mViewModel.uc.sendRedPackToFriend.observe(this, this::handleSendRedPack);
        mViewModel.uc.getMoneyCountLimit.observe(this, this::handleGetMoneyCountLimit);
        mViewModel.uc.getGroupMembers.observe(this, this::handleGetGroupMembers);
    }

    private List<MemberEntity> groupMemberEntities;
    private MemberEntity selectedMemberEntity;

    private void handleGetGroupMembers(List<MemberEntity> memberEntities) {
        groupMemberEntities = memberEntities;
    }

    private SendRedPackMoneyCountLimitResponse moneyCountLimitResponse;

    private static final String TAG = "SendRedPackActivity";

    private void handleGetMoneyCountLimit(SendRedPackMoneyCountLimitResponse sendRedPackMoneyCountLimitResponse) {
        Log.e(TAG, "handleGetMoneyCountLimit: ");
        moneyCountLimitResponse = sendRedPackMoneyCountLimitResponse;
        @SuppressLint("StringFormatMatches") String moneyIntroduce = String.format(getString(R.string.red_total_introduce),
                sendRedPackMoneyCountLimitResponse.getSmall(),
                sendRedPackMoneyCountLimitResponse.getMax());
        mBinding.tvMoneyIntroduce.setText(moneyIntroduce);
        @SuppressLint("StringFormatMatches") String countIntroduce = String.format(getString(R.string.red_count_introduce),
                sendRedPackMoneyCountLimitResponse.getNumber());
        mBinding.tvRedCountIntroduce.setText(countIntroduce);
    }

    private void handleSendRedPack(Boolean b) {
        if (b) {
            ToastUtils.show("发送红包成功！");
        } else {
            ToastUtils.show("发送失败！");
        }
        mBinding.editCountPj.setText("");
        mBinding.editCoinPj.setText("");
        finish();
    }

    private void handleGetMoney(MyMoneyResponse myMoneyResponse) {
        money = myMoneyResponse.getMoney();
        mBinding.textViewRedSurplus.setText(money);
    }

    private void sendRedPack() {
        if (!ClickUtil.canClick()) {
            return;
        }
        if (mBinding.editCoinPj.getText().length() < 1 || mBinding.editCoinPj.getText().toString().equals("")) {
            Toast.makeText(SendRedPackActivity.this, "请输入您要发送的金额！", Toast.LENGTH_SHORT).show();
            return;
        }
        double money = Double.parseDouble(mBinding.editCoinPj.getText().toString());
        if (moneyCountLimitResponse != null && money > moneyCountLimitResponse.getMax()) {
            Toast.makeText(SendRedPackActivity.this, "发送的金额不能大于" + moneyCountLimitResponse.getMax(), Toast.LENGTH_SHORT).show();
            return;
        } else if (moneyCountLimitResponse != null && money < moneyCountLimitResponse.getSmall()) {
            Toast.makeText(SendRedPackActivity.this, "发送的金额不能小于" + moneyCountLimitResponse.getSmall(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mType == TYPE_GROUP && !isExclusive) {
            if (mBinding.editCountPj.getText().length() < 1 || mBinding.editCountPj.getText().toString().equals("")) {
                Toast.makeText(SendRedPackActivity.this, "请输入您要发送的红包数量！", Toast.LENGTH_SHORT).show();
                return;
            }
            int count = Integer.valueOf(mBinding.editCountPj.getText().toString());
            if (moneyCountLimitResponse != null && count > moneyCountLimitResponse.getNumber()) {
                Toast.makeText(SendRedPackActivity.this, "红包数量不能大于" + moneyCountLimitResponse.getNumber() + "！", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (isExclusive) {
            if (selectedMemberEntity == null) {
                Toast.makeText(SendRedPackActivity.this, "请选择需要发送的对象！", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        PayDialog payDialog = new PayDialog(SendRedPackActivity.this, new PayDialog.FinishedPass() {
            @Override
            public void finishedpass(String payPass) {
                String greetings = mBinding.etGreetings.getText().toString();
                if (TextUtils.isEmpty(greetings)) {
                    greetings = getResources().getString(R.string.default_greetings);
                }
                if (mType == TYPE_FRIEND) {
                    mViewModel.sendRedPackToFriend(mToId, greetings,
                            Double.parseDouble(mBinding.editCoinPj.getText().toString()), payPass);
                } else {
                    if (isExclusive) {
                        mViewModel.sendRedPackExclusive(selectedMemberEntity.getId(), selectedMemberEntity.getNikename() + "的专属红包",
                                Double.parseDouble(mBinding.editCoinPj.getText().toString()), mToId, payPass);
                    } else {
                        mViewModel.sendRedPackToGroup(mToId, greetings,
                                Integer.valueOf(mBinding.editCountPj.getText().toString()),
                                Double.parseDouble(mBinding.editCoinPj.getText().toString()), payPass);
                    }
                }
            }
        });
        payDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getUserMoney();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectMemberActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle b = data.getExtras(); //data为B中回传的Intent
            selectedMemberEntity = (MemberEntity) data.getSerializableExtra(Extras.SELECTED_MEMBER_RESULT);
            if (selectedMemberEntity != null) {
                ImgLoader.GlideLoadCircle(mBinding.ivAvatar, selectedMemberEntity.getImg(), R.mipmap.img_user_head);
                mBinding.tvName.setText(selectedMemberEntity.getNikename());
                mBinding.ivAvatar.setVisibility(View.VISIBLE);
            } else {
                mBinding.ivAvatar.setVisibility(View.GONE);
                mBinding.tvName.setText("请选择");
            }
        }
    }
}
