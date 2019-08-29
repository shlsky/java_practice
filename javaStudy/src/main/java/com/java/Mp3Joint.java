package com.java;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * MP3 音频拼接服务
 *
 * @author Song Hongling
 * @date 2019/8/12
 */
public class Mp3Joint {

    /**
     * 音频拼接，生成新的音频
     *
     * @param sourceOnePath 音频1
     * @param sourceTwoPath 音频2
     * @param targetPath    拼接后音频位置
     * @throws Exception
     */
    public static void join(String sourceOnePath, String sourceTwoPath, String targetPath) throws Exception {

        try (FileInputStream input1 = new FileInputStream(sourceOnePath); FileInputStream input2 = new FileInputStream(
            sourceTwoPath);
             FileOutputStream out = new FileOutputStream(targetPath)) {
            byte b[] = new byte[1024];
            //把f1的内容流到f3中
            append(input1, out, b);
            //在刚才的后面流f2到f3中
            append(input2, out, b);

            out.write(b);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 追加输出流
     *
     * @param inpu1 输入流
     * @param out   输出流
     * @param b     字节数组
     * @throws Exception
     */
    private static void append(FileInputStream inpu1, FileOutputStream out, byte b[]) throws Exception {
        int len = 0;
        //把f1的内容流到f3中
        while ((len = inpu1.read(b)) != -1) {
            for (int i = 0; i < len; i++) {
                out.write(b[i]);
            }
        }
    }

    public static void main(String args[]) throws Exception {
        join("resource/hts0024d97e@dx250310ac4b4f6f2b00.mp3", "resource/hts0024dcff@dx250310ac4c836f2b00.mp3",
            "resource/d1.mp3");
    }

}
