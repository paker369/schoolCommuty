package com.dev.common;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dev.common.prepare.FirstCommand;
import com.dev.common.utils.SpUtil;

import static com.dev.common.utils.SpUtil.KEY_FIRST_COMMAND;

/**
 * @author long.guo
 * @since 1/21/21
 */
public class BaseApplication extends Application {

    public static BaseApplication app;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        app = this;
        super.onCreate();
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);

        if (!SpUtil.getInstance().getBoolean(KEY_FIRST_COMMAND)) {
            FirstCommand.command();
            SpUtil.getInstance().save(KEY_FIRST_COMMAND, true);
        }

    }
}
