package com.tts.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

public class MscTtsUtil {
	private static final String APPID = "a";
	private static MscTtsUtil mObject;
	
	private MscTtsUtil(){
		SpeechUtility.createUtility("appid=" + APPID);
	}
	
	public static MscTtsUtil getMscObj() {
		if (mObject == null)
			mObject = new MscTtsUtil();
		return mObject;
	}
	
	
	/**
	 * 合成
	 */
	public void Synthesize(String text,String voiceName) {
		
		
		SpeechSynthesizer speechSynthesizer = SpeechSynthesizer
				.createSynthesizer();
		
		speechSynthesizer.setParameter("ttp", "cssml");
		speechSynthesizer.setParameter(SpeechConstant.TEXT_ENCODING, "UTF-8");
		
		// 设置发音人
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "vixf");//小峰
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "aisxqiang");//湖南话
		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "vinn");//楠楠 	童声
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "aisbabyxu");//许小宝 	童声
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "aisxping");//小萍	青年女声
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "aisjinger");//小婧	青年女声
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "yefang");//叶芳	青年女声
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "aisduck");//鸭先生	卡通人物
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "aisxmeng");//小梦	       青年女声
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaowanzi");//小丸子	卡通人物
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "dalong");//小梅	粤语女声
//		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoqian");//东北
		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, voiceName);
		// 设置语速，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.SPEED, "65");
		// 设置语调，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
		speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
		speechSynthesizer.setParameter(SpeechConstant.BACKGROUND_SOUND, "0");
		// 设置音量，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");
		// 设置合成音频保存位置（可自定义保存位置），默认保存在“./iflytek.pcm”
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = voiceName;
		speechSynthesizer.synthesizeToUri(text, "F:\\workspace\\myworkspace\\integrated-platform\\platform-msc\\src\\main\\webapp\\download\\"+fileName+".pcm",
				synthesizeToUriListener);
	}
	
	/**
	 * 合成监听器
	 */
	SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {

		public void onBufferProgress(int progress) {
			System.out.println("*************合成进度*************" + progress);

		}

		public void onSynthesizeCompleted(String uri, SpeechError error) {
			if (error == null) {
				System.out.println("*************合成成功*************");
				System.out.println("合成音频生成路径：" + uri);
				try {
					convert();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				System.out.println("*************" + error.getErrorCode()
						+ "*************");

		}

	};
	
	private void convert() throws Exception{
		List<String> pcmList = new ArrayList<String>();
		String dirpath = "F:\\workspace\\myworkspace\\integrated-platform\\platform-msc\\src\\main\\webapp\\download\\";
		pcmList = FileUtil.getFiles(dirpath,pcmList, "pcm");
		//pcm to wav
		System.out.println(pcmList.size());
		for (String string : pcmList) {
			WaveHeader.pcmToWav(dirpath+string, dirpath+"\\wav\\"+string.replace("pcm", "wav"));
		}
		//wav to mp3
		List<String> wavList = new ArrayList<String>();
		wavList = FileUtil.getFiles(dirpath+"\\wav", wavList, "wav");
		for (String string : wavList) {
			WavToMp3.execute(new File(dirpath+"\\wav\\" + string), dirpath+"\\mp3\\" + string.replace("wav", "mp3"));
			System.out.println(string + " convert mp3 ok");
		}
	}
	public static void main(String[] args) throws Exception {
		new MscTtsUtil().convert();
	}
}
