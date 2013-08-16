package tv.pps.tj.ppsdemo.engine;

import com.xengine.android.data.cache.DefaultDataRepo;
import com.xengine.android.media.image.loader.XScrollRemoteLoader;
import tv.pps.tj.ppsdemo.data.cache.ImageSource;
import tv.pps.tj.ppsdemo.data.cache.SourceName;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-8-7
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 */
public class MyImageScrollRemoteLoader extends XScrollRemoteLoader {

    private static MyImageScrollRemoteLoader instance;

    public synchronized static MyImageScrollRemoteLoader getInstance() {
        if (instance == null) {
            instance = new MyImageScrollRemoteLoader();
        }
        return instance;
    }

    private MyImageScrollRemoteLoader() {
        super(DownloadMgrHolder.getImageDownloadMgr(),
                MyImageScrollLocalLoader.getInstance());
    }

    @Override
    public String getLocalImage(String imgUrl) {
        ImageSource imageSource = (ImageSource) DefaultDataRepo.
                getInstance().getSource(SourceName.IMAGE);
        if (imageSource != null)
            return imageSource.getLocalImage(imgUrl);
        else
            return null;
    }

    @Override
    public void setLocalImage(String imgUrl, String localImageFile) {
        ImageSource imageSource = (ImageSource) DefaultDataRepo.
                getInstance().getSource(SourceName.IMAGE);
        if (imageSource != null)
            imageSource.putImage(imgUrl, localImageFile);
    }
}
