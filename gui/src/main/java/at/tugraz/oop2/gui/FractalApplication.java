package at.tugraz.oop2.gui;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import at.tugraz.oop2.shared.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import lombok.Getter;

import javax.swing.*;
import javax.swing.text.DefaultCaret;


public class FractalApplication extends Application {

    public static String[] args;
    private GridPane mainPane;
    private Canvas rightCanvas;
    private Canvas leftCanvas;
    private GridPane controlPane;


    @Getter
    private DoubleProperty leftHeight = new SimpleDoubleProperty();
    @Getter
    private DoubleProperty leftWidth = new SimpleDoubleProperty();
    @Getter
    private DoubleProperty rightHeight = new SimpleDoubleProperty();
    @Getter
    private DoubleProperty rightWidth = new SimpleDoubleProperty();
    private double pos_x = -1.0;
    private double pos_y = -1.0;

    Pair<JuliaRenderOptions, MandelbrotRenderOptions> pair;
    /*
    Service mandel_service = new Service() {
        @Override protected Task createTask() {
            return new Task() {
                @Override public Void call() throws InvalidDepthException {
                    renderMandelBrot(iterations, (String) mode_box.getValue());
                    return null;
                }
            };
        }
    };

     */
    private void updateSizes() {

        Bounds leftSize = mainPane.getCellBounds(0, 0);
        leftCanvas.widthProperty().set(leftSize.getWidth());
        leftCanvas.heightProperty().set(leftSize.getHeight());


        Bounds rightSize = mainPane.getCellBounds(1, 0);
        rightCanvas.widthProperty().set(rightSize.getWidth());
        rightCanvas.heightProperty().set(rightSize.getHeight());
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        mainPane = new GridPane();


        leftCanvas = new Canvas();
        leftCanvas.setCursor(Cursor.HAND);

        mainPane.setGridLinesVisible(true);
        mainPane.add(leftCanvas, 0, 0);

        rightCanvas = new Canvas();
        rightCanvas.setCursor(Cursor.HAND);


        mainPane.add(rightCanvas, 1, 0);

        ColumnConstraints cc1 =
                new ColumnConstraints(100, 100, -1, Priority.ALWAYS, HPos.CENTER, true);
        ColumnConstraints cc2 =
                new ColumnConstraints(100, 100, -1, Priority.ALWAYS, HPos.CENTER, true);
        ColumnConstraints cc3 =
                new ColumnConstraints(400, 400, 400, Priority.ALWAYS, HPos.CENTER, true);

        mainPane.getColumnConstraints().addAll(cc1, cc2, cc3);


        RowConstraints rc1 =
                new RowConstraints(400, 400, -1, Priority.ALWAYS, VPos.CENTER, true);

        mainPane.getRowConstraints().addAll(rc1);

        leftHeight.bind(leftCanvas.heightProperty());
        leftWidth.bind(leftCanvas.widthProperty());
        rightHeight.bind(rightCanvas.heightProperty());
        rightWidth.bind(rightCanvas.widthProperty());

        mainPane.widthProperty().addListener(observable -> updateSizes());
        mainPane.heightProperty().addListener(observable -> updateSizes());


        controlPane = new GridPane();
        ColumnConstraints controlLabelColConstraint =
                new ColumnConstraints(195, 195, 200, Priority.ALWAYS, HPos.CENTER, true);
        ColumnConstraints controlControlColConstraint =
                new ColumnConstraints(195, 195, 195, Priority.ALWAYS, HPos.CENTER, true);
        controlPane.getColumnConstraints().addAll(controlLabelColConstraint, controlControlColConstraint);
        mainPane.add(controlPane, 2, 0);


        Scene scene = new Scene(mainPane);

        primaryStage.setTitle("Fractal Displayer");

        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWING, event -> {
            updateSizes();
        });
        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
            updateSizes();
        });
        primaryStage.addEventHandler(WindowEvent.ANY, event -> {
            updateSizes();
        });

        primaryStage.setWidth(1080);
        primaryStage.setHeight(720);

        primaryStage.setScene(scene);
        primaryStage.show();

        Platform.runLater(() -> {
            updateSizes();
            FractalLogger.logInitializedGUI(mainPane, primaryStage, leftCanvas, rightCanvas);
        });

        // Argument Parsing
        Pair<JuliaRenderOptions, MandelbrotRenderOptions> p = ArgParser.parse(args);
        IntegerProperty property_1 = new SimpleIntegerProperty(rightCanvas.widthProperty().intValue());
        IntegerProperty property_2 = new SimpleIntegerProperty(rightCanvas.heightProperty().intValue());
        IntegerProperty property_3 = new SimpleIntegerProperty(leftCanvas.widthProperty().intValue());
        IntegerProperty property_4 = new SimpleIntegerProperty(leftCanvas.heightProperty().intValue());
        p.getKey().setWidth(property_1);
        p.getKey().setHeight(property_2);
        p.getValue().setWidth(property_3);
        p.getValue().setHeight(property_4);

        // Classes for threads Mandelbrot Juliaset
        ///
        /// Should change it's too similar to Dominik all Listeners !!!!!
        ///
        FractalLogger.logRenderCallGUI(p.getValue());
        RenderTaskMandelbrot a = new RenderTaskMandelbrot(p.getValue(), leftCanvas);
        FractalLogger.logRenderFinishedGUI(FractalType.MANDELBROT, a.getSimpleImageMandelbrot());
        FractalLogger.logRenderCallGUI(p.getKey());
        RenderTaskJuliaset b = new RenderTaskJuliaset(p.getKey(),p.getValue(), rightCanvas);
        FractalLogger.logRenderFinishedGUI(FractalType.JULIA, b.getSimpleImageJulia());

        p.getKey().getWidth().bindBidirectional(rightCanvas.widthProperty());
        p.getKey().getHeight().bindBidirectional(rightCanvas.heightProperty());

        p.getValue().getWidth().bindBidirectional(leftCanvas.widthProperty());
        p.getValue().getHeight().bindBidirectional(leftCanvas.heightProperty());



        //ExecutorService executor = Executors.newFixedThreadPool(10);

        // Threads Mandelbrot Juliaset
        Thread thread1 = new Thread(a);
        Thread thread2 = new Thread(b);
        updateSizes();
        thread1.start();
        thread2.start();
        //executor.submit(b);
