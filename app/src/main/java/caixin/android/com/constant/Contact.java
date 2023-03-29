package caixin.android.com.constant;

import android.os.Environment;

import java.io.File;

public class Contact {


    public static final int HISTORY_MESSAGE_SIZE = 15;

    public static final String DCMI_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    private static final String DIR_NAME = "chatroom";
    public static final String CAMERA_IMAGE_PATH = DCMI_PATH + "/" + DIR_NAME + "/camera/";

    public static final String LOCAL_IMAGE_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .getAbsoluteFile().getAbsolutePath() + File.separator + "suishouzhuan" + File.separator;
}
