package tv.pps.tj.ppsdemo.logic;

import android.content.Context;
import com.xengine.android.data.cache.DefaultDataRepo;
import com.xengine.android.data.db.XSQLiteHelper;
import com.xengine.android.system.file.XAndroidFileMgr;
import com.xengine.android.system.file.XFileMgr;
import tv.pps.tj.ppsdemo.data.cache.GlobalStateSource;
import tv.pps.tj.ppsdemo.data.cache.ProgramBaseSource;
import tv.pps.tj.ppsdemo.data.cache.ProgramDetailSource;
import tv.pps.tj.ppsdemo.session.HttpClientHolder;

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
     * 在第一个Activity加载时执行.(少量快速)
     * @param context
     */
    private void preInit(Context context) {
        // 初始化文件管理器
        XFileMgr fileMgr = XAndroidFileMgr.getInstance();
        fileMgr.setRootName("pm25");
        fileMgr.setDir(XFileMgr.FILE_TYPE_TMP, "tmp", true);
        fileMgr.setDir(XFileMgr.FILE_TYPE_PHOTO, "photo", true);
        // 初始化网络
        HttpClientHolder.init(context);
        // 初始化手机功能管理器
//        XScreen screen = new XAndroidScreen(context);
//        mMobileMgr = new XAndroidMobileMgr(this, screen.getScreenWidth(), screen.getScreenHeight());
    }


    /**
     * 初始化系统。
     * @param context
     */
    public void initSystem(Context context) {
        preInit(context);
        clearSystem();
        initFileMgr();
        initDB(context);
        initDataSources(context);
    }

    private void initFileMgr() {
        // 文件夹管理器
        XFileMgr fileMgr = XAndroidFileMgr.getInstance();
        fileMgr.setDir(DIR_DATA_XML, "data" + File.separator + "xml", false);
        // 图片下载管理器
//        ImgMgrHolder.getImgDownloadMgr().setDownloadDirectory(
//                fileMgr.getDir(XFileMgr.FILE_TYPE_TMP).getAbsolutePath());
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
        repo.registerDataSource(new ProgramBaseSource());
        repo.registerDataSource(new ProgramDetailSource());
    }

    public void clearSystem() {
        // clear image cache
//        ImageLoader.getInstance().clearImageCache();

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
