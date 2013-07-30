package tv.pps.tj.ppsdemo.data.model;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-17
 * Time: 上午9:29
 * To change this template use File | Settings | File Templates.
 */
public class ProgramBase {

    private boolean isThird;// 是否是第三方视频资源

    private String id;// 用于请求详情 -id
    private String bkId;// 影片百科id -bkid
    private String name;//影片名称 -name
    private String posterUrl;// 海报地址 -img
    private String type;// 影片类型 -tp
    private float vote;// 影片评分 -vm
    private boolean isFilm;// 用于标识影片是电影或电视剧的值 -multi
    private int videoNumber;// 视频数量 -sc
    private int onlineNumber;// 在线人数 -on
    private long time;// 节目上映时间 -tm
    private int nt; // 0表示已到最后一层,1表示还有下一层 -nt
    private String fotm;// 未知 -fotm
    private String param;//  无实际意义,请详细界面数据时需要加上 -p

    /** 等级相关属性 **/
    private int vipLevel;// 标识影片会员等级 -vip
    private int vipVisibilityLevel;// 影片会员等级可见值 -vopt
    private int onlineLevel;// 在线等级 -vlevel
    private int userRequiredLevel;// 可以播放的用户等级 -popt

    /** 筛选相关属性 **/
    private String firstLetter;//  用于筛选，拼音首字母 -lt
    private String searchProgramYear;// 用于筛选，影片年份 -pt
    private String searchProgramType;// 用于筛选，影片类型 -tp
    private String searchProgramRegion;// 用于筛选，影片地区 -rg

    /** 黑白名单相关属性 **/
    private String[] blackList;// 黑名单 -bl
    private String[] whiteList;// 白名单 -wl
    private String[] platformBlackList;// 平台黑名单 -plat_bl
    private String[] platformWhiteList;// 平台白名单 -plat_wl

    public boolean isThird() {
        return isThird;
    }

    public void setThird(boolean third) {
        isThird = third;
    }

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

    public float getVote() {
        return vote;
    }

    public void setVote(float vote) {
        this.vote = vote;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public int getNt() {
        return nt;
    }

    public void setNt(int nt) {
        this.nt = nt;
    }

    public boolean isFilm() {
        return isFilm;
    }

    public void setFilm(boolean film) {
        isFilm = film;
    }

    public int getVideoNumber() {
        return videoNumber;
    }

    public void setVideoNumber(int videoNumber) {
        this.videoNumber = videoNumber;
    }

    public int getOnlineNumber() {
        return onlineNumber;
    }

    public void setOnlineNumber(int onlineNumber) {
        this.onlineNumber = onlineNumber;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFotm() {
        return fotm;
    }

    public void setFotm(String fotm) {
        this.fotm = fotm;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getSearchProgramYear() {
        return searchProgramYear;
    }

    public void setSearchProgramYear(String searchProgramYear) {
        this.searchProgramYear = searchProgramYear;
    }

    public String getSearchProgramType() {
        return searchProgramType;
    }

    public void setSearchProgramType(String searchProgramType) {
        this.searchProgramType = searchProgramType;
    }

    public String getSearchProgramRegion() {
        return searchProgramRegion;
    }

    public void setSearchProgramRegion(String searchProgramRegion) {
        this.searchProgramRegion = searchProgramRegion;
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

    public String[] getPlatformBlackList() {
        return platformBlackList;
    }

    public void setPlatformBlackList(String[] platformBlackList) {
        this.platformBlackList = platformBlackList;
    }

    public String[] getPlatformWhiteList() {
        return platformWhiteList;
    }

    public void setPlatformWhiteList(String[] platformWhiteList) {
        this.platformWhiteList = platformWhiteList;
    }
}
