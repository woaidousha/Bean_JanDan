package org.bean.jandan.common.util;

/**
 * Created by liuyulong@yixin.im on 2015/5/5.
 */

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * ���ڰѸ������浽�ļ�ϵͳ��
 */
public class AttachmentStore {
    public static long copy(String srcPath, String dstPath) {
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath)) {
            return -1;
        }

        File source = new File(srcPath);
        if (!source.exists()) {
            return -1;
        }

        if (srcPath.equals(dstPath)) {
            return source.length();
        }

        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            fcin = new FileInputStream(source).getChannel();
            fcout = new FileOutputStream(create(dstPath)).getChannel();
            ByteBuffer tmpBuffer = ByteBuffer.allocateDirect(4096);
            while (fcin.read(tmpBuffer) != -1) {
                tmpBuffer.flip();
                fcout.write(tmpBuffer);
                tmpBuffer.clear();
            }
            return source.length();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fcin != null) {
                    fcin.close();
                }
                if (fcout != null) {
                    fcout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static long getFileLength(String srcPath) {
        if (TextUtils.isEmpty(srcPath)) {
            return -1;
        }

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) {
            return -1;
        }

        return srcFile.length();
    }

    public static long save(String path, String content) {
        return save(content.getBytes(), path);
    }

    /**
     * �����ݱ��浽�ļ�ϵͳ�У����ҷ������С
     *
     * @param data
     * @param filePath
     * @return �������ʧ��, �򷵻�-1
     */
    public static long save(byte[] data, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return -1;
        }

        File f = new File(filePath);
        if (f.getParentFile() == null) {
            return -1;
        }

        if (!f.getParentFile()
              .exists()) {// ����������ϼ��ļ���
            f.getParentFile()
             .mkdirs();
        }
        try {
            f.createNewFile();
            FileOutputStream fout = new FileOutputStream(f);
            fout.write(data);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return f.length();
    }

    public static boolean move(String srcFilePath, String dstFilePath) {
        if (TextUtils.isEmpty(srcFilePath) || TextUtils.isEmpty(dstFilePath)) {
            return false;
        }

        File srcFile = new File(srcFilePath);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }

        File dstFile = new File(dstFilePath);
        if (dstFile.getParentFile() == null) {
            return false;
        }

        if (!dstFile.getParentFile()
                    .exists()) {// ����������ϼ��ļ���
            dstFile.getParentFile()
                   .mkdirs();
        }

        return srcFile.renameTo(dstFile);
    }

    public static File create(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        File f = new File(filePath);
        if (!f.getParentFile()
              .exists()) {// ����������ϼ��ļ���
            f.getParentFile()
             .mkdirs();
        }
        try {
            f.createNewFile();
            return f;
        } catch (IOException e) {
            if (f != null && f.exists()) {
                f.delete();
            }
            return null;
        }
    }

    /**
     * @param is
     * @param filePath
     * @return ����ʧ�ܣ�����-1
     */
    public static long save(InputStream is, String filePath) {
        File f = new File(filePath);
        if (!f.getParentFile()
              .exists()) {// ����������ϼ��ļ���
            f.getParentFile()
             .mkdirs();
        }
        FileOutputStream fos = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            int read = 0;
            byte[] bytes = ByteBufferPool.obtain(8192);
            while ((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            ByteBufferPool.recycle(bytes);
            return f.length();
        } catch (IOException e) {
            if (f != null && f.exists()) {
                f.delete();
            }
            DebugLog.e("file save is to " + filePath + " failed: " + e.getMessage());
            return -1;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ���ļ����ļ�ϵͳ�ж�ȡ����
     *
     * @param path
     * @return ����޷���ȡ, �򷵻�null
     */
    public static byte[] load(String path) {
        try {
            File f = new File(path);
            int unread = (int) f.length();
            int read = 0;
            byte[] buf = new byte[unread]; // ��ȡ�ļ�����
            FileInputStream fin = new FileInputStream(f);
            do {
                int count = fin.read(buf, read, unread);
                read += count;
                unread -= count;
            } while (unread != 0);
            fin.close();
            return buf;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static String loadAsString(String path) {
        if (isFileExist(path)) {
            byte[] content = load(path);
            return new String(content);
        } else {
            return null;
        }
    }

    /**
     * ɾ��ָ��·���ļ�
     *
     * @param path
     */
    public static boolean delete(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File f = new File(path);
        if (f.exists()) {
            f = renameOnDelete(f);
            return f.delete();
        } else {
            return false;
        }
    }

    public static void deleteOnExit(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File f = new File(path);
        if (f.exists()) {
            f.deleteOnExit();
        }
    }

    public static boolean deleteDir(String path) {
        return deleteDir(path, true);
    }

    private static boolean deleteDir(String path, boolean rename) {
        boolean success = true;
        File file = new File(path);
        if (file.exists()) {
            if (rename) {
                file = renameOnDelete(file);
            }

            File[] list = file.listFiles();
            if (list != null) {
                int len = list.length;
                for (int i = 0; i < len; ++i) {
                    if (list[i].isDirectory()) {
                        deleteDir(list[i].getPath(), false);
                    } else {
                        boolean ret = list[i].delete();
                        if (!ret) {
                            success = false;
                        }
                    }
                }
            }
        } else {
            success = false;
        }
        if (success) {
            file.delete();
        }
        return success;
    }

    // rename before delete to avoid lingering filesystem lock of android
    private static File renameOnDelete(File file) {
        String tmpPath = file.getParent() + "/" + System.currentTimeMillis() + "_tmp";
        File tmpFile = new File(tmpPath);
        if (file.renameTo(tmpFile)) {
            return tmpFile;
        } else {
            return file;
        }
    }

    public static boolean isFileExist(String path) {
        if (!TextUtils.isEmpty(path) && new File(path).exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean saveBitmap(Bitmap bitmap, String path, boolean recyle) {
        if (bitmap == null || TextUtils.isEmpty(path)) {
            return false;
        }

        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            return true;

        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            if (recyle) {
                bitmap.recycle();
            }
        }
    }
}
