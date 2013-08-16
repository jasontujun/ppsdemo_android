package tv.pps.tj.ppsdemo.data.model;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-8-14
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class ThirdPart {
    /** 第三方相关属性 **/
    private String thirdPartType; // 第三方视频的id值 -partType
    private String thirdPartTitle; // 第三方视频的名称 -partTitle
    private String thirdPartImage; // 第三方视频的图标 -partImage
    private boolean thirdPartIsEntertainment;// 综艺和新闻才有值 - fn
    private Map<String, String> platform;// 当前支持的平台和播放方式：如启动系统播放器 -type

    /** 集数相关属性 **/
    private Map<String, List<Episode>> thirdPartEpisodes;

    public String getThirdPartType() {
        return thirdPartType;
    }

    public void setThirdPartType(String thirdPartType) {
        this.thirdPartType = thirdPartType;
    }

    public String getThirdPartTitle() {
        return thirdPartTitle;
    }

    public void setThirdPartTitle(String thirdPartTitle) {
        this.thirdPartTitle = thirdPartTitle;
    }

    public String getThirdPartImage() {
        return thirdPartImage;
    }

    public void setThirdPartImage(String thirdPartImage) {
        this.thirdPartImage = thirdPartImage;
    }

    public boolean isThirdPartIsEntertainment() {
        return thirdPartIsEntertainment;
    }

    public void setThirdPartIsEntertainment(boolean thirdPartIsEntertainment) {
        this.thirdPartIsEntertainment = thirdPartIsEntertainment;
    }

    public Map<String, String> getPlatform() {
        return platform;
    }

    public void setPlatform(Map<String, String> platform) {
        this.platform = platform;
    }

    public Map<String, List<Episode>> getThirdPartEpisodes() {
        return thirdPartEpisodes;
    }

    public void setThirdPartEpisodes(Map<String, List<Episode>> thirdPartEpisodes) {
        this.thirdPartEpisodes = thirdPartEpisodes;
    }
}
