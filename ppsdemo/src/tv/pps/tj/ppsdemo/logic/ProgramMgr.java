package tv.pps.tj.ppsdemo.logic;

import android.content.Context;
import com.xengine.android.data.cache.DefaultDataRepo;
import com.xengine.android.system.file.XAndroidFileMgr;
import com.xengine.android.utils.XStringUtil;
import tv.pps.tj.ppsdemo.data.cache.GlobalStateSource;
import tv.pps.tj.ppsdemo.data.cache.ProgramBaseSource;
import tv.pps.tj.ppsdemo.data.cache.ProgramDetailSource;
import tv.pps.tj.ppsdemo.data.cache.SourceName;
import tv.pps.tj.ppsdemo.data.model.ProgramBase;
import tv.pps.tj.ppsdemo.data.xml.ProgramListParser;
import tv.pps.tj.ppsdemo.session.StatusCode;
import tv.pps.tj.ppsdemo.session.api.ProgramAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-30
 * Time: 下午3:40
 * To change this template use File | Settings | File Templates.
 */
public class ProgramMgr {
    private static ProgramMgr instance;

    public synchronized static ProgramMgr getInstance() {
        if(instance == null) {
            instance = new ProgramMgr();
        }
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    private static final long REFRESH_INTERVAL = 1*60*60*1000;

    private GlobalStateSource mGlobalStateSource;
    private ProgramBaseSource mProgramBaseSource;
    private ProgramDetailSource mProgramDetailSource;

    private ProgramMgr() {
        DefaultDataRepo repo = DefaultDataRepo.getInstance();
        mGlobalStateSource = (GlobalStateSource) repo.getSource(SourceName.GLOBAL_STATE);
        mProgramBaseSource = (ProgramBaseSource) repo.getSource(SourceName.PROGRAM_BASE);
        mProgramDetailSource = (ProgramDetailSource) repo.getSource(SourceName.PROGRAM_DETAIL);
    }

    /**
     * 获取节目列表
     * @param channelId
     * @return
     */
    public boolean getProgramList(Context context, String channelId) {
        List<ProgramBase> programBaseList = null;
        boolean result = false;
        // 如果更新时间小于REFRESH_INTERVAL，且xml文件存在，则重新解析上次下载的xml文件
        long programListTimeStamp = mGlobalStateSource.getProgramListXmlUpdateTimeStamp();
        String programListXmlFileName = mGlobalStateSource.getProgramListXmlFileName();
        if (System.currentTimeMillis() - programListTimeStamp < REFRESH_INTERVAL
                && !XStringUtil.isNullOrEmpty(programListXmlFileName)) {
            File xmlDir = XAndroidFileMgr.getInstance().getDir(SystemMgr.DIR_DATA_XML);
            File xmlFile = new File(xmlDir, programListXmlFileName);
            if (xmlFile.exists()) {
                try {
                    programBaseList = ProgramListParser.parse(new FileInputStream(xmlFile));
                    result = programBaseList != null;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        // 否则重新下载并解析
        if (!result) {
            programBaseList = new ArrayList<ProgramBase>();
            int resultCode = new ProgramAPI(context).getProgramList(programBaseList, channelId);
            if (result = StatusCode.isSuccess(resultCode)) {
                mGlobalStateSource.setProgramListXmlUpdateTimeStamp(System.currentTimeMillis());
                mGlobalStateSource.setProgramListXmlFileName(channelId + ".xml");// TODO
            }
        }

        if (result) {
            mProgramBaseSource.setAutoNotifyListeners(false);// 关闭自动通知功能，因为对数据源同时做两次修改
            mProgramBaseSource.clear();
            mProgramBaseSource.addAll(programBaseList);
            mProgramBaseSource.notifyDataChanged();
            mProgramBaseSource.setAutoNotifyListeners(true);// 重新开启自动通知功能
        }
        return result;
    }

    /**
     * 获取节目详情
     * @param programId
     * @return
     */
    public boolean getProgramDetail(String programId) {
        return false;
    }
}
