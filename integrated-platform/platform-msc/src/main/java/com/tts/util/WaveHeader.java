package com.tts.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * PCM录音数据转Wav格式
 * @author zhouli
 * 代码取自 http://ikinglai.blog.51cto.com/6220785/1224494/
 */
public class WaveHeader {
	private char fileID[] = { 'R', 'I', 'F', 'F' };

	private int fileLength;

	private char wavTag[] = { 'W', 'A', 'V', 'E' };

	private char fmtHdrID[] = { 'f', 'm', 't', ' ' };

	private int fmtHdrLeth = 16;

	private short formatTag = 1;

	public short channels = 1;

	public short sampleRate = 16000;

	public short bitsPerSample = 16;

	private short blockAlign = (short) (channels * bitsPerSample / 8);

	private int avgBytesPerSec = blockAlign * sampleRate;

	private char dataHdrID[] = { 'd', 'a', 't', 'a' };

	private int dataHdrLeth;

	public WaveHeader(int fileLength) {

		this.fileLength = fileLength + (44 - 8);

		dataHdrLeth = fileLength;

	}

	public WaveHeader(int fileLength, short channels, short sampleRate, short bitsPerSample) {

		this.fileLength = fileLength + (44 - 8);

		dataHdrLeth = fileLength;

		this.channels = channels;

		this.sampleRate = sampleRate;

		this.bitsPerSample = bitsPerSample;

		blockAlign = (short) (channels * bitsPerSample / 8);

		avgBytesPerSec = blockAlign * sampleRate;

	}

	/**
	 * 
	 * @return byte[] 44个字节
	 * 
	 * @throws IOException
	 * 
	 */

	public byte[] getHeader() throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		WriteChar(bos, fileID);

		WriteInt(bos, fileLength);

		WriteChar(bos, wavTag);

		WriteChar(bos, fmtHdrID);

		WriteInt(bos, fmtHdrLeth);

		WriteShort(bos, formatTag);

		WriteShort(bos, channels);

		WriteInt(bos, sampleRate);

		WriteInt(bos, avgBytesPerSec);

		WriteShort(bos, blockAlign);

		WriteShort(bos, bitsPerSample);

		WriteChar(bos, dataHdrID);

		WriteInt(bos, dataHdrLeth);

		bos.flush();

		byte[] r = bos.toByteArray();

		bos.close();

		return r;

	}

	private void WriteShort(ByteArrayOutputStream bos, int s)

			throws IOException {

		byte[] mybyte = new byte[2];

		mybyte[1] = (byte) ((s << 16) >> 24);

		mybyte[0] = (byte) ((s << 24) >> 24);

		bos.write(mybyte);

	}

	private void WriteInt(ByteArrayOutputStream bos, int n) throws IOException {

		byte[] buf = new byte[4];

		buf[3] = (byte) (n >> 24);

		buf[2] = (byte) ((n << 8) >> 24);

		buf[1] = (byte) ((n << 16) >> 24);

		buf[0] = (byte) ((n << 24) >> 24);

		bos.write(buf);

	}

	private void WriteChar(ByteArrayOutputStream bos, char[] id) {

		for (int i = 0; i < id.length; i++) {

			char c = id[i];

			bos.write(c);

		}

	}
	
	public static void main(String[] args) throws IOException {
		String src = "F:\\360Downloads\\Java_voice_1.018_579f01f1\\sample\\MscInvisibleDemo\\tts_test.pcm";
		String target = "F:\\360Downloads\\Java_voice_1.018_579f01f1\\sample\\MscInvisibleDemo\\iflytek_tts.wav";
		File file = new File(src);
			System.out.println(file.length());
			
		// fileLength 录音数据的长度

		WaveHeader header = new WaveHeader((int)file.length());
		
		// 返回44个字节的数组

		byte[] waveHeaderBytes = header.getHeader();
		
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(target);

		// 计算长度
		byte[] buf = new byte[1024 * 4];
		int size = fis.read(buf);
		int PCMSize = 0;
		while (size != -1) {
			PCMSize += size;
			size = fis.read(buf);
		}
		fis.close();
		assert waveHeaderBytes.length == 44; // WAV标准，头部应该是44字节
		// write header
		fos.write(waveHeaderBytes, 0, waveHeaderBytes.length);
		// write data stream
		fis = new FileInputStream(src);
		size = fis.read(buf);
		while (size != -1) {
			fos.write(buf, 0, size);
			size = fis.read(buf);
		}
		fis.close();
		fos.close();
		System.out.println("Convert OK!");
	}
	
	public static void pcmToWav(String src,String target) throws IOException{
		File file = new File(src);
		System.out.println(file.length());
		
		// fileLength 录音数据的长度
	
		WaveHeader header = new WaveHeader((int)file.length());
		
		// 返回44个字节的数组
	
		byte[] waveHeaderBytes = header.getHeader();
		
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(target);
	
		// 计算长度
		byte[] buf = new byte[1024 * 4];
		int size = fis.read(buf);
		int PCMSize = 0;
		while (size != -1) {
			PCMSize += size;
			size = fis.read(buf);
		}
		fis.close();
		assert waveHeaderBytes.length == 44; // WAV标准，头部应该是44字节
		// write header
		fos.write(waveHeaderBytes, 0, waveHeaderBytes.length);
		// write data stream
		fis = new FileInputStream(src);
		size = fis.read(buf);
		while (size != -1) {
			fos.write(buf, 0, size);
			size = fis.read(buf);
		}
		fis.close();
		fos.close();
		System.out.println("wav Convert OK!");
	}
}
