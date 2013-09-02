package tv.pps.tj.ppsdemo.data.cache;

import android.content.Context;
import android.content.SharedPreferences;
import com.xengine.android.data.cache.XDataSource;
import tv.pps.tj.ppsdemo.ui.FragmentChannel;

/**
 * 记录全局都要用到的状态。
 * 存储在SharedPreferences中。
 * 包括当前登陆的用户名、密码和通信token。
 * Created by jasontujun.
 * Date: 12-2-23
 * Time: 下午3:34
 */
public class GlobalStateSource implements XDataSource {

    private static final String PREF_NAME = "ppsdemo.globalState";

    private SharedPreferences pref;

    private static final String CHANNEL_FRAME_DISPLAY_MODE = "channelFrameDisplayMode";
    private static final String PROGRAM_LIST_XML_TIME_STAMP = "programListXmlTimeStamp";
    private static final String PROGRAM_LIST_XML_FILE_NAME = "programListXmlFileName";

    /**
     * 全局状态数据源
     * @param context 请使用由getApplicationContext()获得的context
     */
    public GlobalStateSource(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public String getSourceName() {
        return SourceName.GLOBAL_STATE;
    }

    public long getProgramListXmlUpdateTimeStamp() {
        return pref.getLong(PROGRAM_LIST_XML_TIME_STAMP, 0);
    }

    public void setProgramListXmlUpdateTimeStamp(long timeStamp) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(PROGRAM_LIST_XML_TIME_STAMP, timeStamp);
        editor.commit();
    }

    public String getProgramListXmlFileName() {
        return pref.getString(PROGRAM_LIST_XML_FILE_NAME, null);
    }

    public void setProgramListXmlFileName(String fileName) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PROGRAM_LIST_XML_FILE_NAME, fileName);
        editor.commit();
    }

    public int getChannelFrameMode() {
        return pref.getInt(CHANNEL_FRAME_DISPLAY_MODE, FragmentChannel.MODE_LISTVIEW);
    }

    public void setChannelFrameMode(int mode) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(CHANNEL_FRAME_DISPLAY_MODE, mode);
        editor.commit();
    }
}
