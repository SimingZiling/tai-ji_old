package org.yang.localtools.util;

import javax.annotation.processing.Generated;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类功能
 */
public class ClassUtil {

    private ClassUtil(){}

    /**
     * 迭代
     */
    private static boolean iteration = false;

    /**
     * 扫描包
     * @param packs 包
     * @return 包列表
     */
    public static Set<String> scanPackage(String... packs){
        Set<String> packageDis = new LinkedHashSet<>();
        for (String pack : packs){
            scanPackage(pack,packageDis);
        }
        return packageDis;
    }

    /**
     * 扫描包
     * @param pack 包
     * @param packageSet 包列表
     * @return 包列表
     */
    private static Set<String> scanPackage(String pack,Set<String> packageSet){
        // 通过正则获取带星号的包的前缀贺后缀
        Matcher matcher = Pattern.compile("(.*?)\\.\\*(.*)").matcher(pack);
        if (matcher.matches()) {
            // 当星号后的值为空的时候则进行迭代
            if (matcher.group(2) == null || matcher.group(2).equals("")){
                iteration = true;
            }
            for(String fileName : getPackageFile(matcher.group(1),false)){
                scanPackage(matcher.group(1)+"."+fileName+matcher.group(2),packageSet);
            }
        }else {
            if(!getPackageFile(pack,true).isEmpty()){
                packageSet.add(pack);
            }
        }
        if(iteration){
            for(String fileName : getPackageFile(pack,false)){
                scanPackage(pack+"."+fileName,packageSet);
            }
        }
        return packageSet;
    }

