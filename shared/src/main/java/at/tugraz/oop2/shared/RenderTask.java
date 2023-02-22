package at.tugraz.oop2.shared;

import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;

public abstract class RenderTask extends Task<String> {

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub

		return null;
	}

	public RenderTask(FractalRenderOptions renderOptions, Canvas canvas) {
		super();
		// TODO Auto-generated constructor stub
	}



}
