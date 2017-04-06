package com.ox.mylibrary.activityLauncher;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.ox.mylibrary.util.ManageAcAnim;
import com.ox.mylibrary.util.ManageLog;

import java.lang.reflect.Field;
import java.text.MessageFormat;

/**
 * Created by ox on 16/11/15.
 */
public class Launcher {

    protected final Intent intent;
    protected final Activity activity;

    private AcAnimListener defaultAnimListener = new AcAnimListener() {
        @Override
        public void override() {
            ManageAcAnim.fadeInFadeOut(activity);
        }
    };

    public Launcher(Activity activity, Class<? extends Activity> clz) {
        intent = new Intent(activity, clz);
        this.activity = activity;
    }

    public void start() {
        startActivity(false, 0);
        overridePendingTransition(null);
    }

    public void start(AcAnimListener animListener) {
        startActivity(false, 0);
        overridePendingTransition(animListener);
    }

    public void startForResult(int reqCode) {
        startActivity(true, reqCode);
        overridePendingTransition(null);
    }

    public void startForResult(int reqCode, AcAnimListener animListener) {
        startActivity(true, reqCode);
        overridePendingTransition(animListener);
    }

    private void startActivity(boolean isForResult, int reqCode) {
        try {
            String missParamName = checkParams();
            if (TextUtils.isEmpty(missParamName)) {
                if (isForResult) {
                    activity.startActivityForResult(intent, reqCode);
                } else {
                    activity.startActivity(intent);
                }
            } else {
                throwParamException(this.getClass().getName(), missParamName);
            }
        } catch (IllegalAccessException | LaunchParamException e) {
            ManageLog.e(e);
        }
    }

    private void overridePendingTransition(AcAnimListener animListener) {
        if (animListener != null) {
            animListener.override();
        } else {
            defaultAnimListener.override();
        }
    }


    /**
     * 检查启动activity 所必须的参数是否都传了
     *
     * @return 该传未传的参数的名字
     * @throws IllegalAccessException Field.get(Object) may cause this exception
     */
    private String checkParams() throws IllegalAccessException {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ParamForce.class)) {
                field.setAccessible(true);
                String missParam = "";
                missParam = (String) field.get(missParam);
                if (!intent.hasExtra(missParam)) {
                    return field.getName();
                }
            }
        }
        return "";
    }

    private void throwParamException(String activityName, String missParamName) throws LaunchParamException {
        throw new LaunchParamException(
                MessageFormat.format(
                        "{0} activity 启动异常： 请传入参数 {1}",
                        activityName, missParamName
                )
        );
    }

    public interface AcAnimListener {
        void override();
    }

}
