package at.tugraz.oop2.shared;

import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class FractalRenderOptions implements Serializable {
    protected DoubleProperty centerX;
    protected DoubleProperty centerY;
    protected IntegerProperty width;
    protected IntegerProperty height;
    protected DoubleProperty zoom;
    protected DoubleProperty power;
    protected IntegerProperty iterations;
    protected FractalType type;
    protected Property<ColourModes> mode;
    protected LongProperty requestId;
    protected IntegerProperty totalFragments;
    protected IntegerProperty fragmentNumber;
	private Property<RenderMode> renderMode;
    private StringProperty connection;

    protected IntegerProperty taskPerWorker;

    public IntegerProperty getIterations() {
        return iterations;
    }
    public DoubleProperty getCenterX() {
        return centerX;
    }
    public DoubleProperty getCenterY() {
        return centerY;
    }
    public IntegerProperty getWidth() {
        return width;
    }
    public IntegerProperty getHeight() {
        return height;
    }
    public DoubleProperty getZoom() {
        return zoom;
    }
    public DoubleProperty getPower() {
        return power;
    }
    public FractalType getType() {
        return type;
    }
    public Property<ColourModes> getColorMode() {
        return mode;
    }
    public LongProperty getRequestID() {
        return requestId;
    }
    public IntegerProperty gettotalFrag() {
        return fragmentNumber;
    }
    public Property<RenderMode> getRenderMode() {
        return renderMode;
    }

    public void setCenterX(double valuex){
        centerX.set(valuex);
    }
    public void setCenterY(double valuex){
        centerY.set(valuex);
    }

    public void setZoom(double valuex){
        zoom.set(valuex);
    }

}
