package at.tugraz.oop2.shared;

import at.tugraz.oop2.shared.exception.InvalidDepthException;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.input.MouseButton;

import javax.lang.model.type.ErrorType;
import java.awt.*;
import java.nio.ByteBuffer;

import static at.tugraz.oop2.shared.FractalLogger.logRenderCallGUI;
import static java.lang.Thread.sleep;

public class RenderTaskMandelbrot extends RenderTask implements Runnable{
    private MandelbrotRenderOptions renderOptions;
    private Canvas canvas;
    private boolean something_happened;
    private SimpleImage simple_image;
    private double power;

    public SimpleImage getSimpleImageMandelbrot(){return simple_image;};
    public boolean draw_finished_bool;

    public void run(){
        try {
            drawMandelbrot();
        } catch (InvalidDepthException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public RenderTaskMandelbrot(MandelbrotRenderOptions renderOptions, Canvas canvas) {
        super(renderOptions, canvas);
            this.renderOptions = renderOptions;
            this.canvas = canvas;
            this.something_happened = false;
            this.simple_image = new SimpleImage((int)canvas.getWidth(), (int)canvas.getHeight());
            this.power = renderOptions.getPower().getValue();
            this.draw_finished_bool = true;


        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("InfiniteLoopStatement")
    public void drawMandelbrot() throws InvalidDepthException, InterruptedException {
        double mandel_center_x;
        double mandel_center_y;
        double mandel_zoom;
        double mandel_zoom_old = 0;
        int height;
        int width;
        ComplexNumber c1 = new ComplexNumber(power);
        //System.out.println("This is offset " + size);
//        System.out.println("this is my test output 111 " + String.valueOf(renderOptions.getColorMode().getValue()));
//        logRenderCallGUI(renderOptions);
        while(true){

                height = (int) canvas.getHeight() ;
                width = (int) canvas.getWidth() ;
                simple_image = new SimpleImage(width, height);

                c1.setReal(0.0);
                c1.setImaginary(0.0);
                c1.setPow(power);
                mandel_center_x = renderOptions.getCenterX().getValue();
                mandel_center_y = renderOptions.getCenterY().getValue();
                mandel_zoom = renderOptions.getZoom().getValue();
                if (mandel_zoom_old != mandel_zoom){ FractalLogger.logZoomGUI(mandel_zoom, FractalType.MANDELBROT);}
                mandel_zoom_old = mandel_zoom;

                for (int y = 0; y < height; y++) {

                    for (int x = 0; x < width; x++) {

                        simple_image.setPixel(x, y,
                                c1.drawMandel((((x - width / 2.0) / (100 * (5.34 * mandel_zoom + 1))) + mandel_center_x),
                                        (((y - height / 2.0) / (100 * (5.34 * mandel_zoom + 1))) + mandel_center_y), renderOptions.getIterations(), String.valueOf(renderOptions.getColorMode().getValue())));
                    }
                }
                if(!something_happened)
                {
                    GraphicsContext gc = canvas.getGraphicsContext2D();

                    PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
                    gc.getPixelWriter().setPixels(0, 0, width, height, pixelFormat, simple_image.getByteData(), 0, width * 3);
                    if(draw_finished_bool){
                        FractalLogger.logDrawDoneGUI(FractalType.MANDELBROT);
                        draw_finished_bool = false;
                }
                    //          System.out.println("This is height and width " + canvas.getHeight() + canvas.getWidth());
                }
                else{
                    GraphicsContext gc = canvas.getGraphicsContext2D();

                    PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
                    gc.getPixelWriter().setPixels(0, 0, width, height, pixelFormat, simple_image.getByteData(), 0, width * 3);

                    FractalLogger.logRenderFinishedGUI(FractalType.MANDELBROT, simple_image);
                    something_happened = false;

                }

                ///System.out.println("This is height and width " + canvas.getHeight() + canvas.getWidth());


        }

    }

    public void updateMandel(MandelbrotRenderOptions renderOptions, Canvas canvas){
        this.renderOptions = renderOptions;
        this.canvas = canvas;
        //this.something_happened = false;
        this.power = renderOptions.getPower().getValue();
    }
    public void sendInterrupt(){
        something_happened = true;
        draw_finished_bool = true;
    }
}
