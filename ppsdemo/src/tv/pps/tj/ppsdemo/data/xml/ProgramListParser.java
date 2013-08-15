package tv.pps.tj.ppsdemo.data.xml;

import android.util.Xml;
import com.xengine.android.data.cache.DefaultDataRepo;
import com.xengine.android.utils.XStringUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import tv.pps.tj.ppsdemo.data.cache.GlobalStateSource;
import tv.pps.tj.ppsdemo.data.cache.SourceName;
import tv.pps.tj.ppsdemo.data.model.ProgramBase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析节目列表xml文件的解析器
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-29
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public class ProgramListParser {

    public static List<ProgramBase> parse(InputStream is) {
        try {
            GlobalStateSource globalStateSource = (GlobalStateSource) DefaultDataRepo.
                    getInstance().getSource(SourceName.GLOBAL_STATE);

            List<ProgramBase> resultList = null;
            ProgramBase programBase = null;

            XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例
            parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        resultList = new ArrayList<ProgramBase>();
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("subs")) {
                            String TM = parser.getAttributeValue(null, "TM");
                            globalStateSource.setProgramListXmlUpdateTimeStamp(Long.parseLong(TM));
                        } else if (parser.getName().equals("sub") || parser.getName().equals("Thirdsub")) {
                            programBase = new ProgramBase();
                            if (parser.getName().equals("sub"))
                                programBase.setThird(false);
                            else
                                programBase.setThird(true);
                            String id = parser.getAttributeValue(null, "id");
                            programBase.setId(id);
                            String name = parser.getAttributeValue(null, "name");
                            programBase.setName(name);
                            String bkid = parser.getAttributeValue(null, "bkid");
                            programBase.setBkId(bkid);
                            String vm = parser.getAttributeValue(null, "vm");
                            if (!XStringUtil.isNullOrEmpty(vm))
                                programBase.setVote(Float.parseFloat(vm));
                            String img = parser.getAttributeValue(null, "img");
                            programBase.setPosterUrl(img);
                            String lt = parser.getAttributeValue(null, "lt");
                            programBase.setFirstLetter(lt);
                            String nt = parser.getAttributeValue(null, "nt");
                            if (!XStringUtil.isNullOrEmpty(nt))
                                programBase.setNt(Integer.parseInt(nt));
                            String multi = parser.getAttributeValue(null, "multi");
                            programBase.setFilm("1".equals(multi));
                            String sc = parser.getAttributeValue(null, "sc");
                            if (!XStringUtil.isNullOrEmpty(sc))
                                programBase.setVideoNumber(Integer.parseInt(sc));
                            String on = parser.getAttributeValue(null, "on");
                            if (!XStringUtil.isNullOrEmpty(on))
                                programBase.setOnlineNumber(Integer.parseInt(on));
                            String tm = parser.getAttributeValue(null, "tm");
                            if (!XStringUtil.isNullOrEmpty(tm))
                                programBase.setTime(Long.parseLong(tm));
                            String vip = parser.getAttributeValue(null, "vip");
                            if (!XStringUtil.isNullOrEmpty(vip))
                                programBase.setVipLevel(Integer.parseInt(vip));
                            String vopt = parser.getAttributeValue(null, "vopt");
                            if (!XStringUtil.isNullOrEmpty(vopt))
                                programBase.setVipVisibilityLevel(Integer.parseInt(vopt));
                            String popt = parser.getAttributeValue(null, "popt");
                            if (!XStringUtil.isNullOrEmpty(popt))
                                programBase.setUserRequiredLevel(Integer.parseInt(popt));
                            String vlevel = parser.getAttributeValue(null, "vlevel");
                            if (!XStringUtil.isNullOrEmpty(vlevel))
                                programBase.setOnlineLevel(Integer.parseInt(vlevel));
                            String tp = parser.getAttributeValue(null, "tp");
                            programBase.setType(tp);
                            String fotm = parser.getAttributeValue(null, "fotm");
                            programBase.setFotm(fotm);
                            String p = parser.getAttributeValue(null, "p");
                            programBase.setParam(p);
                        } else if (parser.getName().equals("bl")) {
                            eventType = parser.next();
                            String bl = parser.getText();
                            if (!XStringUtil.isNullOrEmpty(bl))
                                programBase.setBlackList(bl.split(";"));
                        } else if (parser.getName().equals("wl")) {
                            eventType = parser.next();
                            String wl = parser.getText();
                            if (!XStringUtil.isNullOrEmpty(wl))
                                programBase.setWhiteList(wl.split(";"));
                        } else if (parser.getName().equals("plat_bl")) {
                            eventType = parser.next();
                            String plat_bl = parser.getText();
                            if (!XStringUtil.isNullOrEmpty(plat_bl))
                                programBase.setPlatformBlackList(plat_bl.split(";"));
                        } else if (parser.getName().equals("plat_wl")) {
                            eventType = parser.next();
                            String plat_wl = parser.getText();
                            if (!XStringUtil.isNullOrEmpty(plat_wl))
                                programBase.setPlatformWhiteList(plat_wl.split(";"));
                        } else if (parser.getName().equals("search")) {
                            String pt = parser.getAttributeValue(null, "pt");
                            programBase.setSearchProgramYear(pt);
                            String tp = parser.getAttributeValue(null, "tp");
                            programBase.setSearchProgramType(tp);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("sub") || parser.getName().equals("Thirdsub")) {
                            resultList.add(programBase);
                            programBase = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            return resultList;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
