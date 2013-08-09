package tv.pps.tj.ppsdemo.engine;

import com.xengine.android.data.cache.DefaultDataRepo;
import com.xengine.android.media.image.loader.XScrollLocalLoader;
import tv.pps.tj.ppsdemo.data.cache.ImageSource;
import tv.pps.tj.ppsdemo.data.cache.SourceName;

/**
 * Created with IntelliJ IDEA.
 * User: tujun
 * Date: 13-8-7
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 */
public class MyImageScrollLocalLoader extends XScrollLocalLoader {

    private static MyImageScrollLocalLoader instance;

    public synchronized static MyImageScrollLocalLoader getInstance() {
        if (instance == null) {
            instance = new MyImageScrollLocalLoader();
        }
        return instance;
    }

    private MyImageScrollLocalLoader() {
        super();
    }

    @Override
    public String getLocalImage(String imgUrl) {
        ImageSource imageSource = (ImageSource) DefaultDataRepo.
                getInstance().getSource(SourceName.IMAGE);
        return imageSource.getLocalImage(imgUrl);
    }
}
