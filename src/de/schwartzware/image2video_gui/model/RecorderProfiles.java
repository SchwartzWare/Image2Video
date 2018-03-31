package de.schwartzware.image2video_gui.model;

import java.awt.Dimension;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avutil;

public class RecorderProfiles {
	public static final RecorderProfile DNXHD_DEMO = RecorderProfile.make("DNxHD Demo", new Dimension(1920, 1080),
			false, avcodec.AV_CODEC_ID_DNXHD, true, 120000000, "mxf", 1, 25, avutil.AV_PIX_FMT_YUV422P);
	public static final RecorderProfile H264_DEMO = new RecorderProfile().changeName("H.264 Demo");
}