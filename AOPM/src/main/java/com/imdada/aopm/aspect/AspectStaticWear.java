package com.imdada.aopm.aspect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;

import com.sun.tools.attach.VirtualMachine;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Song Hongling
 * @date 2019/10/30
 */
@Slf4j
public class AspectStaticWear {

    /**
     * LTW，aspectJ 运行时加载，JVMTI技术
     */
    public static void loadAgent() {

        try {
            //VirtualMachine vm = VirtualMachine.attach(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
            //String aspectJWeaverJarPath = fetchAspectJWeaverJar();
            //if (aspectJWeaverJarPath == null) {
            //    return;
            //}
            //vm.loadAgent(aspectJWeaverJarPath);
            //vm.detach();
        } catch (Exception e) {
            log.error("loadAgent 异常", e);
        }
    }

    /**
     * aspectjweaver-1.8.11.jar 在resources/lib目录下，因为jar包中的文件不能得到文件的路径，
     * 所以此处获取到本项目jar中aspectjweaver-1.8.11.jar
     * 的输入流，然后写成文件，然后返回文件的绝对路径供Agent使用
     *
     * @return
     */
    private static String fetchAspectJWeaverJar() {
        File file = new File("aspectjweaver_1.8.11_temp.jar");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            log.error("fetch aspectJWeaver exception", e);
            return null;
        }
        try (InputStream input = AspectStaticWear.class.getClass().getResourceAsStream("/lib/aspectjweaver-1.8.11.jar");
             OutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (Throwable e) {
            log.error("fetch aspectJWeaver exception", e);
        }
        log.info("aspectJWeaver path = {}", file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
