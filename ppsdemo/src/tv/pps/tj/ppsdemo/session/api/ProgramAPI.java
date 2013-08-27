package tv.pps.tj.ppsdemo.session.api;

import android.content.Context;
import android.content.res.Resources;
import com.xengine.android.system.file.XAndroidFileMgr;
import com.xengine.android.utils.XLog;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.data.model.ProgramBase;
import tv.pps.tj.ppsdemo.data.model.ProgramDetail;
import tv.pps.tj.ppsdemo.data.xml.ProgramDetailParser;
import tv.pps.tj.ppsdemo.data.xml.ProgramListParser;
import tv.pps.tj.ppsdemo.engine.DownloadMgrHolder;
import tv.pps.tj.ppsdemo.logic.SystemMgr;
import tv.pps.tj.ppsdemo.session.StatusCode;
import tv.pps.tj.ppsdemo.util.ZipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by fdp.
 * Modified by jasontujun.
 * Date: 12-7-19
 * Time: 下午12:59
 */
public class ProgramAPI {

    private String hostUrl;
    private String apiProgramListUrl;
    private String apiProgramDetailUrl;

    public ProgramAPI(Context context) {
        Resources res = context.getResources();
        hostUrl = res.getString(R.string.host);
        apiProgramListUrl = res.getString(R.string.program_list);
        apiProgramDetailUrl = res.getString(R.string.program_detail);
    }

    /**
     * 获取某个频道下的节目列表
     * @param result
     * @param channelId
     * @return
     */
    public int getProgramList(List<ProgramBase> result, String channelId) {
        String url = hostUrl + apiProgramListUrl + channelId +".xml.zip";
        XLog.d("API", "request url：" + url);

        File dir = XAndroidFileMgr.getInstance().getDir(SystemMgr.DIR_DATA_XML);
        File zip = new File(dir, "tmp_list.zip");
        if (zip.exists())
            zip.delete();
        // 下载zip文件
        if (!DownloadMgrHolder.getDownloadMgr().download(url, zip))
            return StatusCode.HTTP_EXCEPTION;// 下载失败

        List<File> zipFileList = ZipUtil.getFileList(zip.getAbsolutePath(), false, true);
        if (zipFileList == null || zipFileList.size() == 0)
            return StatusCode.LOCAL_FILE_ERROR;// zip文件不合法
        // 获取目标xml文件名
        File xmlInZip = zipFileList.get(0);// 默认zip里只有一个xml
        File xml = new File(dir, xmlInZip.getName());// 本地的解压出来的xml文件
        if (xml.exists())
            xml.delete();// 如果旧的xml文件存在，删除之
        // 解压zip文件
        if (!ZipUtil.unZipFolder(zip.getAbsolutePath(), dir.getAbsolutePath())) {
            return StatusCode.LOCAL_FILE_UNZIP_ERROR;// zip解压失败
        }
        zip.delete();// 删除zip文件

        try {
            // 解析xml
            List<ProgramBase> parserResultList = ProgramListParser.parse(new FileInputStream(xml));
            if (parserResultList == null)
                return StatusCode.LOCAL_XML_PARSE_ERROR;// 解析失败
            result.addAll(parserResultList);
            return StatusCode.SUCCESS;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return StatusCode.LOCAL_FILE_NOT_FOUND_ERROR;
        }
    }

    /**
     * 获取某个节目的详细信息
     * @param result
     * @param programId
     * @return
     */
    public int getProgramDetail(ProgramDetail result, String programId) {
        String url = hostUrl + apiProgramDetailUrl + programId +".xml.zip";
        XLog.d("API", "request url：" + url);

        File dir = XAndroidFileMgr.getInstance().getDir(SystemMgr.DIR_DATA_XML);
        File zip = new File(dir, "tmp_detail.zip");
        if (zip.exists())
            zip.delete();
        // 下载zip文件
        if (!DownloadMgrHolder.getDownloadMgr().download(url, zip))
            return StatusCode.HTTP_EXCEPTION;// 下载失败

        List<File> zipFileList = ZipUtil.getFileList(zip.getAbsolutePath(), false, true);
        if (zipFileList.size() == 0)
            return StatusCode.LOCAL_FILE_ERROR;// zip文件不合法
        // 获取目标xml文件名
        File xmlInZip = zipFileList.get(0);// 默认zip里只有一个xml
        File xml = new File(dir, xmlInZip.getName());// 本地的解压出来的xml文件
        if (xml.exists())
            xml.delete();// 如果旧的xml文件存在，删除之
        // 解压zip文件
        if (!ZipUtil.unZipFolder(zip.getAbsolutePath(), dir.getAbsolutePath())) {
            return StatusCode.LOCAL_FILE_UNZIP_ERROR;// zip解压失败
        }
        zip.delete();// 删除zip文件

        try {
            // 解析xml
            if (!ProgramDetailParser.parse(new FileInputStream(xml), result))
                return StatusCode.LOCAL_XML_PARSE_ERROR;// 解析失败
            return StatusCode.SUCCESS;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return StatusCode.LOCAL_FILE_NOT_FOUND_ERROR;
        }
    }
}
