package at.tugraz.oop2.shared;

import at.tugraz.oop2.shared.exception.InvalidDepthException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;

import java.nio.ByteBuffer;

import static at.tugraz.oop2.shared.FractalLogger.logRenderCallGUI;

public class RenderTaskJuliaset extends RenderTask{
    private JuliaRenderOptions renderOptions;

    private MandelbrotRenderOptions renderOptionsMandel;
    private Canvas canvas;
    private boolean something_happened;

    private double pow;

    public double getPower(){return pow;}

    private SimpleImage simple_image_julia;

    public SimpleImage getSimpleImageJulia(){return simple_image_julia;};
    public boolean draw_finished_bool;

    public void run(){
        try {
            drawJulia();
        } catch (InvalidDepthException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public RenderTaskJuliaset(JuliaRenderOptions renderOptions,MandelbrotRenderOptions renderOptionsMandel, Canvas canvas) {
        super(renderOptions, canvas);
            this.renderOptions = renderOptions;
            this.renderOptionsMandel = renderOptionsMandel;
            this.canvas = canvas;
            this.something_happened = false;
            this.simple_image_julia = new SimpleImage((int)canvas.getWidth(), (int)canvas.getHeight());
            this.pow = renderOptions.getPower().getValue();
            this.draw_finished_bool = true;


        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("InfiniteLoopStatement")
    public void drawJulia() throws InvalidDepthException, InterruptedException {
        int height;
        int width;
        ComplexNumber c1 = new ComplexNumber(pow);

        double julia_center_x;
        double julia_center_y;
        double julia_zoom;
        double julia_zoom_old = 0;

        while (true) {
                height = (int) canvas.getHeight();
                width = (int) canvas.getWidth();
                c1.setReal(0);
                c1.setImaginary(0);
                c1.setPow(pow);
                simple_image_julia = new SimpleImage(width, height);


                julia_center_x = renderOptions.getCenterX().getValue();
                julia_center_y = renderOptions.getCenterY().getValue();
                julia_zoom = renderOptions.getZoom().getValue();
                if (julia_zoom_old != julia_zoom) { FractalLogger.logZoomGUI(julia_zoom, FractalType.JULIA);}
                julia_zoom_old = julia_zoom;


                for (int y = 0; y < height; y++) {

                    for (int x = 0; x < width; x++) {

                        simple_image_julia.setPixel(x, y,
                                c1.drawJulia((((x - width / 2f) / (100 * (2.2 * julia_zoom + 1))) + julia_center_x),
                                        (((y - height / 2f) / (100 * (2.2 * julia_zoom + 1))) + julia_center_y), renderOptions.getIterations(), String.valueOf(renderOptions.getColorMode().getValue()),
                                        renderOptionsMandel.getCenterX().getValue(), renderOptionsMandel.getCenterY().getValue()));
                    }


                }

                if (!something_happened) {
                    GraphicsContext gc_2 = canvas.getGraphicsContext2D();

                    PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
                    gc_2.getPixelWriter().setPixels(0, 0, width, height, pixelFormat, simple_image_julia.getByteData(), 0, width * 3);
                    if(draw_finished_bool){
                        FractalLogger.logDrawDoneGUI(FractalType.JULIA);
                        draw_finished_bool = false;
                    }

                    //julia_service.restart();
                }
                else{
                    GraphicsContext gc_2 = canvas.getGraphicsContext2D();

                    PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
                    gc_2.getPixelWriter().setPixels(0, 0, width, height, pixelFormat, simple_image_julia.getByteData(), 0, width * 3);

                    FractalLogger.logRenderFinishedGUI(FractalType.JULIA, simple_image_julia);
                    something_happened = false;
                }

        }
    }



    public void updateJulia(JuliaRenderOptions renderOptions, Canvas canvas){
        this.renderOptions = renderOptions;
        this.canvas = canvas;
        //this.something_happened = false;
        this.pow = renderOptions.getPower().getValue();

    }
    public void sendInterrupt(){
        draw_finished_bool = true;
        something_happened = true;
    }
}
