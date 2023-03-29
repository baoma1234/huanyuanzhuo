package caixin.android.com.http;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import caixin.android.com.Application;
import caixin.android.com.base.BaseModel;
import caixin.android.com.constant.Contact;
import caixin.android.com.constant.Extras;
import caixin.android.com.daomanager.ConversationDaoManager;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.entity.base.BaseResponse;
import caixin.android.com.entity.base.BaseWebSocketResponse;
import caixin.android.com.entity.chatroom.GetAdsRequest;
import caixin.android.com.http.basic.config.HttpCode;
import caixin.android.com.http.basic.config.HttpConfig;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.notifier.Notifier;
import caixin.android.com.view.activity.ChatRoomActivity;
import caixin.android.com.view.activity.LoginActivity;
import caixin.android.com.view.activity.MainActivity;
import caixin.android.com.entity.SendMessageResponse;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketManager {

    private static WebSocket mWebSocket;
    private static final String TAG = "WebSocketManager";
    private Context appContext;

    public static WebSocketManager INSTANCE;
    private Map<String, ServerMessage> stringListeners = new ConcurrentHashMap<>();
    private Map<String, ConnecteReceive> connectListeners = new ConcurrentHashMap<>();
    private boolean isConnecting = false;

    public synchronized static WebSocketManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WebSocketManager();
        }
        return INSTANCE;
    }

    public interface ServerMessage {
        public void onReceiveMessageFromServer(BaseWebSocketResponse baseWebSocketResponse);
    }

    public interface ConnecteReceive {
        public void onConnected(String msg);

        public void onConnectedException(String msg);
    }

    private long oldTime = 0;
    private int notLoginTime = 0;

    private void cleanAndGoLogin(String message) {
        Intent intent = new Intent(appContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Extras.INTENT_LOGIN_AGAIN, true);
        bundle.putString(Extras.INTENT_LOGIN_MESSAGE, message);
        intent.putExtras(bundle);
        Application.getInstance().setIsHttpLogin(false);
        appContext.startActivity(intent);
    }

    private WebSocket.StringCallback stringCallback = new WebSocket.StringCallback() {
        @Override
        public void onStringAvailable(String s) {
            Log.e(TAG, "onStringAvailable: " + s);
            BaseWebSocketResponse baseWebSocketResponse = null;
            try {
                baseWebSocketResponse = JSONObject.parseObject(s, BaseWebSocketResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Log.e(TAG, "onStringAvailable: " + baseWebSocketResponse.toString());
            if (baseWebSocketResponse.getErrorcode() == BaseResponse.ERRORCODE_TOKEN_INVAILABLE) {
                if (Math.abs(System.currentTimeMillis() - oldTime) < 30 * 1000) {
                    if (notLoginTime >= 1) {
                        notLoginTime = 0;
                        MMKVUtil.setToken("");
                        cleanAndGoLogin(baseWebSocketResponse.getMsg());
                    }
                    notLoginTime++;
                } else {
                    notLoginTime = 0;
                }
                oldTime = System.currentTimeMillis();
                return;
            }
            if (baseWebSocketResponse.getErrorcode() == 600) {
                if (stringListeners.containsKey(Extras.REQUEST_ACTION_LOGTOU)) {
                    stringListeners.get(Extras.REQUEST_ACTION_LOGTOU).onReceiveMessageFromServer(baseWebSocketResponse);
                }
                return;
            }
            Set entrys = stringListeners.entrySet(); // 1.获得所有的键值对Entry对象
            Iterator iter = entrys.iterator(); // 2.迭代出所有的entry
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Log.e(TAG, "onStringAvailable: " + entry.getKey());
                ((ServerMessage) entry.getValue()).onReceiveMessageFromServer(baseWebSocketResponse);
            }
            if (baseWebSocketResponse.getAct().equals(Extras.REQUEST_ACTION_FRIEND_APPLY)) {
                UserInfoEntity userInfo = MMKVUtil.getUserInfo();
                userInfo.setIsNew(1);
                MMKVUtil.setUserInfo(userInfo);
            }
        }
    };

    public void addServerReceive(String key, ServerMessage serverMessage) {
        Log.e(TAG, "addServerReceive: " + key);
        stringListeners.put(key, serverMessage);
    }

    private WebSocketManager.ServerMessage serverMessage = new WebSocketManager.ServerMessage() {
        @Override
        public void onReceiveMessageFromServer(BaseWebSocketResponse baseWebSocketResponse) {
            if (baseWebSocketResponse.getAct().equals(Extras.REQUEST_ACTION_SEND_MESSAGE) || baseWebSocketResponse.getAct().equals(Extras.REQUEST_ACTION_SEND_PIC)) {
                if (baseWebSocketResponse.getData() == null) {
                    return;
                }
                List<SendMessageResponse> userResponseList = JSONObject.parseArray(baseWebSocketResponse.getData().toString(), SendMessageResponse.class);
                if (userResponseList == null || userResponseList.size() == 0)
                    return;
                SendMessageResponse sendMessageResponse = userResponseList.get(0);
                insertConversation(sendMessageResponse);
            }
        }
    };

    private boolean isRelatedTopAccount(SendMessageResponse sendMessageResponse) {
        if (ChatRoomActivity.getInstance().getmType() == ChatRoomActivity.TYPE_GROUP) {
            if (sendMessageResponse.getTotype() == SendMessageResponse.TOTYPE_GROUP) {
                if (sendMessageResponse.getGroupId() == ChatRoomActivity.getInstance().getGroupId())
                    return true;
            }
        } else if (ChatRoomActivity.getInstance().getmType() == ChatRoomActivity.TYPE_FRIEND) {
            if (sendMessageResponse.getSendId() == ChatRoomActivity.getInstance().getFriendId() && sendMessageResponse.getGetId() == MMKVUtil.getUserInfo().getId())
                return true;
            return sendMessageResponse.getSendId() == MMKVUtil.getUserInfo().getId() && sendMessageResponse.getGetId() == ChatRoomActivity.getInstance().getFriendId();
        }
        return false;
    }

    private Notifier notifier;

    public void insertConversation(SendMessageResponse sendMessageResponse) {
        if (ChatRoomActivity.isFront) {
            if (ChatRoomActivity.getInstance() != null) {
                if (isRelatedTopAccount(sendMessageResponse)) {
                    return;
                }
            }
        }
        sendMessageResponse.setIsread(0);
        if (notifier == null)
            notifier = new Notifier(appContext);
        notifier.vibrateAndPlayTone();
        ConversationDaoManager.getInstance().insertOrReplace(sendMessageResponse, true);
    }

    public void removeServerReceive(String key) {
        stringListeners.remove(key);
    }

    public void addConnecteListener(String key, ConnecteReceive connecteReceive) {
        connectListeners.put(key, connecteReceive);
    }

    public void removeConnectReceive(String key) {
        connectListeners.remove(key);
    }

    public void onUserException(String exception) {
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.putExtra(exception, true);
        appContext.startActivity(intent);
    }

    public int send(String jsonString) {
        Log.e(TAG, "send: jsonString" + jsonString);
        if (mWebSocket == null || !mWebSocket.isOpen()) {
            close();
            connectWebSocket(false);
            return HttpCode.CONNECT_STATE_DISCONNECTED;
        } else {
            mWebSocket.send(jsonString);
        }
        return HttpCode.CONNECT_STATE_CONNECTED;
    }

    public void init(Context context) {
        this.appContext = context;
    }

    private Thread pingThread;

    public void connectWebSocket(boolean isInit) {
        if (isConnecting)
            return;
        isConnecting = true;
        addServerReceive("CONVERSATION", serverMessage);
        AsyncHttpClient.getDefaultInstance().websocket(
                HttpConfig.WEBSOCKET_ADDRESS,// webSocket地址
                HttpConfig.WEBSOCKET_PROTOCOL,// 端口
                (ex, webSocket) -> {
                    if (ex != null) {
                        isConnecting = false;
                        ex.printStackTrace();
                        Log.e(TAG, "onCompleted:  ex = " + ex.toString() + ex.getMessage());
//                        onUserException("");
                        MMKVUtil.setConnectedState(HttpCode.CONNECT_STATE_DISCONNECTED);
                        for (String key : connectListeners.keySet()) {
                            connectListeners.get(key).onConnectedException(ex.toString());
                        }
                        return;
                    }
                    isConnecting = false;
                    MMKVUtil.setConnectedState(HttpCode.CONNECT_STATE_CONNECTED);
                    mWebSocket = webSocket;
                    webSocket.setStringCallback(stringCallback);
                    for (String key : connectListeners.keySet()) {
                        connectListeners.get(key).onConnected(ex.toString());
                    }
                    if (Application.getInstance().isIsHttpLogin()) {
                        UserCenterModel.getInstance().login(MMKVUtil.getUserInfo().getId(), new BaseModel.Callback() {
                            @Override
                            public void onSuccess(Object data, String mes) {
                            }

                            @Override
                            public void onFailure(String msg) {

                            }

                            @Override
                            public void onDisconnected(String msg) {

                            }
                        });
                    }
                    if (pingThread == null) {
                        pingThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (!Thread.currentThread().isInterrupted()) {
                                    try {
                                        UserCenterModel.getInstance().ping();
                                        Thread.sleep(5 * 1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else {
                        pingThread.interrupt();
                    }
                    pingThread.start();
                    webSocket.setDataCallback((emitter, bb) -> {
                        Log.e(TAG, "onStringAvailable: I got some bytes!");
                        bb.recycle();
                    });
                    webSocket.setClosedCallback(ex1 -> {
                        Log.e(TAG, "setClosedCallback: " + ex1.getMessage());
                    });
                    webSocket.setPongCallback(new WebSocket.PongCallback() {
                        @Override
                        public void onPongReceived(String s) {
                            Log.e(TAG, "onPongReceived: ");
                        }
                    });
                });
    }

    public void close() {
        if (mWebSocket != null) {
            mWebSocket.setStringCallback(null);
            mWebSocket.setDataCallback(null);
            mWebSocket.close();
            mWebSocket = null;
            removeServerReceive("CONVERSATION");
        }
    }

    public boolean isConnect() {
        if (mWebSocket != null) {
            return mWebSocket.isOpen();
        }
        return false;
    }
}
