package at.tugraz.oop2.shared;


import at.tugraz.oop2.shared.exception.InvalidDepthException;
import javafx.beans.property.IntegerProperty;

public class ComplexNumber {
    private double realNum_;
    private double imaginaryNum_;
    public long max_iterations_;

    private double pow;

    public double getPow(){return pow;}

    public ComplexNumber(){
        realNum_ = 0;
        imaginaryNum_ = 0;
        max_iterations_ = 128;
        pow = 2;
    }

    public void setPow(double power){pow = power;}

    public ComplexNumber(double power){
        realNum_ = 0;
        imaginaryNum_ = 0;
        max_iterations_ = 128;
        pow = power;
    }

    public double getReal() {

        return realNum_;
    }

    public void setReal(double realNum) {

        this.realNum_ = realNum;
    }

    public double getimaginary() {

        return imaginaryNum_;
    }

    public void setImaginary(double imaginaryNum) {
        this.imaginaryNum_ = imaginaryNum;
    }

    public ComplexNumber(double realNum, double imaginaryNum, double power){
        realNum_ = realNum;
        imaginaryNum_ = imaginaryNum;
        pow = power;
    }

    public synchronized void add(ComplexNumber complex){
        this.realNum_ = this.realNum_ + complex.getReal();
        this.imaginaryNum_ = this.imaginaryNum_+ complex.getimaginary();

    }

    public synchronized void multi(ComplexNumber complex, double power){
        double tmp_real = realNum_;
        double tmp_imag = imaginaryNum_;
        double tmpr2 = realNum_;
        double tmpi2 = imaginaryNum_;
//        if (power == 2)
//        {
//            realNum_ = tmp_real * tmp_real - tmp_imag * tmp_imag;
//            imaginaryNum_ = 2 * tmp_real * tmp_imag;
//        }
//        else
        {
            for (int i = 1; i < power; i++)
            {
                tmp_real = realNum_;
                tmp_imag = imaginaryNum_;
                realNum_ = tmp_real * tmpr2 - tmp_imag * tmpi2;
                imaginaryNum_ = tmp_real * tmpi2 + tmp_imag * tmpr2;
            }
        }
    }

    public synchronized boolean magnitudeCheck(){
        double mag = Math.sqrt(realNum_ * realNum_ + imaginaryNum_ * imaginaryNum_);
        return mag < 2.0;
    }

    public long calcMandelIterations(ComplexNumber pixel) {
        long iterations = 0;
        // c = p , z_0 = 0, z = z² + c
        while (magnitudeCheck() && iterations < max_iterations_) {

            multi(pixel, getPow()); // z²
            add(pixel); // + c
            iterations++;
            //System.out.println("Complex: " + real_+ " + i" + imag_);
            //System.out.println("Iterations: " + iterations);
        }

        return iterations;
    }

    public short[] drawMandel(double x, double y, IntegerProperty max_iterations, String colour_mode) {
        ComplexNumber pixel = new ComplexNumber(x, y, pow);

        max_iterations_ = max_iterations.getValue();

        short[] red = {255, 0, 0};
        short[] blue = {0, 0, 255};
        short[] colour = {0, 0, 0};

        realNum_ = x;
        imaginaryNum_ = y;
        long iterations = calcMandelIterations(pixel);
        if (iterations < max_iterations_) {
            double colour_strength = ((double) iterations / (double) max_iterations_);
            //           System.out.println("this is my test output 111 " + colour_strength);
            if (colour_mode.equals("BLACK_WHITE")) {
                for (int i = 0; i < 3; i++) {
                    colour[i] = (short) (255.0 * colour_strength);
                }
            } else {
                //TODO: this in the comments is needed for colours
                colour[0] = (short) (red[0] * colour_strength);
                colour[1] = 0;
                colour[2] = (short) (blue[2] * (1 - colour_strength));
                //System.out.print("real= " + realNum_ + " imag" + imaginaryNum_);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                colour[i] = 0;
            }
        }
        return colour;
    }

    public short[] drawJulia(double x, double y, IntegerProperty max_iterations, String colour_mode, double mandel_x, double mandel_y) throws InvalidDepthException {
        //TODO: do sui der klumpat pixel eini

        max_iterations_ = max_iterations.getValue();

        short[] red = {255, 0, 0};
        short[] blue = {0, 0, 255};
        short[] colour = {0, 0, 0};

        realNum_ = x;
        imaginaryNum_ = y;
        ComplexNumber pixel = new ComplexNumber(mandel_x, mandel_y, getPow());
        long iterations = calcMandelIterations(pixel);
        if (iterations < max_iterations_) {
            double colour_strength = ((double) iterations / (double) max_iterations_);
            if (colour_mode.equals("BLACK_WHITE")) {
                for (int i = 0; i < 3; i++) {
                    colour[i] = (short) (255.0 * colour_strength);
                }
            } else {
                //TODO: this in the comments is needed for colours
                colour[0] = (short) (red[0] * colour_strength);
                colour[1] = 0;
                colour[2] = (short) (blue[2] * (1 - colour_strength));
                //System.out.print("real= " + real_ + " imag" + imag_);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                colour[i] = 0;
            }
        }
        return colour;
    }

}
