package tv.pps.tj.ppsdemo.data.model;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-29
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class ProgramDetail {

    private String id;// 影片id -id
    private String bkId;// 影片百科id -bkid
    private String name;// 影片名称 -name
    private String posterUrl;// 海报地址 -img
    private String type;// 影片类型 -type
    private String region;// 影片地区 -region
    private String director;// 导演 -dirt
    private String[] actor;// 演员 -actor
    private float vote;// 评分 -vote
    private int voteCount;// 评分人数 -vote_count
    private String introduction;// 剧情简介 -inton
    private boolean isFilm;// 用于标识影片是电影或电视剧的值 -multi
    private int programLength;// 影片时长(min) - ct
    private boolean followable;// 标识可以追剧 -followable
    private boolean isEntertainment;// 综艺和新闻才有值 - fn

    /** 等级相关属性 **/
    private int vipLevel;// 标识影片会员等级 -vip
    private int vipVisibilityLevel;// 影片会员等级可见值 -vopt
    private int onlineLevel;// 在线等级 -vlevel
    private int userRequiredLevel;// 可以播放的用户等级 -popt

    /** 黑白名单相关属性 **/
    private String[] blackList;// 黑名单 -block
    private String[] whiteList;// 白名单 -wlock

    /** 第三方相关属性 **/
    private String thirdPartType; // 第三方视频的id值 -partType
    private String thirdPartTitle; // 第三方视频的名称 -partTitle
    private String thirdPartImage; // 第三方视频的图标 -partImage
    private boolean thirdPartIsEntertainment;// 综艺和新闻才有值 - fn
    private Map<String, String> platform;// 当前支持的平台和播放方式：如启动系统播放器 -type

    /** 集数相关属性 **/
    private List<Episode> ppsNormalEpisode;
    private List<Episode> ppsHqEpisode;
    private List<Episode> ppsTrailerEpisode;
    private List<Episode> thirdPartNormalEpisode;
    private List<Episode> thirdPartHqEpisode;
    private List<Episode> thirdPartTrailerEpisode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBkId() {
        return bkId;
    }

    public void setBkId(String bkId) {
        this.bkId = bkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getActor() {
        return actor;
    }

    public void setActor(String[] actor) {
        this.actor = actor;
    }

    public float getVote() {
        return vote;
    }

    public void setVote(float vote) {
        this.vote = vote;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean isFilm() {
        return isFilm;
    }

    public void setFilm(boolean film) {
        isFilm = film;
    }

    public int getProgramLength() {
        return programLength;
    }

    public void setProgramLength(int programLength) {
        this.programLength = programLength;
    }

    public boolean isFollowable() {
        return followable;
    }

    public void setFollowable(boolean followable) {
        this.followable = followable;
    }

    public boolean isEntertainment() {
        return isEntertainment;
    }

    public void setEntertainment(boolean entertainment) {
        isEntertainment = entertainment;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getVipVisibilityLevel() {
        return vipVisibilityLevel;
    }

    public void setVipVisibilityLevel(int vipVisibilityLevel) {
        this.vipVisibilityLevel = vipVisibilityLevel;
    }

    public int getOnlineLevel() {
        return onlineLevel;
    }

    public void setOnlineLevel(int onlineLevel) {
        this.onlineLevel = onlineLevel;
    }

    public int getUserRequiredLevel() {
        return userRequiredLevel;
    }

    public void setUserRequiredLevel(int userRequiredLevel) {
        this.userRequiredLevel = userRequiredLevel;
    }

    public String[] getBlackList() {
        return blackList;
    }

    public void setBlackList(String[] blackList) {
        this.blackList = blackList;
    }

    public String[] getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String[] whiteList) {
        this.whiteList = whiteList;
    }

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

    public List<Episode> getPpsNormalEpisode() {
        return ppsNormalEpisode;
    }

    public void setPpsNormalEpisode(List<Episode> ppsNormalEpisode) {
        this.ppsNormalEpisode = ppsNormalEpisode;
    }

    public List<Episode> getPpsHqEpisode() {
        return ppsHqEpisode;
    }

    public void setPpsHqEpisode(List<Episode> ppsHqEpisode) {
        this.ppsHqEpisode = ppsHqEpisode;
    }

    public List<Episode> getPpsTrailerEpisode() {
        return ppsTrailerEpisode;
    }

    public void setPpsTrailerEpisode(List<Episode> ppsTrailerEpisode) {
        this.ppsTrailerEpisode = ppsTrailerEpisode;
    }

    public List<Episode> getThirdPartNormalEpisode() {
        return thirdPartNormalEpisode;
    }

    public void setThirdPartNormalEpisode(List<Episode> thirdPartNormalEpisode) {
        this.thirdPartNormalEpisode = thirdPartNormalEpisode;
    }

    public List<Episode> getThirdPartHqEpisode() {
        return thirdPartHqEpisode;
    }

    public void setThirdPartHqEpisode(List<Episode> thirdPartHqEpisode) {
        this.thirdPartHqEpisode = thirdPartHqEpisode;
    }

    public List<Episode> getThirdPartTrailerEpisode() {
        return thirdPartTrailerEpisode;
    }

    public void setThirdPartTrailerEpisode(List<Episode> thirdPartTrailerEpisode) {
        this.thirdPartTrailerEpisode = thirdPartTrailerEpisode;
    }
}
