package org.bean.jandan.common.util;

import android.os.Environment;
import android.text.TextUtils;

import org.bean.jandan.common.C;

import java.io.File;
import java.io.IOException;

/**
 * Created by liuyulong@yixin.im on 2015/5/5.
 */
public class StorageUtil {

    private static boolean sMounted = false;
    private static boolean sFolderReady = false;
    private static String sExternalStorageDir = null;

    public static void init() {
        loadStorageState();
    }

    private static void loadStorageState() {
        //检查外置存储是否已经mount
        sMounted = Environment.getExternalStorageState()
                              .equals(Environment.MEDIA_MOUNTED);
        if (sMounted) {
            String externalPath = Environment.getExternalStorageDirectory()
                                             .getPath();
            if (sFolderReady && externalPath.equals(sExternalStorageDir)) {
                return;
            }
            sExternalStorageDir = externalPath;
            sFolderReady = createSubFolders();
        } else {
            sExternalStorageDir = null;
            sFolderReady = false;
        }
        //获取外置存储路径

        //创建所需的文件夹
    }

    public static boolean isExternalStorageExist() {
        return sMounted;
    }

    private static boolean createSubFolders() {
        if (!isExternalStorageExist()) {
            return false;
        }

        boolean result = true;
        String appDirectory = sExternalStorageDir + "/" + C.Directory.APP_DIRECTORY_NAME;
        File root = new File(appDirectory);
        if (root.exists() && !root.isDirectory()) {
            root.delete();
        }
        for (C.Directory directory : C.Directory.values()) {
            result &= makeDirectory(appDirectory + directory.getStoragePath());
        }
        if (result) {
            createNoMediaFile(appDirectory);
        }
        sFolderReady = result;
        return sFolderReady;
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    private static boolean makeDirectory(String path) {
        File file = new File(path);
        boolean exist = file.exists();
        if (!exist) {
            exist = file.mkdirs();
        }
        return exist;
    }

    protected static String NO_MEDIA_FILE_NAME = ".nomedia";

    private static void createNoMediaFile(String path) {
        File noMediaFile = new File(path + "/" + NO_MEDIA_FILE_NAME);
        try {
            if (!noMediaFile.exists()) {
                noMediaFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkSubFolders() {
        if (!sFolderReady) {
            return createSubFolders();
        }
        return true;
    }

    public static String getWritePath(String fileName, C.Directory directory) {
        if (TextUtils.isEmpty(fileName) || !checkSubFolders()) {
            return "";
        }
        return pathForName(fileName, directory, false, false);
    }

    private static String pathForName(String fileName, C.Directory type, boolean dir, boolean check) {
        String directory = getDirectoryByDirType(type);
        StringBuilder path = new StringBuilder(directory);
        if (type.isStorageByMD5()) {
            path.append(getMD5Prefix(fileName));
        }

        if (!dir) {
            path.append("/")
                .append(fileName);
        }

        String pathString = path.toString();
        File file = new File(pathString);

        if (check) {
            if (file.exists()) {
                if ((dir && file.isDirectory())
                        || (!dir && !file.isDirectory())) {
                    return pathString;
                }
            }

            return "";
        } else {
            return pathString;
        }
    }

    /**
     * 返回指定类型的文件夹路径
     *
     * @param directory
     * @return
     */
    public static String getDirectoryByDirType(C.Directory directory) {
        if (isExternalStorageExist()) {
            String dirName = String.format("/%s%s", C.Directory.APP_DIRECTORY_NAME, directory.getStoragePath());
            return sExternalStorageDir + dirName;
        }

        return null;
    }

    private static String getMD5Prefix(String fileName) {
        String md5 = StringUtil.makeMd5(fileName);
        if (md5.length() < 4) {
            return md5;
        } else {
            return md5.substring(0, 2) + "/" + md5.substring(2, 4);
        }
    }
}
