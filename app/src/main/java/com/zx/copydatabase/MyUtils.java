package com.zx.copydatabase;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 周旭 on 2017/1/20.
 */

public class MyUtils {

    /**
     * 将assets下的资源复制到应用程序的databases目录下
     * @param context 上下文
     * @param fileName assets下的资源的文件名
     */
    public static void copyAssetsToDB(Context context, String fileName) throws IOException {
        //数据库的存储路径,该路径在：data/data/包名/databases目录下，
        String destPath = context.getDatabasePath("").getPath();
        Log.i("tag","path---->"+destPath);
        File file = new File(destPath);
        if (!file.exists()) {
            file.mkdirs();  //创建目录
        }else {
            return;
        }

        //打开assest文件，获得输入流
        InputStream is = context.getAssets().open(fileName);
        BufferedInputStream bis = new BufferedInputStream(is);

        //获得写入文件的输出流
        FileOutputStream fos = new FileOutputStream(destPath +File.separator + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] data = new byte[2 * 1024];
        int len;
        while ((len = bis.read(data)) != -1){
            bos.write(data, 0, len);
        }

        bos.flush();
        bis.close();
        bos.close();
    }
}

