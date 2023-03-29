package caixin.android.com.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * 作者：jogger
 * 时间：2018/11/21 14:49
 * 描述：
 */
@SuppressWarnings("all")
public class FileUtil {
    private static final String FILE_NAME = "91gj";
    private static final String IMG_CACHE_FILE_PATH = FILE_NAME + File.separator + "img";
    private static final String TAG = FileUtil.class.getSimpleName();
    private static FileUtil sUtils;

    private FileUtil() {
    }

    public static FileUtil getInstance() {
        if (sUtils == null) {
            synchronized (FileUtil.class) {
                if (sUtils == null) {
                    sUtils = new FileUtil();
                }
            }
        }
        return sUtils;
    }

    public static File getImgCacheFile() {
        String sdCardPath = getSDCardPath();
        if (TextUtils.isEmpty(sdCardPath)) {
            return null;
        }
        File fileDir = new File(sdCardPath + File.separator + IMG_CACHE_FILE_PATH);
        if (!fileDir.exists())
            fileDir.mkdirs();
        return fileDir;
    }

    /**
     * 获取sd卡路径
     */
    public static String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED) || Environment.getExternalStorageState().equals
                (Environment.MEDIA_SHARED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        if (sdcardDir != null) {
            return sdcardDir.toString() + File.separator;
        } else {
            return null;
        }
    }

    public static void saveBmp2Gallery(Context context, Bitmap bmp, String picName) {
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//通知相册更新
        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public static File compressImg(String filePath) {
        File file = new File(filePath);
        Log.e(TAG, "-------->>" + filePath + ":" + file.exists());
        if (!file.exists()) return file;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if (bitmap == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        long date = System.currentTimeMillis();
        file = new File(FileUtil.getImgCacheFile(), String.valueOf(date) + ".jpg");
        while (baos.toByteArray().length / 1024 > 1024) {//大小控制在1m以内
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int ret;
            while ((ret = bais.read(buf, 0, buf.length)) != -1) {
                fos.write(buf, 0, ret);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bais.close();
            baos.close();
            if (fos != null)
                fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 移动文件
     *
     * @param srcFileName 源文件完整路径
     * @param destDirName 目的目录完整路径
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFile(String srcFileName, String destDirName) {
        boolean result = false;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File srcFile = new File(srcFileName);
            if (!srcFile.exists() || !srcFile.isFile()) {
                result = false;
            } else {
                File destFile = new File(destDirName);
//                if (!destDir.exists())
//                    destDir.mkdirs();

                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                destFile.createNewFile();
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destDirName);
                byte[] buf = new byte[1024];
                int ret;
                while ((ret = fis.read(buf, 0, buf.length)) != -1) {
                    fos.write(buf, 0, ret);
                }
                result = true;
                srcFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 移动目录
     *
     * @param srcDirName  源目录完整路径
     * @param destDirName 目的目录完整路径
     * @return 目录移动成功返回true，否则返回false
     */
    public static boolean moveDirectory(String srcDirName, String destDirName) {

        File srcDir = new File(srcDirName);
        if (!srcDir.exists() || !srcDir.isDirectory())
            return false;

        File destDir = new File(destDirName);
        if (!destDir.exists())
            destDir.mkdirs();

        /**
         * 如果是文件则移动，否则递归移动文件夹。删除最终的空源文件夹
         * 注意移动文件夹时保持文件夹的树状结构
         */
        File[] sourceFiles = srcDir.listFiles();
        for (File sourceFile : sourceFiles) {
            if (sourceFile.isFile())
                moveFile(sourceFile.getAbsolutePath(), destDir.getAbsolutePath());
            else if (sourceFile.isDirectory())
                moveDirectory(sourceFile.getAbsolutePath(),
                        destDir.getAbsolutePath() + File.separator + sourceFile.getName());
            else
                ;
        }
        return srcDir.delete();
    }

    /**
     * 复制文件
     *
     * @param srcFileName  源文件完整路径
     * @param destFileName 目的完整路径
     * @return 文件复制成功返回true，否则返回false
     */
    public boolean copyFile(String srcFileName, String destFileName) {
        boolean result = false;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File srcFile = new File(srcFileName);
            if (!srcFile.exists() || !srcFile.isFile()) {
                result = false;
            } else {
                File destFile = new File(destFileName);
                if (!destFile.exists())
                    destFile.createNewFile();
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                byte[] buf = new byte[1024];
                int ret;
                while ((ret = fis.read(buf, 0, buf.length)) != -1) {
                    fos.write(buf, 0, ret);
                }
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 读取文件。
     *
     * @return message
     */
    public static String readFile(String filePath) {
        String result = "";
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            in = new FileInputStream(filePath);
            baos = new ByteArrayOutputStream();
            bis = new BufferedInputStream(in);
            bos = new BufferedOutputStream(baos);
            int ret;
            byte[] buf = new byte[1024];
            while ((ret = bis.read(buf, 0, buf.length)) != -1) {
                bos.write(buf, 0, ret);
            }
            result = bos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (bis != null)
                    bis.close();
                if (baos != null)
                    baos.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 写入文件。
     *
     * @return message
     */
    public static boolean writeFile(File file, String json) {
        boolean result = false;
        OutputStream out = null;
        ByteArrayInputStream bais = null;
        BufferedOutputStream bos = null;
        try {
            out = new FileOutputStream(file);
            bos = new BufferedOutputStream(out);
            bais = new ByteArrayInputStream(json.getBytes());
            int ret;
            byte[] buf = new byte[1024];
            while ((ret = bais.read(buf, 0, buf.length)) != -1) {
                bos.write(buf, 0, ret);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
                if (bais != null)
                    bais.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 递归获取文件大小
     *
     * @param file 文件夹
     * @return 文件大小
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            return 0;
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size 文件大小
     * @return 如10MB、10KB、10GB
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            if (size == 0) {
                return "0MB";
            }
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    public static void deleteAllFilesIfBig(File root) {
        if (getFolderSize(root) < 50 * 1024 * 1024)
            return;
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFilesIfBig(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFilesIfBig(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    public static String saveFile(Bitmap bm, boolean delete) {
        try {
            String path = getSDPath() + "/caixin/temp/";
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            if (delete) {
                deleteAllFilesIfBig(dirFile);
            }
            File myCaptureFile = new File(path + System.currentTimeMillis() + ".jpg");
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos;
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            Log.e(TAG, "saveFile: " + myCaptureFile.getAbsolutePath());
            return myCaptureFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

    public static boolean checkDirExist(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        return false;
    }
}
