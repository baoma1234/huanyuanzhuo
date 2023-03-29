package caixin.android.com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caixin.huanyu.R;

import caixin.android.com.utils.DpUtil;

public class ChatRoomUnreadView extends RelativeLayout {
    private int lastX;
    private int lastY;
    private TextView unreadTextView;
    private ImageView unreadImageView;

    public ChatRoomUnreadView(Context context) {
        super(context);
        initView(context);
    }

    public ChatRoomUnreadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public ChatRoomUnreadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_chat_room_unread_view, this, true);
        unreadTextView = findViewById(R.id.tv_unread_count);
    }

    public void setUnreadCount(int count) {
        if (unreadTextView != null) {
            if (count > 0) {
                unreadTextView.setVisibility(View.VISIBLE);
                if (count < 99) {
                    unreadTextView.setText(count + "");
                } else {
                    unreadTextView.setText("99+");
                }
            } else {
                unreadTextView.setVisibility(View.GONE);
            }
        }
    }
}
