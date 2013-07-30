package tv.pps.tj.ppsdemo.data.model;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-7-29
 * Time: 下午4:22
 * To change this template use File | Settings | File Templates.
 */
public class Episode {

    private String name;// 集数或名称

    private String id;// 每一集的ID -id
    private String gid;// 唯一标识ID -gid
    private String aid;// 未知 -aid
    private String url;// pps的播放地址 -url
    private String webUrl;// pps的网站播放地址 -webURL
    private String fotm;// 当前码率,如:普通、高清 -fotm
    private String language;// 当前语言,如:国语 -lang
    private String tag;// 语言+码率 -tag
    private String format;// 当前视频的格式 -fmt
    private int length;// 每一集的时长(min) -ct
    private long fileSize;// 每一集的大小(byte) -fsize
    private boolean canDownload;// 标识是否可下载 -dl
    private boolean isEntertainment;// 综艺和新闻才有值 - fn
    private boolean isTrailer;// 标识是否是花絮预告 -type
    private boolean pfv2mp4;// 标识是否有（Highline编码）的剧集 -pfv2mp4
    private String def;// 未知 -def
    private long time;// 每一集上映时间 -tm
    private int bitrate;// 每一集的比特率 -bitrate
    private String vseg;// 未知 -vseg

    /** android相关属性 **/
    private String androidId;// android(baseline编码)每一集的播放ID -vid
    private String androidThirdPartUrl;// android第三方web每一集播放地址 -url_android
    private String androidUrlKey;// android (baseline编码) 每一集的投递ID
    private String androidFileSize;// android (baseline编码) 每一集的大小 -extfs

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getFotm() {
        return fotm;
    }

    public void setFotm(String fotm) {
        this.fotm = fotm;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isCanDownload() {
        return canDownload;
    }

    public void setCanDownload(boolean canDownload) {
        this.canDownload = canDownload;
    }

    public boolean isEntertainment() {
        return isEntertainment;
    }

    public void setEntertainment(boolean entertainment) {
        isEntertainment = entertainment;
    }

    public boolean isTrailer() {
        return isTrailer;
    }

    public void setTrailer(boolean trailer) {
        isTrailer = trailer;
    }

    public boolean isPfv2mp4() {
        return pfv2mp4;
    }

    public void setPfv2mp4(boolean pfv2mp4) {
        this.pfv2mp4 = pfv2mp4;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public String getVseg() {
        return vseg;
    }

    public void setVseg(String vseg) {
        this.vseg = vseg;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getAndroidThirdPartUrl() {
        return androidThirdPartUrl;
    }

    public void setAndroidThirdPartUrl(String androidThirdPartUrl) {
        this.androidThirdPartUrl = androidThirdPartUrl;
    }

    public String getAndroidUrlKey() {
        return androidUrlKey;
    }

    public void setAndroidUrlKey(String androidUrlKey) {
        this.androidUrlKey = androidUrlKey;
    }

    public String getAndroidFileSize() {
        return androidFileSize;
    }

    public void setAndroidFileSize(String androidFileSize) {
        this.androidFileSize = androidFileSize;
    }
}
