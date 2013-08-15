package tv.pps.tj.ppsdemo.engine;

import com.xengine.android.data.cache.DefaultDataRepo;
import com.xengine.android.media.image.loader.XImageViewRemoteLoader;
import tv.pps.tj.ppsdemo.data.cache.ImageSource;
import tv.pps.tj.ppsdemo.data.cache.SourceName;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-8-2
 * Time: 下午2:56
 * To change this template use File | Settings | File Templates.
 */
public class MyImageViewRemoteLoader extends XImageViewRemoteLoader {

    private static MyImageViewRemoteLoader instance;

    public synchronized static MyImageViewRemoteLoader getInstance() {
        if (instance == null) {
            instance = new MyImageViewRemoteLoader();
        }
        return instance;
    }

    private MyImageViewRemoteLoader() {
        super(DownloadMgrHolder.getImageDownloadMgr());
    }

    @Override
    public String getLocalImage(String imgUrl) {
        ImageSource imageSource = (ImageSource) DefaultDataRepo.
                getInstance().getSource(SourceName.IMAGE);
        return imageSource.getLocalImage(imgUrl);
    }

    @Override
    public void setLocalImage(String imgUrl, String localImageFile) {
        ImageSource imageSource = (ImageSource) DefaultDataRepo.
                getInstance().getSource(SourceName.IMAGE);
        imageSource.putImage(imgUrl, localImageFile);
    }
}
