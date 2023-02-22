package at.tugraz.oop2.shared;

import javafx.beans.property.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JuliaRenderOptions extends FractalRenderOptions {
    private DoubleProperty constantX;
    private DoubleProperty constantY;

    public JuliaRenderOptions(DoubleProperty centerX, DoubleProperty centerY, IntegerProperty width, IntegerProperty height, DoubleProperty zoom, DoubleProperty power, IntegerProperty iterations, DoubleProperty constantX, DoubleProperty constantY, Property<ColourModes> mode, IntegerProperty fragmentNumber, IntegerProperty totalFragments, Property<RenderMode> renderMode, StringProperty conncetion,  IntegerProperty tasks) {
        super(centerX, centerY, width, height, zoom, power, iterations, FractalType.JULIA, mode, new SimpleLongProperty(0), totalFragments, fragmentNumber, renderMode, conncetion, tasks);
        this.constantX = constantX;
        this.constantY = constantY;
    }

    public JuliaRenderOptions(DoubleProperty centerX, DoubleProperty centerY, IntegerProperty width, IntegerProperty height, DoubleProperty zoom, DoubleProperty power, IntegerProperty iterations, DoubleProperty constantX, DoubleProperty constantY, Property<ColourModes> mode, Property<RenderMode> renderMode, StringProperty conncetion,  IntegerProperty tasks) {
        this(centerX, centerY, width, height, zoom, power, iterations, constantX, constantY, mode, new SimpleIntegerProperty(0), new SimpleIntegerProperty(1), renderMode, conncetion, tasks);

    }



}
