package tv.pps.tj.ppsdemo.logic;

import android.content.Context;
import com.xengine.android.data.cache.DefaultDataRepo;
import com.xengine.android.data.db.XSQLiteHelper;
import com.xengine.android.system.file.XAndroidFileMgr;
import com.xengine.android.system.file.XFileMgr;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.data.cache.GlobalStateSource;
import tv.pps.tj.ppsdemo.data.cache.ImageSource;
import tv.pps.tj.ppsdemo.data.cache.ProgramBaseSource;
import tv.pps.tj.ppsdemo.data.cache.ProgramDetailSource;
import tv.pps.tj.ppsdemo.engine.*;

import java.io.File;

/**
 * Created by jasontujun.
 * Date: 12-4-27
 * Time: 下午3:08
 */
public class SystemMgr {
    private static SystemMgr instance;

    public synchronized static SystemMgr getInstance() {
        if(instance == null) {
            instance = new SystemMgr();
        }
        return instance;
    }

    private SystemMgr() {}


    public static final int DIR_DATA_XML = 20;

    /**
     * 初始化xengine的一些重要模块。
     * 在第一个Activity的最开始执行。
     * @param context
     */
    public void initEngine(Context context) {
        // 初始化屏幕
        ScreenHolder.init(context);
        // 初始化文件管理模块
        XFileMgr fileMgr = XAndroidFileMgr.getInstance();
        fileMgr.setRootName("ppsdemo");
        fileMgr.setDir(XFileMgr.FILE_TYPE_TMP, "tmp", true);
        fileMgr.setDir(XFileMgr.FILE_TYPE_PHOTO, "photo", true);
        fileMgr.setDir(DIR_DATA_XML, "data" + File.separator + "xml", false);
        // 初始化网络模块
        HttpClientHolder.init(context);
        DownloadMgrHolder.init(HttpClientHolder.getImageHttpClient(),
                ScreenHolder.getInstance().getScreenWidth(),
                ScreenHolder.getInstance().getScreenHeight());
        UploadMgrHolder.init(HttpClientHolder.getImageHttpClient());
        // 初始化图片加载模块
        MyImageViewLocalLoader.getInstance().init(
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small
        );
        MyImageSwitcherLocalLoader.getInstance().init(
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small
        );
        MyImageScrollLocalLoader.getInstance().init(
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small
        );
        MyImageScrollRemoteLoader.getInstance().init(
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small,
                R.drawable.default_image_bg_small
        );
        // 初始化手机功能管理器
//        mMobileMgr = new XAndroidMobileMgr(this, screen.getScreenWidth(), screen.getScreenHeight());
    }

    /**
     * 初始化系统。
     * @param context
     */
    public void initSystem(Context context) {
        initDB(context);
        initDataSources(context);
    }


    /**
     * 初始化数据库
     * @param context
     */
    private void initDB(Context context) {
        // 初始化数据库
        XSQLiteHelper.initiate(context, "ppsdemo_db", 1);
    }

    /**
     * 初始化数据源。
     * 一部分是空数据源。
     * 一部分从sharePreference导入。
     * 一部分从SQLite导入。
     */
    private void initDataSources(Context context) {
        DefaultDataRepo repo = DefaultDataRepo.getInstance();
        repo.registerDataSource(new GlobalStateSource(context));
        repo.registerDataSource(new ImageSource());
        repo.registerDataSource(new ProgramBaseSource());
        repo.registerDataSource(new ProgramDetailSource());
    }

    public void clearSystem() {
        // clear image cache
        MyImageViewLocalLoader.getInstance().clearImageCache();
        MyImageSwitcherLocalLoader.getInstance().clearImageCache();
        MyImageScrollLocalLoader.getInstance().clearImageCache();
        MyImageScrollRemoteLoader.getInstance().clearImageCache();

        // clear tmp file
        XAndroidFileMgr.getInstance().clearDir(XFileMgr.FILE_TYPE_TMP);
        XAndroidFileMgr.getInstance().clearDir(XFileMgr.FILE_TYPE_PHOTO);

        // clear Mgr
//        LoginMgr.clearInstance();
//        BbsArticleMgr.clearInstance();
//        BbsBoardMgr.clearInstance();
//        BbsMailMgr.clearInstance();
//        BbsPersonMgr.clearInstance();

        // clear DataSource
        DefaultDataRepo.clearInstance();
    }
}
