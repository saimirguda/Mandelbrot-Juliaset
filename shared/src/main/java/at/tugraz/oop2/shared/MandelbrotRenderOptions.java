package at.tugraz.oop2.shared;

import javafx.beans.property.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MandelbrotRenderOptions extends FractalRenderOptions {


    public MandelbrotRenderOptions(DoubleProperty centerX, DoubleProperty centerY, IntegerProperty width, IntegerProperty height, DoubleProperty zoom, DoubleProperty power, IntegerProperty iterations, Property<ColourModes> mode, IntegerProperty fragmentNumber, IntegerProperty totalFragments, Property<RenderMode> renderMode, StringProperty conncetion,  IntegerProperty tasks) {
        super(centerX, centerY, width, height, zoom, power, iterations, FractalType.MANDELBROT, mode, new SimpleLongProperty(0), totalFragments, fragmentNumber, renderMode, conncetion, tasks);
    }

    public MandelbrotRenderOptions(DoubleProperty centerX, DoubleProperty centerY, IntegerProperty width, IntegerProperty height, DoubleProperty zoom, DoubleProperty power, IntegerProperty iterations, Property<ColourModes> mode, Property<RenderMode> renderMode, StringProperty conncetion,  IntegerProperty tasks) {
        this(centerX, centerY, width, height, zoom, power, iterations, mode, new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), renderMode, conncetion, tasks);
    }

}