//        System.out.println("this is my test output 2 " + p.getKey().getCenterY());


        // ControlPane instantiation and binding
        ///
        /// Should change it's too similar to Dominik all Listeners !!!!!
        ///
        FractalLogger.logArgumentsGUI(p.getValue().getCenterX(), p.getValue().getCenterY(), p.getValue().getZoom(), p.getValue().getPower(), p.getKey().getIterations(),
                p.getKey().getConstantX(), p.getKey().getConstantY(), p.getKey().getZoom(), p.getKey().getColorMode());
        ControlPane control_class = new ControlPane();
        control_class.addFields();
        control_class.addTextToFields();
        control_class.setValuesToFields(p);
        controlPane = control_class.addToPane(controlPane);
        control_class.bindArguments(p);

        // Listeners
        ///
        /// Should change it's too similar to Dominik all Listeners !!!!!
        ///
        ChangeListener<String> updateBothIteration = (observableValue, v1, v2) -> {
            updateSizes();

            //System.out.println("this is my test output " + v1 + " Julia : " + p.getValue().getPower());
           // if ((p.getValue().getPower().getValue() < 2) || !v2.isEmpty() && !v2.matches("[0-9]+") )
           // {
           //     //p.getKey().setPower(new SimpleDoubleProperty(2));
                //p.getValue().setPower(new SimpleDoubleProperty(2));
           //     control_class.field_list.get(8).setText("2"); // ? text
           // }
            if (!v2.matches("[0-9]+") && !v2.isEmpty()) {
                control_class.field_list.get(0).setText("128"); // ? text
            }
            else if (Double.parseDouble(v2) > Integer.MAX_VALUE - 1) {
                control_class.field_list.get(0).setText("128");
            }
            // prevents overflow

            a.sendInterrupt();
            b.sendInterrupt();
            FractalLogger.logRenderCallGUI(p.getKey());
            FractalLogger.logRenderCallGUI(p.getValue());
            a.updateMandel(p.getValue(), leftCanvas);
            b.updateJulia(p.getKey(), rightCanvas);
            //System.out.println("this is my test output " + p.getKey().getPower() + " Julia : " + p.getValue().getPower());


            //b.set(new RenderTaskJuliaset(p.getKey(), rightCanvas));
            //paintMandelbrot();
            //paintJulia();

        };

        control_class.connection_editor.setOnAction(actionEvent -> {
                    createFrame(p);

                }

            );
        ChangeListener<String> updateBothPower = (observableValue, v1, v2) -> {
            updateSizes();

            //System.out.println("this is my test output " + v1 + " Julia : " + p.getValue().getPower());
            // if ((p.getValue().getPower().getValue() < 2) || !v2.isEmpty() && !v2.matches("[0-9]+") )
            // {
            //     //p.getKey().setPower(new SimpleDoubleProperty(2));
            //p.getValue().setPower(new SimpleDoubleProperty(2));
            //     control_class.field_list.get(8).setText("2"); // ? text
            // }
            //double power_value = Math.round((p.getValue().getPower().getValue()));
            //control_class.field_list.get(8).setText(power_value);
            if (!v2.matches("[0-9]+") && !v2.isEmpty()) {
                control_class.field_list.get(8).setText("2"); // ? text
            }
            else if (Integer.parseInt(v2) == 1 && !v2.isEmpty() ) {
                control_class.field_list.get(8).setText("2"); // ? text
            }
            else if (Double.parseDouble(v2) > Integer.MAX_VALUE - 1) {
                control_class.field_list.get(8).setText("2");
            }
            //if(v2.matches())
            // prevents overflow
            //System.out.println("this is my test output " +v2 + " Julia : " + p.getValue().getPower());

            FractalLogger.logRenderCallGUI(p.getKey());
            FractalLogger.logRenderCallGUI(p.getValue());
            a.sendInterrupt();
            b.sendInterrupt();
            a.updateMandel(p.getValue(), leftCanvas);
            b.updateJulia(p.getKey(), rightCanvas);
            //System.out.println("this is my test output " + p.getKey().getPower() + " Julia : " + p.getValue().getPower());


            //b.set(new RenderTaskJuliaset(p.getKey(), rightCanvas));
            //paintMandelbrot();
            //paintJulia();

        };
        ChangeListener<String> updateMandel = (observableValue, v1, v2) -> {
            updateSizes();
            if (!observableValue.getValue().matches("[-]?[0-9]*[.]?[0-9]*") && !observableValue.getValue().isEmpty()) {
              control_class.field_list.get(1).setText("0");
            control_class.field_list.get(2).setText("0");
              control_class.field_list.get(3).setText("0");
            }
            a.sendInterrupt();
            FractalLogger.logRenderCallGUI(p.getValue());
            a.updateMandel(p.getValue(), leftCanvas);

            //b.set(new RenderTaskJuliaset(p.getKey(), rightCanvas));
            //paintMandelbrot();
            //paintJulia();

        };
        ChangeListener<String> updateJulia = (observableValue, v1, v2) -> {
            updateSizes();
            if (!observableValue.getValue().matches("[-]?[0-9]*[.]?[0-9]*") && !observableValue.getValue().isEmpty()) {
                control_class.field_list.get(4).setText("0");
                control_class.field_list.get(5).setText("0");
                control_class.field_list.get(6).setText("0");
            }
            b.sendInterrupt();
            FractalLogger.logRenderCallGUI(p.getKey());
            b.updateJulia(p.getKey(), rightCanvas);
            //b.updateMandel(p.getValue(), leftCanvas);
            //b.set(new RenderTaskJuliaset(p.getKey(), rightCanvas));
            //paintMandelbrot();
            //paintJulia();

        };
        control_class.field_list.get(0).textProperty().addListener(updateBothIteration);
        control_class.field_list.get(8).textProperty().addListener(updateBothPower );

        //System.out.println("this is my test output " + control_class.field_list.get(0).textProperty().getValue());
        //System.out.println("this is my test output " + control_class.field_list.get(1).textProperty().getValue());
        //System.out.println("this is my test output " + control_class.field_list.get(8).textProperty().getValue());
        control_class.field_list.get(1).textProperty().addListener(updateMandel);
        control_class.field_list.get(2).textProperty().addListener(updateMandel);
        control_class.field_list.get(3).textProperty().addListener(updateMandel);
        control_class.field_list.get(4).textProperty().addListener(updateJulia);
        control_class.field_list.get(5).textProperty().addListener(updateJulia);
        control_class.field_list.get(6).textProperty().addListener(updateJulia);
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            updateSizes();
            a.sendInterrupt();
            b.sendInterrupt();
            FractalLogger.logRenderCallGUI(p.getKey());
            FractalLogger.logRenderCallGUI(p.getValue());
            a.updateMandel(p.getValue(), leftCanvas);
            b.updateJulia(p.getKey(), rightCanvas);
            //b.set(new RenderTaskJuliaset(p.getKey(), rightCanvas));
            //paintMandelbrot();
            //paintJulia();
        };
        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.heightProperty().addListener(stageSizeListener);

        leftCanvas.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                resetPos();
            }
        });

        rightCanvas.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                resetPos();
            }
        });

        leftCanvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (checkPos()) {
                    getMouse(mouseEvent);
                } else {
                    double centerX = p.getValue().getCenterX().getValue();
                    double centerY = p.getValue().getCenterY().getValue();
                    double zoom_val = p.getValue().getZoom().getValue();
                    centerX = centerX + (pos_x - mouseEvent.getSceneX()) / 100 / (5.34 * zoom_val + 1);
                    centerY = centerY + (pos_y - mouseEvent.getSceneY()) / 100 / (5.34 * zoom_val + 1);
                    p.getValue().setCenterX(centerX);
                    p.getValue().setCenterY(centerY);

                    getMouse(mouseEvent);
                    updateSizes();
                    a.sendInterrupt();
                    FractalLogger.logRenderCallGUI(p.getKey());
                    a.updateMandel(p.getValue(), leftCanvas);
                    b.sendInterrupt();
                    FractalLogger.logRenderCallGUI(p.getValue());
                    b.updateJulia(p.getKey(), rightCanvas);
                    double scale = Math.pow(10, 4);
                    centerX = Math.round(centerX * scale) / scale;
                    centerY = Math.round(centerY * scale) / scale;
                    FractalLogger.logDragGUI(centerX, centerY, FractalType.MANDELBROT);
                    //thread1.stop();

                }
            }
        });
        leftCanvas.addEventHandler(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override public void handle(ScrollEvent event) {
                double SCROLLSIZE = 0.02;
                if (event.getDeltaY() > 0 || p.getValue().getZoom().getValue() >= 0.02) {
                    double zoom_value =
                            Math.round((p.getValue().getZoom().getValue() + SCROLLSIZE * event.getDeltaY() / 40.0) * 100.0) / 100.0;
                    p.getValue().setZoom(zoom_value);
                    control_class.field_list.get(3).setText(String.valueOf(p.getValue().getZoom().getValue()));
                    //System.out.println(double_list.get(2).getValue() + SCROLLSIZE * event.getDeltaY() / 40.0);
                    updateSizes();
                    a.sendInterrupt();
                    FractalLogger.logRenderCallGUI(p.getValue());
                    a.updateMandel(p.getValue(), leftCanvas);
                    //thread1.stop();

                }
            }

        });

        rightCanvas.addEventHandler(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override public void handle(ScrollEvent event) {
                double SCROLLSIZE = 0.02;
                if (event.getDeltaY() > 0 || p.getKey().getZoom().getValue() >= 0.02) {
                    double zoom_value =
                            Math.round((p.getKey().getZoom().getValue() + SCROLLSIZE * event.getDeltaY() / 40.0) * 100.0) / 100.0;
                    p.getKey().setZoom(zoom_value);
                    control_class.field_list.get(6).setText(String.valueOf(p.getKey().getZoom().getValue()));
                    //julia_service.restart();
                    updateSizes();
                    b.sendInterrupt();
                    FractalLogger.logRenderCallGUI(p.getKey());
                    b.updateJulia(p.getKey(), rightCanvas);
                }
            }
        });



        rightCanvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (checkPos()) {
                    getMouse(mouseEvent);
                } else {
                    double centerX = p.getKey().getCenterX().getValue();
                    double centerY = p.getKey().getCenterY().getValue();
                    double zoom_val = p.getKey().getZoom().getValue();
                    centerX = centerX + (pos_x - mouseEvent.getSceneX()) / 100 / (5.34 * zoom_val + 1);
                    centerY = centerY + (pos_y - mouseEvent.getSceneY()) / 100 / (5.34 * zoom_val + 1);
                    p.getKey().setCenterX(centerX);
                    p.getKey().setCenterY(centerY);


                    getMouse(mouseEvent);
                    updateSizes();
                    b.sendInterrupt();
                    FractalLogger.logRenderCallGUI(p.getKey());
                    b.updateJulia(p.getKey(), rightCanvas);
                    double scale = Math.pow(10, 4);
                    centerX = Math.round(centerX * scale) / scale;
                    centerY = Math.round(centerY * scale) / scale;
                    FractalLogger.logDragGUI(centerX, centerY, FractalType.JULIA);
                    //thread1.stop();
                }
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void createFrame(Pair<JuliaRenderOptions, MandelbrotRenderOptions> p)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("Connections");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   return;
                }
                JPanel panel = new JPanel();
                JTextArea textArea = new JTextArea(20, 55);
                JScrollPane scroller = new JScrollPane(textArea);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);



                textArea.append(p.getKey().getConnection().getValue());
                panel.add(scroller);

                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
    }

    public void resetPos(){
        pos_x = -1;
        pos_y = -1;
    }

    public boolean checkPos(){
        return ((pos_y + pos_x ) < 0);
    }

    public void getMouse(MouseEvent mouseEvent){
        pos_x = mouseEvent.getSceneX();
        pos_y = mouseEvent.getSceneY();
    }
    public double getPos_x(){
        return pos_x;
    }
    public double getPos_y(){
        return pos_y;
    }

}

