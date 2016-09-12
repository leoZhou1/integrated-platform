package com.tts.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    // 获取路径path下的所有文件名，存放在List<String>
	public static List<String> getFiles(String path, List<String> data) {
        File f=new File(path);
        if (f.isDirectory()) {
            File[] fs=f.listFiles();
            for (int i=0;i<fs.length;i++) {
                data=getFiles(fs[i].getPath(), data);
            }
        } else if (f.getName().endsWith(".ftl")) {
            data.add(f.getName());
        }
        return data;
    }
	
	public static List<String> getFiles(String path,List<String> data,String postfix) {
        File f=new File(path);
        if (f.isDirectory()) {
            File[] fs=f.listFiles();
            for (int i=0;i<fs.length;i++) {
                data=getFiles(fs[i].getPath(),data, postfix);
            }
        } else if (f.getName().endsWith(postfix)) {
            data.add(f.getName());
        }
        return data;
    }
}
