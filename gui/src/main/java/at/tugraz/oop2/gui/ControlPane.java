package at.tugraz.oop2.gui;

import at.tugraz.oop2.shared.JuliaRenderOptions;
import at.tugraz.oop2.shared.MandelbrotRenderOptions;
import at.tugraz.oop2.shared.RenderMode;
import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javafx.util.converter.NumberStringConverter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ControlPane {

    public List<TextField> field_list;
    public  List<Text> text_list;
    public ChoiceBox mode_box;
    public ChoiceBox render_box;
    public Button connection_editor = new Button("Edit Connections");

    public ControlPane(){
        field_list = new ArrayList<>();
        text_list = new ArrayList<>();
        mode_box = new ChoiceBox();
        render_box = new ChoiceBox<>();
        mode_box.getItems().add("BLACK_WHITE");
        mode_box.getItems().add("COLOUR_FADE");
        render_box.getItems().add("LOCAL");
        render_box.getItems().add("DISTRIBUTED");
    }
    public void addFields() {
        TextField iterations_field = new TextField();
        TextField mandelBrotX_field = new TextField();
        TextField mandelBrotY_field = new TextField();
        TextField mandelBrotZoom_field = new TextField();
        TextField juliaX_field = new TextField();
        TextField juliaY_field = new TextField();
        TextField juliaZoom_field = new TextField();
        TextField tasksPerWorker_field = new TextField();
        TextField power_field = new TextField();
        //TextField renderMode_field = new TextField();
        //TextField connection_field = new TextField();

        field_list.add(iterations_field);
        field_list.add(mandelBrotX_field);
        field_list.add(mandelBrotY_field);
        field_list.add(mandelBrotZoom_field);
        field_list.add(juliaX_field);
        field_list.add(juliaY_field);
        field_list.add(juliaZoom_field);
        field_list.add(tasksPerWorker_field);
        field_list.add(power_field);
        //field_list.add(renderMode_field);
        //field_list.add(connection_field);
    }

    public void addTextToFields() {
        Text iterations_text = new Text("Iterations");
        Text mandelBrotX_text = new Text("Mandelbrot X center");
        Text mandelBrotY_text = new Text("Mandelbrot Y center");
        Text mandelBrotZoom_text = new Text("Mandelbrot Zoom");
        Text juliaX_text = new Text("Julia X center");
        Text juliaY_text = new Text("Julia Y center");
        Text juliaZoom_text = new Text("Julia Zoom");
        Text mode_text = new Text("Colour Mode");
        Text renderMode_text = new Text("Render Mode");
        Text tasksPerWorker_text = new Text("Task per Worker");
        Text connection_text = new Text("Connection Editor");
        Text power_text = new Text("Power");

        text_list.add(iterations_text);
        text_list.add(mandelBrotX_text);
        text_list.add(mandelBrotY_text);
        text_list.add(mandelBrotZoom_text);
        text_list.add(juliaX_text);
        text_list.add(juliaY_text);
        text_list.add(juliaZoom_text);
        text_list.add(mode_text);
        text_list.add(renderMode_text);
        text_list.add(tasksPerWorker_text);
        text_list.add(connection_text);
        text_list.add(power_text);
    }
    public void setValuesToFields(Pair<JuliaRenderOptions, MandelbrotRenderOptions> pair) {
        field_list.get(0).setText(String.valueOf(pair.getKey().getIterations()));
        field_list.get(1).setText(String.valueOf(pair.getValue().getCenterX()));
        field_list.get(2).setText(String.valueOf(pair.getValue().getCenterY()));
        field_list.get(3).setText(String.valueOf(pair.getValue().getZoom()));
        field_list.get(4).setText(String.valueOf(pair.getKey().getCenterX()));
        field_list.get(5).setText(String.valueOf(pair.getKey().getCenterY()));
        field_list.get(6).setText(String.valueOf(pair.getKey().getZoom()));
        field_list.get(8).setText(String.valueOf(pair.getKey().getPower()));

        field_list.get(7).setText(String.valueOf(pair.getKey().getTaskPerWorker().getValue()));
        mode_box.setValue(String.valueOf(pair.getValue().getColorMode()));
        render_box.setValue(String.valueOf(pair.getValue().getRenderMode()));
    }

    public GridPane addToPane(GridPane controlPane) {
        controlPane.add(text_list.get(0), 0, 0, 1, 1);
        controlPane.add(text_list.get(11), 0, 1, 1, 1);
        controlPane.add(text_list.get(1), 0, 2, 1, 1);
        controlPane.add(text_list.get(2), 0, 3, 1, 1);
        controlPane.add(text_list.get(3), 0, 4, 1, 1);
        controlPane.add(text_list.get(4), 0, 5, 1, 1);
        controlPane.add(text_list.get(5), 0, 6, 1, 1);
        controlPane.add(text_list.get(6), 0, 7, 1, 1);
        controlPane.add(text_list.get(7), 0, 8, 1, 1);
        controlPane.add(text_list.get(8), 0, 9, 1, 1);
        controlPane.add(text_list.get(9), 0, 10, 1, 1);
        controlPane.add(text_list.get(10), 0, 11, 1, 1);
        Text number_connected = new Text("Connected Workers");
        controlPane.add(number_connected, 0, 12, 1, 1);

        controlPane.add(field_list.get(0), 1, 0, 1, 1);
        controlPane.add(field_list.get(8), 1, 1, 1, 1);
        controlPane.add(field_list.get(1), 1, 2, 1, 1);
        controlPane.add(field_list.get(2), 1, 3, 1, 1);
        controlPane.add(field_list.get(3), 1, 4, 1, 1);
        controlPane.add(field_list.get(4), 1, 5, 1, 1);
        controlPane.add(field_list.get(5), 1, 6, 1, 1);
        controlPane.add(field_list.get(6), 1, 7, 1, 1);
        controlPane.add(mode_box, 1, 8, 1, 1);
        controlPane.add(render_box, 1, 9, 1, 1);
        controlPane.add(field_list.get(7), 1, 10, 1, 1);

        controlPane.add(connection_editor, 1, 11, 1, 1);
        Text number_connected_zero = new Text("0");
        controlPane.add(number_connected_zero, 1, 12, 1, 1);
        return controlPane;
    }
    public void bindArguments(Pair<JuliaRenderOptions, MandelbrotRenderOptions> pair) {
        field_list.get(0).textProperty().bindBidirectional(pair.getKey().getIterations(), new NumberStringConverter());
        field_list.get(0).textProperty().bindBidirectional(pair.getValue().getIterations(), new NumberStringConverter());
        field_list.get(8).textProperty().bindBidirectional(pair.getKey().getPower(), new NumberStringConverter());
        field_list.get(8).textProperty().bindBidirectional(pair.getValue().getPower(), new NumberStringConverter());

        field_list.get(1).textProperty().bindBidirectional(pair.getValue().getCenterX(), new NumberStringConverter());
        field_list.get(2).textProperty().bindBidirectional(pair.getValue().getCenterY(), new NumberStringConverter());
        field_list.get(3).textProperty().bindBidirectional(pair.getValue().getZoom(), new NumberStringConverter());
        field_list.get(4).textProperty().bindBidirectional(pair.getKey().getCenterX(), new NumberStringConverter());
        field_list.get(5).textProperty().bindBidirectional(pair.getKey().getCenterY(), new NumberStringConverter());
        field_list.get(6).textProperty().bindBidirectional(pair.getKey().getZoom(), new NumberStringConverter());


        mode_box.valueProperty().bindBidirectional(pair.getKey().getMode());
        render_box.valueProperty().bindBidirectional(pair.getKey().getRenderMode());
    }


}
