package in.fortrainer.admin.utilities;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by hbb20 on 5/1/17.
 */

public class EECMultiDexApplication extends MultiDexApplication {
    public static Context context;
    private static EECMultiDexApplication eecMultiDexApplication;

    public EECMultiDexApplication() {
        eecMultiDexApplication = this;
    }

    public static EECMultiDexApplication getEecMultiDexApplication() {
        return eecMultiDexApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
