package com.imdada.aopm.aspect;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Song Hongling
 * @date 2019/10/31
 */
@Slf4j
public class HttpClientAop {

    public static void enhance() {
        try {
            ClassPool classPool = ClassPool.getDefault();
            classPool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
            CtClass ctClass = classPool.get("org.apache.http.impl.client.CloseableHttpClient");
            // 需要修改的方法名称
            String aopMethodName = "execute";
            CtMethod mold = ctClass.getDeclaredMethod(aopMethodName,
                new CtClass[] {ClassPool.getDefault().get("org.apache.http.client.methods.HttpUriRequest"),
                    ClassPool.getDefault().get("org.apache.http.client.ResponseHandler")});
            // 修改原有的方法名称
            String replaceName = aopMethodName + "$impl";
            mold.setName(replaceName);

            //使用原始方法名loop，定义一个新方法，在这个方法内部调用loop$impl
            CtMethod newMethod = CtNewMethod.make("public " + mold.getReturnType().getName() + " " + aopMethodName
                    + "(org.apache.http.client.methods.HttpUriRequest rq"
                    + ",org.apache.http.client.ResponseHandler rh){\n"
                    + "\tObject result = null;\n"
                    + "\tThrowable thl = null;\n"
                    + "\ttry {\n"
                    + "\t    result = " + replaceName + "(rq,rh);\n"
                    + "\t    return result;\n"
                    + "\t} catch (Throwable e) {\n"
                    + "\t    thl = e;\n"
                    + "\t    throw e;\n"
                    + "\t} finally {\n"
                    + "\t    com.imdada.aopm.rpc.HttpClientAspectJ.statisticResponse(rq, result, thl);\n"
                    + "\t}\n"
                    + "}"
                , ctClass);
            ctClass.addMethod(newMethod);
            ctClass.toClass();
            log.info("CloseableHttpClient enhance success");
        } catch (Exception e) {
            log.error("CloseableHttpClient enhance exception", e);
        }
    }
}
