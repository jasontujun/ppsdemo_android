package tv.pps.tj.ppsdemo.data.xml;

import android.util.Xml;
import com.xengine.android.utils.XLog;
import com.xengine.android.utils.XStringUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import tv.pps.tj.ppsdemo.data.model.Episode;
import tv.pps.tj.ppsdemo.data.model.ProgramDetail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析节目详情xml文件的解析器。
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-29
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
public class ProgramDetailParser {

    private static final String TAG = ProgramListParser.class.getSimpleName();

    /**
     * TIP 解析原则：节点开始标签创建对象或容器，节点结束标签处理之并销毁
     * @param is
     * @return
     */
    public static ProgramDetail parse(InputStream is) {
        try {
            ProgramDetail programDetail = null;
            List<Episode> normalEpisodeList = null;
            List<Episode> hqEpisodeList = null;
            List<Episode> trailerEpisodeList = null;
            Episode episode = null;
            boolean isThirdPart = false;
            Map<String, String> platformMap = null;

            XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例
            parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        // 节目数据
                        if (parser.getName().equals("Sub")) {
                            programDetail = new ProgramDetail();
                            String tm = parser.getAttributeValue(null, "tm");
                            XLog.d(TAG, "<Sub tm=" + tm);
                        } else if (parser.getName().equals("id")) {
                            eventType = parser.next();
                            String id = parser.getText();
                            XLog.d(TAG, "<id>" + id + "</id>");
                            programDetail.setId(id);
                        } else if (parser.getName().equals("inton")) {
                            eventType = parser.next();
                            String inton = parser.getText();
                            XLog.d(TAG, "<inton>" + inton + "</inton>");
                            programDetail.setIntroduction(inton);
                        } else if (parser.getName().equals("type")) {
                            eventType = parser.next();
                            String type = parser.getText();
                            XLog.d(TAG, "<type>" + type + "</type>");
                            programDetail.setType(type);
                        } else if (parser.getName().equals("region")) {
                            eventType = parser.next();
                            String region = parser.getText();
                            XLog.d(TAG, "<region>" + region + "</region>");
                            programDetail.setRegion(region);
                        } else if (parser.getName().equals("dirt")) {
                            eventType = parser.next();
                            String dirt = parser.getText();
                            XLog.d(TAG, "<dirt>" + dirt + "</dirt>");
                            programDetail.setDirector(dirt);
                        } else if (parser.getName().equals("actor")) {
                            eventType = parser.next();
                            String actors = parser.getText();
                            XLog.d(TAG, "<actor>" + actors + "</actor>");
                            if (!XStringUtil.isNullOrEmpty(actors))
                                programDetail.setActor(actors.split(","));
                        } else if (parser.getName().equals("vote_count")) {
                            eventType = parser.next();
                            String vote_count = parser.getText();
                            XLog.d(TAG, "<vote_count>" + vote_count + "</vote_count>");
                            if (!XStringUtil.isNullOrEmpty(vote_count))
                                programDetail.setVoteCount(Integer.parseInt(vote_count));
                        } else if (parser.getName().equals("block")) {
                            eventType = parser.next();
                            String block = parser.getText();
                            XLog.d(TAG, "<block>" + block + "</block>");
                            if (!XStringUtil.isNullOrEmpty(block))
                                programDetail.setBlackList(block.split(";"));
                        } else if (parser.getName().equals("wlock")) {
                            eventType = parser.next();
                            String wlock = parser.getText();
                            XLog.d(TAG, "<wlock>" + wlock + "</wlock>");
                            if (!XStringUtil.isNullOrEmpty(wlock))
                                programDetail.setWhiteList(wlock.split(";"));
                        } else if (parser.getName().equals("name")) {
                            eventType = parser.next();
                            String name = parser.getText();
                            XLog.d(TAG, "<name>" + name + "</name>");
                            programDetail.setName(name);
                        } else if (parser.getName().equals("vote")) {
                            eventType = parser.next();
                            String vote = parser.getText();
                            XLog.d(TAG, "<vote>" + vote + "</vote>");
                            if (!XStringUtil.isNullOrEmpty(vote))
                                programDetail.setVote(Float.parseFloat(vote));
                        } else if (parser.getName().equals("bkid")) {
                            eventType = parser.next();
                            String bkid = parser.getText();
                            XLog.d(TAG, "<bkid>" + bkid + "</bkid>");
                            programDetail.setBkId(bkid);
                        } else if (parser.getName().equals("multi")) {
                            eventType = parser.next();
                            String multi = parser.getText();
                            XLog.d(TAG, "<multi>" + multi + "</multi>");
                            if (!XStringUtil.isNullOrEmpty(multi))
                                programDetail.setFilm(multi.equals("1"));
                        } else if (parser.getName().equals("vip")) {
                            eventType = parser.next();
                            String vip = parser.getText();
                            XLog.d(TAG, "<vip>" + vip + "</vip>");
                            if (!XStringUtil.isNullOrEmpty(vip))
                                programDetail.setVipLevel(Integer.parseInt(vip));
                        } else if (parser.getName().equals("vopt")) {
                            eventType = parser.next();
                            String vopt = parser.getText();
                            XLog.d(TAG, "<vopt>" + vopt + "</vopt>");
                            if (!XStringUtil.isNullOrEmpty(vopt))
                                programDetail.setVipVisibilityLevel(Integer.parseInt(vopt));
                        } else if (parser.getName().equals("popt")) {
                            eventType = parser.next();
                            String popt = parser.getText();
                            XLog.d(TAG, "<popt>" + popt + "</popt>");
                            if (!XStringUtil.isNullOrEmpty(popt))
                                programDetail.setUserRequiredLevel(Integer.parseInt(popt));
                        } else if (parser.getName().equals("vlevel")) {
                            eventType = parser.next();
                            String vlevel = parser.getText();
                            XLog.d(TAG, "<vlevel>" + vlevel + "</vlevel>");
                            if (!XStringUtil.isNullOrEmpty(vlevel))
                                programDetail.setOnlineLevel(Integer.parseInt(vlevel));
                        } else if (parser.getName().equals("ct")) {
                            eventType = parser.next();
                            String ct = parser.getText();
                            XLog.d(TAG, "<ct>" + ct + "</ct>");
                            if (!XStringUtil.isNullOrEmpty(ct))
                                programDetail.setProgramLength(Integer.parseInt(ct));
                        } else if (parser.getName().equals("img")) {
                            eventType = parser.next();
                            String img = parser.getText();
                            XLog.d(TAG, "<img>" + img + "</img>");
                            programDetail.setPosterUrl(img);
                        } else if (parser.getName().equals("followable")) {
                            eventType = parser.next();
                            String followable = parser.getText();
                            XLog.d(TAG, "<followable>" + followable + "</followable>");
                            if (!XStringUtil.isNullOrEmpty(followable))
                                programDetail.setFollowable(followable.equals("1"));
                        } else if (parser.getName().equals("fn")) {
                            eventType = parser.next();
                            String fn = parser.getText();
                            XLog.d(TAG, "<fn>" + fn + "</fn>");
                            if (!XStringUtil.isNullOrEmpty(fn))
                                programDetail.setEntertainment(fn.equals("1"));
                        }
                        // 剧集列表
                        else if (parser.getName().equals("Channels")) {
                            normalEpisodeList = new ArrayList<Episode>();
                            hqEpisodeList = new ArrayList<Episode>();
                            trailerEpisodeList = new ArrayList<Episode>();
                            String Total = parser.getAttributeValue(null, "Total");
                            XLog.d(TAG, "<Channels Total=" + Total + ">");
                        }
                        // 单个剧集
                        else if (parser.getName().equals("Channel")) {
                            episode = new Episode();
                            String id = parser.getAttributeValue(null, "id");
                            XLog.d(TAG, "<Channel id=" + id);
                            episode.setId(id);
                            String url = parser.getAttributeValue(null, "url");
                            XLog.d(TAG, "     url=" + url);
                            episode.setUrl(url);
                            String gid = parser.getAttributeValue(null, "gid");
                            XLog.d(TAG, "     gid=" + gid);
                            episode.setGid(gid);
                            String fotm = parser.getAttributeValue(null, "fotm");
                            XLog.d(TAG, "     fotm=" + fotm);
                            episode.setFotm(fotm);
                            String lang = parser.getAttributeValue(null, "lang");
                            XLog.d(TAG, "     lang=" + lang);
                            episode.setUrl(lang);
                            String fsize = parser.getAttributeValue(null, "fsize");
                            XLog.d(TAG, "     fsize=" + fsize);
                            if (!XStringUtil.isNullOrEmpty(fsize))
                                episode.setFileSize(Long.parseLong(fsize));
                            String dl = parser.getAttributeValue(null, "dl");
                            XLog.d(TAG, "     dl=" + dl);
                            if (!XStringUtil.isNullOrEmpty(dl))
                                episode.setCanDownload(dl.equals("1"));
                            String tm = parser.getAttributeValue(null, "tm");
                            XLog.d(TAG, "     tm=" + tm);
                            if (!XStringUtil.isNullOrEmpty(tm))
                                episode.setTime(Long.parseLong(tm));
                            String ct = parser.getAttributeValue(null, "ct");
                            XLog.d(TAG, "     ct=" + ct);
                            if (!XStringUtil.isNullOrEmpty(ct))
                                episode.setLength(Integer.parseInt(ct));
                            String fmt = parser.getAttributeValue(null, "fmt");
                            XLog.d(TAG, "     fmt=" + fmt);
                            episode.setFormat(fmt);
                            String def = parser.getAttributeValue(null, "def");
                            XLog.d(TAG, "     def=" + def);
                            episode.setUrl(def);
                            String bitrate = parser.getAttributeValue(null, "bitrate");
                            XLog.d(TAG, "     bitrate=" + bitrate);
                            if (!XStringUtil.isNullOrEmpty(bitrate))
                                episode.setBitrate(Integer.parseInt(bitrate));
                            String aid = parser.getAttributeValue(null, "aid");
                            XLog.d(TAG, "     aid=" + aid);
                            episode.setFormat(aid);
                            String type = parser.getAttributeValue(null, "type");
                            XLog.d(TAG, "     type=" + type);
                            if (!XStringUtil.isNullOrEmpty(type))
                                episode.setTrailer(type.equals("预告花絮"));
                            String tag = parser.getAttributeValue(null, "tag");
                            XLog.d(TAG, "     tag=" + tag);
                            episode.setTag(tag);
                            String url_key = parser.getAttributeValue(null, "url_key");
                            XLog.d(TAG, "     url_key=" + url_key);
                            episode.setAndroidUrlKey(url_key);
                            String vid = parser.getAttributeValue(null, "vid");
                            XLog.d(TAG, "     vid=" + vid);
                            episode.setAndroidId(vid);
                            String vseg = parser.getAttributeValue(null, "vseg");
                            XLog.d(TAG, "     vseg=" + vseg);
                            episode.setVseg(vseg);
                            String webURL = parser.getAttributeValue(null, "webURL");
                            XLog.d(TAG, "     webURL=" + webURL);
                            if (!XStringUtil.isNullOrEmpty(webURL))
                                episode.setWebUrl(webURL);
                            String webUrl = parser.getAttributeValue(null, "webUrl");
                            XLog.d(TAG, "     webUrl=" + webUrl);
                            if (!XStringUtil.isNullOrEmpty(webUrl))
                                episode.setWebUrl(webUrl);
                            String pfv2mp4 = parser.getAttributeValue(null, "pfv2mp4");
                            XLog.d(TAG, "     pfv2mp4=" + pfv2mp4);
                            if (!XStringUtil.isNullOrEmpty(pfv2mp4))
                                episode.setPfv2mp4(pfv2mp4.equals("1"));

                            eventType = parser.next();
                            String episodeName = parser.getText();
                            XLog.d(TAG, "<Channel>" + episodeName + "</Channel>");
                            if (!XStringUtil.isNullOrEmpty(episodeName))
                                episode.setName(episodeName);
                        }
                        // 第三方相关信息
                        else if (parser.getName().equals("ThirdPart")) {
                            isThirdPart = true;
                        }
                        else if (parser.getName().equals("part")) {
                            String partType = parser.getAttributeValue(null, "partType");
                            XLog.d(TAG, "     partType=" + partType);
                            programDetail.setThirdPartType(partType);
                            String partTitle = parser.getAttributeValue(null, "partTitle");
                            XLog.d(TAG, "     partTitle=" + partTitle);
                            programDetail.setThirdPartTitle(partTitle);
                            String partImage = parser.getAttributeValue(null, "partImage");
                            XLog.d(TAG, "     partImage=" + partImage);
                            programDetail.setThirdPartImage(partImage);
                            String fn = parser.getAttributeValue(null, "fn");
                            XLog.d(TAG, "<fn>" + fn + "</fn>");
                            if (!XStringUtil.isNullOrEmpty(fn))
                                programDetail.setThirdPartIsEntertainment(fn.equals("1"));
                        }
                        // 平台列表
                        else if (parser.getName().equals("playType")) {
                            platformMap = new HashMap<String, String>();
                        }
                        // 单个平台
                        else if (parser.getName().equals("platform")) {
                            String platformName = parser.getAttributeValue(null, "type");
                            eventType = parser.next();
                            String platformContent = parser.getText();
                            XLog.d(TAG, "<platform type=" + platformName + ">" + platformContent + "</platform>");
                            platformMap.put(platformName, platformContent);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("Sub")) {
                        } else if (parser.getName().equals("Channels")) {
                            if (!isThirdPart) {
                                if (normalEpisodeList != null && normalEpisodeList.size() != 0)
                                    programDetail.setPpsNormalEpisode(normalEpisodeList);
                                if (hqEpisodeList != null && hqEpisodeList.size() != 0)
                                    programDetail.setPpsHqEpisode(hqEpisodeList);
                                if (trailerEpisodeList != null && trailerEpisodeList.size() != 0)
                                    programDetail.setPpsTrailerEpisode(trailerEpisodeList);
                            } else {
                                if (normalEpisodeList != null && normalEpisodeList.size() != 0)
                                    programDetail.setThirdPartNormalEpisode(normalEpisodeList);
                                if (hqEpisodeList != null && hqEpisodeList.size() != 0)
                                    programDetail.setThirdPartHqEpisode(hqEpisodeList);
                                if (trailerEpisodeList != null && trailerEpisodeList.size() != 0)
                                    programDetail.setThirdPartTrailerEpisode(trailerEpisodeList);
                            }
                            normalEpisodeList = null;
                            hqEpisodeList = null;
                            trailerEpisodeList = null;
                        } else if (parser.getName().equals("Channel")) {
                            if (episode.isTrailer())
                                trailerEpisodeList.add(episode);
                            else if (episode.getTag().equals("国语高清"))
                                hqEpisodeList.add(episode);
                            else
                                normalEpisodeList.add(episode);
                            episode = null;
                        } else if (parser.getName().equals("ThirdPart")) {
                            isThirdPart = false;
                        }  else if (parser.getName().equals("playType")) {
                            programDetail.setPlatform(platformMap);
                            platformMap = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            return programDetail;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
