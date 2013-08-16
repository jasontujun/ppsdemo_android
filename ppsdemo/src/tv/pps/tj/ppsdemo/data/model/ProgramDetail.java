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

    /** 集数相关属性 **/
    private Map<String, List<Episode>> ppsEpisodes;

    /** 第三方相关 **/
    private List<ThirdPart> thirdPartList;

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

    public Map<String, List<Episode>> getPpsEpisodes() {
        return ppsEpisodes;
    }

    public void setPpsEpisodes(Map<String, List<Episode>> ppsEpisodes) {
        this.ppsEpisodes = ppsEpisodes;
    }

    public List<ThirdPart> getThirdPartList() {
        return thirdPartList;
    }

    public void setThirdPartList(List<ThirdPart> thirdPartList) {
        this.thirdPartList = thirdPartList;
    }
}