    /**
     * 获取jar包中包路径中文件
     * @param url 地址
     * @param type 获取类型 true表示文件 false表示文件夹
     * @param packageDirName 包路径名
     * @return 有效文件列表
     */
    private static Set<String> getJarPackageFile(URL url,Boolean type,String packageDirName) throws IOException {
        Set<String> packageFile = new LinkedHashSet<>();
        // 从此jar包 得到一个枚举类
        Enumeration<JarEntry> entries = ((JarURLConnection) url.openConnection()).getJarFile().entries();
        // 同样的进行循环迭代
        while (entries.hasMoreElements()){
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            // 如果是以/开头的
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }
            if(type == null){
                // 如果前半部分和定义的包名相同
                if (!entry.isDirectory() && name.startsWith(packageDirName)){
                    Matcher matcher = Pattern.compile(packageDirName + ".*?/(.*?)").matcher(name);
                    if (matcher.matches()) {
                        Matcher fileMatcher = Pattern.compile("/").matcher(matcher.group(1));
                        if(!fileMatcher.find()){
                            packageFile.add(matcher.group(1));
                        }
                    }
                }else {
                    // 如果前半部分和定义的包名相同
                    if (name.startsWith(packageDirName)) {
                        Matcher matcher = Pattern.compile(packageDirName + "/(.*?)/.*").matcher(name);
                        if (matcher.matches()) {
                            packageFile.add(matcher.group(1));
                        }
                    }
                }
            }else if(Boolean.TRUE.equals(type) && !entry.isDirectory()){
                if (name.startsWith(packageDirName)) {
                    Matcher matcher = Pattern.compile(packageDirName + ".*?/(.*?)").matcher(name);
                    if (matcher.matches()) {
                        Matcher fileMatcher = Pattern.compile("/").matcher(matcher.group(1));
                        if(!fileMatcher.find()){
                            packageFile.add(matcher.group(1));
                        }
                    }
                }
            }else {
                if (entry.isDirectory() && name.startsWith(packageDirName)) {
                    // 如果前半部分和定义的包名相同
                    Matcher matcher = Pattern.compile(packageDirName + "/(.*?)/.*").matcher(name);
                    if (matcher.matches()) {
                        packageFile.add(matcher.group(1));
                    }
                }
            }
        }
        return packageFile;
    }


    /**
     * 获取文件或者文件夹中包路径中文件
     * @param url 地址
     * @param type 获取类型 true表示文件 false表示文件夹
     * @return 有效文件列表
     */
    private static Set<String> getFilePackageFile(URL url,Boolean type) throws UnsupportedEncodingException {
        Set<String> packageFile = new LinkedHashSet<>();
        // 获取包的物理路径
        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
        // 获取此包的目录 建立一个File
        File dir = new File(filePath);
        // 如果不存在或者文件不是目录
        if(!dir.exists() || !dir.isDirectory()){
            return packageFile;
        }
        // 获取目录中文件列表
        File[] dirfiles = dir.listFiles();
        if (dirfiles == null || dirfiles.length <= 0){
            return packageFile;
        }
        for (File file : dirfiles){
            if(type == null){
                packageFile.add(file.getName());
            }else if (Boolean.TRUE.equals(type)){
                if (!file.isDirectory()) {
                    packageFile.add(file.getName());
                }
            }else {
                if (file.isDirectory()) {
                    packageFile.add(file.getName());
                }
            }
        }
        return packageFile;
    }

    /**
     * 获取包路径中文件
     * @param packPath 包路径
     * @param type 获取类型 true表示文件 false表示文件夹
     * @return 有效文件列表（不包含空目录）
     */
    public static Set<String> getPackageFile(String packPath,Boolean type){
        Set<String> packageFile = new LinkedHashSet<>();
        String packageDirName = packPath.replace('.', '/');
        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 迭代包路径
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                if("file".equals(protocol)){
                    Set<String> filePackageFile = getFilePackageFile(url,type);
                    packageFile.addAll(filePackageFile);
                }else if ("jar".equals(protocol)){
                    packageFile.addAll(getJarPackageFile( url, type, packageDirName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packageFile;
    }

    /**
     * 扫描Class
     * @param packs 包
     * @return class列表
     */
    public static Set<Class<?>> scanClases(String... packs){
        Set<Class<?>> classes = new LinkedHashSet<>();
        for (String pack : packs){
            Set<String> packages = new LinkedHashSet<>();
            for (String packName : scanPackage(pack,packages)){
                scanClases(packName,classes);
            }
        }
        return classes;
    }

    /**
     * 扫描class
     * @param pack 包
     * @return class列表
     */
    private static Set<Class<?>> scanClases(String pack,Set<Class<?>> classes){
        for (String className : getPackageFile(pack,true)){
            className = className.substring(0,className.length() - 6);
            try {
                classes.add(Class.forName(pack + '.' + className));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    /**
     * 判断是否具有指定注解类型（JDK9中可以用）
     * @param clazz 需要判断的类
     * @param annotationClass 注解类
     * @param ergodic 是否进行遍历 true 进行遍历 false不进行遍历
     * @return  true 标识拥有该注解 false 没有该注解
     */
    public static boolean verifyAnnotation(Class<?> clazz,Class<?> annotationClass,boolean ergodic){
        // 获取到clazz的注解
        Annotation[] annotations = clazz.getAnnotations();
        if(annotations.length <= 0){
            return false;
        }

        // 开始遍历注解
        for (Annotation annotation : annotations){
            if (annotation.annotationType() != Deprecated.class &&
                    annotation.annotationType() != SuppressWarnings.class &&
                    annotation.annotationType() != Override.class &&
                    // 该注解为 JDK9中的注解
                    annotation.annotationType() != Generated.class &&
                    annotation.annotationType() != Target.class &&
                    annotation.annotationType() != Retention.class &&
                    annotation.annotationType() != Documented.class &&
                    annotation.annotationType() != Inherited.class){
                if (annotation.annotationType() == annotationClass) {
                    return true;
                    // 判断是否进行迭代
                } else if(ergodic){
                    return  verifyAnnotation(annotation.annotationType(),annotationClass,ergodic);
                }
            }
        }
        return false;
    }
}
