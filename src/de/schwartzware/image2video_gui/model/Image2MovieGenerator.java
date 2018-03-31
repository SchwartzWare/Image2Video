package de.schwartzware.image2video_gui.model;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class Image2MovieGenerator {
	protected File inputFile;
	protected File outputFile;
	protected float duration;
	protected int frames;
	protected RecorderProfile recorderProfile;
	protected boolean useImageSizeAsResolution = false;

	protected ArrayList<ProgressListener> progressListeners = new ArrayList<ProgressListener>();

	public Image2MovieGenerator(File inputFile, File outputFile, float duration, RecorderProfile recorderProfile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.duration = duration;
		this.recorderProfile = recorderProfile;

		frames = (int) (duration * recorderProfile.frameRate);
	}

	public void setUseImageSizeAsResolution(boolean b) {
		this.useImageSizeAsResolution = b;
	}

	public void addProgressListener(ProgressListener pl) {
		progressListeners.add(pl);
	}

	public void removeProgressListener(ProgressListener pl) {
		ArrayList<ProgressListener> removeCollection = new ArrayList<ProgressListener>();
		progressListeners.removeAll(removeCollection);
	}

	public static Dimension getImageDimension(File imgFile) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(imgFile);
		} catch (IOException e) {
			return new Dimension(-1, -1);
		}

		return new Dimension(img.getWidth(), img.getHeight());
	}

	public void start() {
		this.convertImagetoMovie(inputFile, outputFile);
	}

	protected void convertImagetoMovie(File imgFile, File outputFile) {
		OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
		Dimension resolution = recorderProfile.resolution;
		if (useImageSizeAsResolution) {
			resolution = Image2MovieGenerator.getImageDimension(imgFile);
		}
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile.getAbsolutePath(), resolution.width,
				resolution.height);
		try {
			recorder.setFrameRate(recorderProfile.frameRate);
			recorder.setInterleaved(recorderProfile.interleaved);
			recorder.setVideoCodec(recorderProfile.videoCodec);
			recorder.setVideoBitrate(recorderProfile.videoBitrate);
			recorder.setPixelFormat(recorderProfile.pixelFormat);
			recorder.setFormat(recorderProfile.format);
			recorder.setVideoQuality(recorderProfile.videoQuality);
			recorder.start();

			Frame frame = grabberConverter.convert(cvLoadImage(imgFile.getAbsolutePath()));
			for (int i = 1; i <= frames; i++) {
				recorder.record(frame);
				this.onProgress(i);
			}
			recorder.stop();
		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
			e.printStackTrace();
		}

	}

	protected void onProgress(int frame) {
		for (ProgressListener pl : progressListeners) {
			pl.onProgress(frames / frame, frame);
		}
	}

	public interface ProgressListener {
		public void onProgress(double progress, int frame);
	}
}