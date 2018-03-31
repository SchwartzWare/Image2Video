package de.schwartzware.image2video_gui.model;

import java.awt.Dimension;

import org.bytedeco.javacpp.avcodec;

public class RecorderProfile {
	public String name = "H.264";
	public Dimension resolution = new Dimension(1920, 1080);
	public boolean allowCustomResolution = true;
	public int videoCodec = avcodec.AV_CODEC_ID_H264;
	public boolean interleaved = false;
	public int videoBitrate = -1;
	public String format = "mp4";
	public double videoQuality = 1;
	public double frameRate = 25;
	public int pixelFormat = -1; // default

	public RecorderProfile() {

	}

	public RecorderProfile copy() {
		RecorderProfile rp = new RecorderProfile();
		rp.name = name;
		rp.allowCustomResolution = allowCustomResolution;
		rp.resolution = resolution;
		rp.videoCodec = videoCodec;
		rp.interleaved = interleaved;
		rp.videoBitrate = videoBitrate;
		rp.format = format;
		rp.videoQuality = videoQuality;
		rp.frameRate = frameRate;
		rp.pixelFormat = pixelFormat;

		return rp;
	}

	public RecorderProfile setResolution(Dimension d) {
		this.resolution = d;
		return this;
	}

	public RecorderProfile changeName(String name) {
		this.name = name;
		return this;
	}

	public RecorderProfile setInterleaved(boolean b) {
		this.interleaved = b;
		return this;
	}

	public RecorderProfile setVideoBitrate(int videoBitrate) {
		this.videoBitrate = videoBitrate;
		return this;
	}

	public RecorderProfile setFormat(String format) {
		this.format = format;
		return this;
	}

	public RecorderProfile setVideoQuality(double videoQuality) {
		this.videoQuality = videoQuality;
		return this;
	}

	public RecorderProfile setFrameRate(double frameRate) {
		this.frameRate = frameRate;
		return this;
	}

	public RecorderProfile setPixelFormat(int pixelFormat) {
		this.pixelFormat = pixelFormat;
		return this;
	}

	public static RecorderProfile make(String name, Dimension resolution, boolean allowCustomResolution, int videoCodec,
			boolean interleaved, int videoBitrate, String format, double videoQuality, double frameRate,
			int pixelFormat) {
		RecorderProfile rp = new RecorderProfile();
		rp.name = name;
		rp.allowCustomResolution = allowCustomResolution;
		rp.resolution = resolution;
		rp.videoCodec = videoCodec;
		rp.interleaved = interleaved;
		rp.videoBitrate = videoBitrate;
		rp.format = format;
		rp.videoQuality = videoQuality;
		rp.frameRate = frameRate;
		rp.pixelFormat = pixelFormat;
		return rp;
	}
}