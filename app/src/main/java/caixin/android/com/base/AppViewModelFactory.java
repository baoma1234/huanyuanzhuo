package caixin.android.com.base;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import caixin.android.com.http.UserCenterModel;
import caixin.android.com.viewmodel.AboutUsViewModel;
import caixin.android.com.viewmodel.ActivityViewModel;
import caixin.android.com.viewmodel.AddMemberViewModel;
import caixin.android.com.viewmodel.ChangePassViewModel;
import caixin.android.com.viewmodel.ChatViewModel;
import caixin.android.com.viewmodel.ContactViewModel;
import caixin.android.com.viewmodel.ConversationViewModel;
import caixin.android.com.viewmodel.DeleteMemberViewModel;
import caixin.android.com.viewmodel.EmojiViewModel;
import caixin.android.com.viewmodel.ForgetPasswordViewModel;
import caixin.android.com.viewmodel.FriendCommunityViewModel;
import caixin.android.com.viewmodel.FriendInfoViewModel;
import caixin.android.com.viewmodel.GroupMembersViewModel;
import caixin.android.com.viewmodel.GroupViewModel;
import caixin.android.com.viewmodel.HomeViewModel;
import caixin.android.com.viewmodel.LiuHeGalleryViewModel;
import caixin.android.com.viewmodel.LiuHeQueryViewModel;
import caixin.android.com.viewmodel.LoginViewModel;
import caixin.android.com.viewmodel.MainViewModel;
import caixin.android.com.viewmodel.MineViewModel;
import caixin.android.com.viewmodel.MomoExchangeViewModel;
import caixin.android.com.viewmodel.MyWalletViewModel;
import caixin.android.com.viewmodel.NewFriendsMsgViewModel;
import caixin.android.com.viewmodel.RedpackResultViewModel;
import caixin.android.com.viewmodel.RegisterViewModel;
import caixin.android.com.viewmodel.SearchUserViewModel;
import caixin.android.com.viewmodel.SendRedPackViewModel;
import caixin.android.com.viewmodel.SignViewModel;
import caixin.android.com.viewmodel.SplashViewModel;
import caixin.android.com.viewmodel.UserInfoViewModel;

/**
 * Created by goldze on 2019/3/26.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;

    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application) {
        this.mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(ForgetPasswordViewModel.class)) {
            return (T) new ForgetPasswordViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(ContactViewModel.class)) {
            return (T) new ContactViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(ConversationViewModel.class)) {
            return (T) new ConversationViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(MineViewModel.class)) {
            return (T) new MineViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(UserInfoViewModel.class)) {
            return (T) new UserInfoViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(ChangePassViewModel.class)) {
            return (T) new ChangePassViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(GroupViewModel.class)) {
            return (T) new GroupViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(ChatViewModel.class)) {
            return (T) new ChatViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(NewFriendsMsgViewModel.class)) {
            return (T) new NewFriendsMsgViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(FriendInfoViewModel.class)) {
            return (T) new FriendInfoViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(AboutUsViewModel.class)) {
            return (T) new AboutUsViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(GroupMembersViewModel.class)) {
            return (T) new GroupMembersViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(AddMemberViewModel.class)) {
            return (T) new AddMemberViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(DeleteMemberViewModel.class)) {
            return (T) new DeleteMemberViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(SendRedPackViewModel.class)) {
            return (T) new SendRedPackViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(RedpackResultViewModel.class)) {
            return (T) new RedpackResultViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(MomoExchangeViewModel.class)) {
            return (T) new MomoExchangeViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(MyWalletViewModel.class)) {
            return (T) new MyWalletViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(SignViewModel.class)) {
            return (T) new SignViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(FriendCommunityViewModel.class)) {
            return (T) new FriendCommunityViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(LiuHeGalleryViewModel.class)) {
            return (T) new LiuHeGalleryViewModel(mApplication, UserCenterModel.getInstance());
        } else if (modelClass.isAssignableFrom(ActivityViewModel.class)) {
            return (T) new ActivityViewModel(mApplication, UserCenterModel.getInstance());
        }else if (modelClass.isAssignableFrom(EmojiViewModel.class)) {
            return (T) new EmojiViewModel(mApplication, UserCenterModel.getInstance());
        }else if (modelClass.isAssignableFrom(LiuHeQueryViewModel.class)) {
            return (T) new LiuHeQueryViewModel(mApplication, UserCenterModel.getInstance());
        }else if (modelClass.isAssignableFrom(SearchUserViewModel.class)) {
            return (T) new SearchUserViewModel(mApplication, UserCenterModel.getInstance());
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
