package com.ox.mylibrary.activityLauncher;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.text.MessageFormat;

/**
 * Created by ox on 16/11/15.
 */
public class Launcher {

    protected final Intent intent;
    protected final Activity activity;

    public Launcher(Activity activity, Class<? extends Activity> clz) {
        intent = new Intent(activity, clz);
        this.activity = activity;
    }

    /**
     * start activity
     */
    public void start() {
        startActivity(false, 0);
    }

    /**
     * start activity for result
     *
     * @param reqCode request code
     */
    public void startForResult(int reqCode) {
        startActivity(true, reqCode);
    }

    private void startActivity(boolean isForResult, int reqCode) {
        try {
            String missParam = checkParams();
            if (TextUtils.isEmpty(missParam)) {
                if (isForResult) {
                    activity.startActivity(intent);
                } else {
                    activity.startActivityForResult(intent, reqCode);
                }
            } else {
                throwParamException(this.getClass().getName(), missParam);
            }
        } catch (IllegalAccessException | LaunchParamException e) {
            e.printStackTrace();
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
            if (field.isAnnotationPresent(OX_ParamForce.class)) {
                field.setAccessible(true);
                String missParam = "";
                missParam = (String) field.get(missParam);
                if (!intent.hasExtra(missParam)) {
                    return missParam;
                }
            }
        }
        return "";
    }

    private void throwParamException(String activityName, String missParam) throws LaunchParamException {
        throw new LaunchParamException(
                MessageFormat.format(
                        "{0} activity 启动异常： 请传入参数 {1}",
                        activityName, missParam
                )
        );
    }

}
