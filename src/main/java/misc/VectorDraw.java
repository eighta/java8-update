package misc;

public class VectorDraw {

    public static void main(String[] args) {

        Punto p0 = new Punto(1,0);
        Punto p1 = new Punto(6,0);

        draw(p0, p1);

    }

    private static void draw(Punto p0, Punto p1) {
        // Vector director
        Punto v = p1.minus(p0);

        // pendiente
        double slope = slope(p0, p1);

        for (int i = 0; i < 10; i++) {
            Punto _p = parametric(p0, v, slope * i);
            System.out.println(i + ": " + _p);
        }

//        System.out.println( parametric(p0, v, 1.0/5.0) );

    }

    private static double slope(Punto p0, Punto p1) {
        return ( p1.getY() - p0.getY() ) /
                (double) ( p1.getX() - p0.getX() );
    }

    private static Punto parametric(Punto p0, Punto v, double t) {
        return new Punto (
                parametricAxis(p0.getX(), v.getX(), t),
                parametricAxis(p0.getY(), v.getY(), t));
    }

    private static int parametricAxis(int p0, int v, double t) {
        double r =  p0 + (v * t);
        return (int) Math.round(r);
    }

}

class Punto {
    private int x;
    private int y;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Punto minus(Punto p) {
        return new Punto(this.x - p.getX(), this.y - p.getY());
    }

    @Override
    public String toString() {
        return "Punto{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}