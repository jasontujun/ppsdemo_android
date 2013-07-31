package tv.pps.tj.ppsdemo.session.api;

import android.content.Context;
import android.content.res.Resources;
import com.xengine.android.session.http.XHttp;
import com.xengine.android.utils.XLog;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import tv.pps.tj.ppsdemo.R;
import tv.pps.tj.ppsdemo.data.model.ProgramBase;
import tv.pps.tj.ppsdemo.session.HttpClientHolder;
import tv.pps.tj.ppsdemo.session.StatusCode;

import java.util.List;

/**
 * Created by fdp.
 * Modified by jasontujun.
 * Date: 12-7-19
 * Time: 下午12:59
 */
public class ProgramAPI {
    private XHttp http;

    private String hostUrl;
    private String apiProgramListUrl;
    private String apiProgramDetailUrl;

    public ProgramAPI(Context context) {
        http = HttpClientHolder.getMainHttpClient();

        Resources res = context.getResources();
        hostUrl = res.getString(R.string.host);
        apiProgramListUrl = res.getString(R.string.program_list);
        apiProgramDetailUrl = res.getString(R.string.program_detail);
    }

    /**
     * 获取某个频道下的节目列表
     * @param programList
     * @param channelId
     * @return
     */
    public int getProgramList(List<ProgramBase> programList, String channelId) {
        String url = hostUrl + apiProgramListUrl + channelId +".xml.zip";
        XLog.d("API", "request url：" + url);

        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = http.execute(httpGet, false);
        if(response == null) {
            return StatusCode.HTTP_EXCEPTION;
        }

        int status = response.getStatusLine().getStatusCode();
        if(StatusCode.isSuccess(status)) {
            // TODO
        }
        return status;
    }
}
