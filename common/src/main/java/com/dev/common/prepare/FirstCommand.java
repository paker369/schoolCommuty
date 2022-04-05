package com.dev.common.prepare;

import android.util.Log;

import com.dev.common.BaseApplication;
import com.dev.common.database.dynamic.Dynamic;
import com.dev.common.database.news.News;
import com.dev.common.database.user.User;
import com.dev.common.utils.data.Collections;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.dev.common.database.DaoProvider.dynamicDao;
import static com.dev.common.database.DaoProvider.newsDao;
import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 1/23/21
 */
public class FirstCommand {

    public static void command() {
        setupAdminUser();
        setupNormalUser();
        setupDynamic();
//        copy();
        setupNews();
    }

    public static void setupNews() {
        News news = new News(
                "新闻观察：美国多地疫苗接种乱象频发",
                "疫苗储存也是个难题。目前，美国广泛分发的新冠疫苗必须在零下70℃的温度下保存，美国一些小型医院和机构缺少符合要求的冷藏柜，并且已出现数起因冷藏柜出现问题引发的混乱事件。美国华盛顿州西雅图一存储疫苗的冷藏柜出现故障，致1650剂新冠疫苗面临过期风险。西雅图相关卫生机构于28日深夜紧急发布预约接种消息，称这批疫苗将在29日早间过期。消息发布后，数百人连夜赶来接种疫苗，有的人甚至穿着睡衣赶赴现场。而在佛罗里达州，一名工作人员不小心关闭了冷藏柜的电源，导致1000多剂新冠疫苗被毁。\n" +
                        "\n" +
                        "　　分析认为，种种接种乱象背后凸显了美国医疗体系的弊端和运转紊乱，没有统一的医疗系统，缺乏全国统一的计划和行动。美国总统拜登日前将其新冠疫苗接种目标从就职百日内1亿剂，即每天100万剂，提高到每天150万剂；拜登还承诺将疫苗分发速度提升大约16%。不为，种种接种乱象背后凸显了美国医疗体系的弊端和运转紊乱，没有统一的医疗系统，缺乏全国统一的计划和行动。美国总统拜登日前将其新冠疫苗接种目标从就职百日内1亿剂，即每天100万剂，提高到每天150万剂；拜登还承诺将疫苗分发速度提升大约16%。为，种种接种乱象背后凸显了美国医疗体系的弊端和运转紊乱，没有统一的医疗系统，缺乏全国统一的计划和行动。美国总统拜登日前将其新冠疫苗接种目标从就职百日内1亿剂，即每天100万剂，提高到每天150万剂；拜登还承诺将疫苗分发速度提升大约16%。过有报道指出，美国的接种行动可能要持续到今年秋季甚至冬季；甚至有美国媒体评论称，按照这样的疫苗接种情况，美国大约需要数年才能控制疫情。",
                "http://p5.img.cctvpic.com/photoworkspace/2021/01/31/2021013110382345880.png", "新闻");
        newsDao().insert(news);
        news.category = "机械";
        newsDao().insert(news);
        news.category = "科技";
        newsDao().insert(news);
    }

    public static void setupAdminUser() {
        User admin = new User("00000000000", "admin", "admin", null, 1, 0);
        admin.name = "admin";
        admin.isAdmin = true;
        userDao().insert(admin);
    }

    public static void setupNormalUser() {
        List<User> users = new ArrayList<>();

        List<String> nicknames = Collections.mutableListOf("赵四", "小宝", "张三", "李四", "王五", "seven");
        for (int i = 0; i < nicknames.size(); i++) {
            User user = new User("1553984974" + i, "123456", nicknames.get(i), null, i + 1, 0);
            users.add(user);
        }

        for (int i = 0; i < 10; i++) {
            User user = new User("1833999291" + i, "123456", "", null, 1 + i, 1);
            users.add(user);
        }
        userDao().insert(users);
    }

    private static void setupDynamic() {
        User admin = userDao().findAdmin();
        if (admin == null) return;
        Dynamic bean1 = new Dynamic(admin.id, "欢迎使用", "欢迎使用欢迎使用欢迎使用欢迎使用", null);
        Dynamic bean2 = new Dynamic(admin.id, "公告", "动态功能正式上线啦！！动态功能正式上线啦！！动态功能正式上线啦！！", null);
        dynamicDao().insert(bean1, bean2);
        List<Dynamic> dynamic = dynamicDao().findAll();
        for (Dynamic dynamic1 : dynamic) {
            Log.i("guolong", dynamic1.toString());
        }
    }

    public static void copy() {
        new Thread(() -> {
            try {
                copyAssets("books", BaseApplication.app.getExternalCacheDir() + File.separator + "books");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("guolong", e.getMessage(), e);
            }
        }).start();
    }

    private static void copyAssets(String assetDir, String dir) {
        String[] files;
        try {
            // 获得Assets一共有几多文件
            files = BaseApplication.app.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            Log.i("guolong", e1.getMessage(), e1);
            return;
        }
        File mWorkingPath = new File(dir);
        // 如果文件路径不存在
        if (!mWorkingPath.exists()) {
            // 创建文件夹
            if (!mWorkingPath.mkdirs()) {
                // 文件夹创建不成功时调用
                Log.i("guolong", "failed create dir");
            }
        }
        for (String file : files) {
            try {
                // 获得每个文件的名字
                // 根据路径判断是文件夹还是文件
                if (!file.contains(".")) {
                    if (0 == assetDir.length()) {
                        copyAssets(file, dir + file + "/");
                    } else {
                        copyAssets(assetDir + "/" + file, dir + "/"
                                + file + "/");
                    }
                    continue;
                }
                File outFile = new File(mWorkingPath, file);
                if (outFile.exists())
                    outFile.delete();
                InputStream in = null;
                if (0 != assetDir.length())
                    in = BaseApplication.app.getAssets().open(assetDir + "/" + file);
                else
                    in = BaseApplication.app.getAssets().open(file);
                OutputStream out = new FileOutputStream(outFile);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
