package at.tugraz.oop2.gui;

import at.tugraz.oop2.shared.ColourModes;
import at.tugraz.oop2.shared.JuliaRenderOptions;
import at.tugraz.oop2.shared.MandelbrotRenderOptions;
import at.tugraz.oop2.shared.RenderMode;
import javafx.beans.property.*;
import javafx.util.Pair;

public class ArgParser {
	private final static String iterations_="iterations";
	private final static String power_="power";
	private final static String mandelbrotX_="mandelbrotx";
	private final static String mandelbrotY_="mandelbroty";
	private final static String mandelbrotZoom_="mandelbrotzoom";
	private final static String juliaX_="juliax";
	private final static String juliaY_="juliay";
	private final static String juliaZoom_="juliazoom";
	private final static String colourMode_="colourmode";


	//New parameters and settings of the GUI
	private final static String tasksPerWorker_="tasksPerWorker";
	private final static String renderMode_="rendermode";
	private final static String connection_="connection";
	public static Pair<JuliaRenderOptions,MandelbrotRenderOptions> parse(String args[]) {

 IntegerProperty iterations = new SimpleIntegerProperty(128);

DoubleProperty power = new SimpleDoubleProperty(2);

DoubleProperty mandelbrotX = new SimpleDoubleProperty(0);
DoubleProperty mandelbrotY = new SimpleDoubleProperty(0);
DoubleProperty mandelbrotZoom = new SimpleDoubleProperty(0);

DoubleProperty juliaX = new SimpleDoubleProperty(0);


DoubleProperty juliaY = new SimpleDoubleProperty(0);

DoubleProperty juliaZoom =  new SimpleDoubleProperty(0);

Property<ColourModes> cm = new SimpleObjectProperty<>(ColourModes.BLACK_WHITE);


//New parameters and settings of the GUI
IntegerProperty tasksPerWorker = new SimpleIntegerProperty(5);
Property<RenderMode> rm = new SimpleObjectProperty<>(RenderMode.LOCAL);
StringProperty connection = new SimpleStringProperty("localhost:8010");

		for(int i = 0; i < args.length;i++) {

			if(args[i].startsWith("--")) {
				String [] name_value = args[i].toLowerCase().replaceAll("-", "").split("=");

				if(name_value[0].equals(iterations_)) {

					try {
						if (!name_value[1].matches("[0-9]+") && !name_value[1].isEmpty()) {
							iterations.set(128);
						}
						else if (Double.parseDouble(name_value[1]) > Integer.MAX_VALUE - 1) {
							iterations.set(128);
						}
						iterations.set(Integer.parseInt(name_value[1]));
					}catch(NumberFormatException e) {

					}
				}
        		else if(name_value[0].equals(power_)) {
					try {
						if (!name_value[1].matches("[0-9]+") && !name_value[1].isEmpty()) {
							power.set(2);
						}
						else if (Integer.parseInt(name_value[1]) == 1 && !name_value[1].isEmpty() ) {
							power.set(2);
						}
						else if (Double.parseDouble(name_value[1]) > Integer.MAX_VALUE - 1) {
							power.set(2);
						}
						power.set(Double.parseDouble(name_value[1]));
					}catch(NumberFormatException e) {

					}
				}
        		else if(name_value[0].equals(mandelbrotX_)) {
					try {
						mandelbrotX.set(Double.parseDouble(name_value[1]));
					}catch(NumberFormatException e) {

					}
				}
        		else if(name_value[0].equals(mandelbrotY_)) {
					try {
						mandelbrotY.set(Double.parseDouble(name_value[1]));
					}catch(NumberFormatException e) {

					}
				}
        		else if(name_value[0].equals(mandelbrotZoom_)) {
					try {
						mandelbrotZoom.set(Double.parseDouble(name_value[1]));
					}catch(NumberFormatException e) {

					}
				}
        		else if(name_value[0].equals(juliaX_)) {
					try {
						juliaX.set(Double.parseDouble(name_value[1]));
					}catch(NumberFormatException e) {

					}
				}
        		else if(name_value[0].equals(juliaY_)) {
					try {
						juliaY.set(Double.parseDouble(name_value[1]));
					}catch(NumberFormatException e) {

        			}
        		}
        		else if(name_value[0].equals(juliaZoom_)) {
        			try {
						juliaZoom.set(Double.parseDouble(name_value[1]));
        			}catch(NumberFormatException e) {
        				
        			}
        		}
        		else if(name_value[0].equals(colourMode_)) {
        			if(name_value[1].equals(ColourModes.BLACK_WHITE.name()))
        				cm.setValue(ColourModes.BLACK_WHITE);
        			else if(name_value[1].equals(ColourModes.COLOUR_FADE.name()))
						cm.setValue(ColourModes.COLOUR_FADE);
        		}
				else if(name_value[0].equals(renderMode_)) {
					if(name_value[1].equals(RenderMode.LOCAL.name()))
						rm.setValue(RenderMode.LOCAL);
					else if(name_value[1].equals(RenderMode.DISTRIBUTED.name()))
						rm.setValue(RenderMode.DISTRIBUTED);
				}
				else if(name_value[0].equals(tasksPerWorker_)) {
					try {
						tasksPerWorker.set(Integer.parseInt(name_value[1]));
					}catch(NumberFormatException e) {

					}
				}
				else if(name_value[0].equals(connection_)) {
					try {
						if (!name_value[1].matches("[a-zA-Z]:[0-9]+") && !name_value[1].isEmpty()) {
							connection.set("localhost:8010");
						}
						connection.set(name_value[1]);
					}catch(NumberFormatException e) {

					}
				}
        	}
        }

		///System.out.println("this is my test output " + juliaY);
		JuliaRenderOptions jo = new JuliaRenderOptions(juliaX, juliaY, new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), juliaZoom, power, iterations, juliaX, juliaY, cm, rm, connection, tasksPerWorker);
		MandelbrotRenderOptions mo = new MandelbrotRenderOptions(mandelbrotX, mandelbrotY, new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), mandelbrotZoom, power, iterations, cm,  rm, connection, tasksPerWorker);
		return new Pair<JuliaRenderOptions, MandelbrotRenderOptions>(jo, mo);
	}
}


/*
name: iterations:  type integer, default value: 128

name: power:  type floating point, default value: 2.0

name: mandelbrotX:  type floating point, default value: 0.0

name: mandelbrotY:  type floating point, default value: 0.0

name: mandelbrotZoom:  type floating point, default value: 0.0

name: juliaX:  type floating point, default value: 0.0

name: juliaY:  type floating point, default value: 0.0

name: juliaZoom:  type floating point, default value: 0.0

name: colourMode:  type enumerable (BLACK_WHITE, COLOUR_FADE), default value: BLACK_WHITE*/
