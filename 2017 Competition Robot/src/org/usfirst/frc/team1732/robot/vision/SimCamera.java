package org.usfirst.frc.team1732.robot.vision;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class SimCamera implements Runnable {

	private UsbCamera camera;

	private CvSink cvSink;
	private CvSource outputStream;
	private boolean initialized = false;

	private Mat source;

	public SimCamera() {
		this.source = new Mat();
	}
	public void init() {
		this.camera = new UsbCamera("usb cam", "/dev/video0");

		CameraServer.getInstance().addCamera(camera);
		this.camera.setResolution(320, 240);
		this.camera.setFPS(30);
		this.camera.setBrightness(0);
		this.camera.setExposureManual(10);

		this.cvSink = CameraServer.getInstance().getVideo();
		this.outputStream = CameraServer.getInstance().putVideo("cam0", 320, 240);
		this.initialized = true;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) { // thread is always running
			if (this.camera == null && !this.initialized) {
				this.init();
			} else if (this.camera.isConnected() && this.initialized && CameraServer.getInstance() != null) {
				this.cvSink.grabFrame(this.source);
				this.outputStream.putFrame(source);
			}

		}
	}
}
