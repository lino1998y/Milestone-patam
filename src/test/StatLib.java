package test;

public class StatLib {

    // simple average
    public static float avg(float[] x) {
        float count = sum(x);
        return (count / x.length);
    }

    // simple sum
    public static float sum(float[] x) {
        float count = 0;
        for (int i = 0; i < x.length; i++) {
            count += x[i];
        }
        return count;
    }

    // returns the variance of X and Y
    public static float var(float[] x) {
        float newArray[] = new float[x.length];
        float u = avg(x);

        for (int i = 0; i < x.length; i++) {
            newArray[i] = x[i] * x[i];
        }

        float avgNew = avg(newArray);

        return avgNew - (u * u);
    }

    // returns the covariance of X and Y
    public static float cov(float[] x, float[] y) {
        float newArrayX[] = new float[x.length];
        float newArrayY[] = new float[y.length];
        float newArrayXY[] = new float[x.length];
        float ex = avg(x);
        float ey = avg(y);

        for (int i = 0; i < y.length; i++) {
            newArrayY[i] = y[i] - ey;
        }

        for (int i = 0; i < x.length; i++) {
            newArrayX[i] = x[i] - ex;
        }
        for (int i = 0; i < x.length; i++) {
            newArrayXY[i] = newArrayX[i] * newArrayY[i];
        }

        float cov = avg(newArrayXY);
        return cov;

    }

    // returns the Pearson correlation coefficient of X and Y
    public static float pearson(float[] x, float[] y) {
        float covXY = cov(x, y);
        float varX = var(x);
        float squareX = (float) Math.sqrt(varX);
        float varY = var(y);
        float squareY = (float) Math.sqrt(varY);
        return covXY / (squareX * squareY);
    }

    // performs a linear regression and returns the line equation
    public static Line linear_reg(Point[] points) {
        float xArray[] = new float[points.length];
        float yArray[] = new float[points.length];

        for (int i = 0; i < points.length; i++) {
            xArray[i] = points[i].x;
            yArray[i] = points[i].y;
        }

        float covXY = cov(xArray, yArray);
        float varX = var(xArray);
        float a = covXY / varX;

        float avgX = avg(xArray);
        float avgY = avg(yArray);
        float b = avgY - (a * avgX);

        Line lineReturn = new Line(a, b);
        return lineReturn;
    }

    // returns the deviation between point p and the line equation of the points
    public static float dev(Point p, Point[] points) {
        return dev(p, linear_reg(points));
    }

    // returns the deviation between point p and the line
    public static float dev(Point p, Line l) {
        return Math.abs(p.y - l.f(p.x));
    }
}
