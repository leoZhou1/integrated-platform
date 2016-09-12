package com.tts.util;

import java.io.File;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

public class WavToMp3 {
	/**
     * 执行转化过程
     * 
     * @param source
     *            源文件
     * @param desFileName
     *            目标文件名
     * @return 转化后的文件
     */
    public static File execute(File source, String desFileName)
            throws Exception {
        File target = new File(desFileName);
 
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(new Integer(128000));
        audio.setChannels(new Integer(2));
        audio.setSamplingRate(new Integer(44100));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);
 
        return target;
    }
    
    public static void main(String[] args) throws Exception {
    	String filePath = "F:\\360Downloads\\Java_voice_1.018_579f01f1\\sample\\MscInvisibleDemo\\";
    	String fileName = "晨悦生活0826";
		WavToMp3.execute(new File(filePath + fileName+".wav"), filePath + fileName+".mp3");
		System.out.println("完成");
	}
}