package com.onektower.accessiblityservice;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhwilson on 2016/12/2.
 */
public class NRAccessiblityService extends AccessibilityService {
    private List<AccessibilityNodeInfo> infos;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        infos = new ArrayList<>();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int eventType = accessibilityEvent.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED://通知栏提示
                Log.i("zhwilson", "TYPE_NOTIFICATION_STATE_CHANGED");
                List<CharSequence> texts = accessibilityEvent.getText();
                if (texts != null && texts.size() > 0) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        Log.i("zhwilson", "content:" + content);
//                        if (content.contains("QQ")){
                        if ("com.tencent.mobileqq".equals(accessibilityEvent.getPackageName())) {
                            //模拟打开
                            if (accessibilityEvent.getParcelableData() != null && accessibilityEvent.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) accessibilityEvent.getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                    Log.i("zhwilson", "进入qq");
                                } catch (PendingIntent.CanceledException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗口状态发生变化
                String className = accessibilityEvent.getClassName().toString();
                Log.i("zhwilson", "className:" + className);
                if ("com.tencent.mobileqq.activity.SplashActivity".equals(className)) {

                }
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }
}
