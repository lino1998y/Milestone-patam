package test;

import java.util.ArrayList;
import java.util.List;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {

    private ArrayList<CorrelatedFeatures> features = new ArrayList<>();
    Point[] pointsArray;
    double constNumber = 0.9;

    @Override
    public void learnNormal(TimeSeries ts) {
        TimeSeries.Columns[] col = ts.getCols();
        TimeSeries.Columns floatMax1, floatMax2;


        for (int i = 0; i < col.length; i++) {
            float max = 0;
            floatMax1 = null;
            floatMax2 = null;
            for (int j = i + 1; j < col.length; j++) {

                float[] floatArrayA = this.convertFloatListToFloatArray(col[i].getFloats());
                float[] floatArrayB = this.convertFloatListToFloatArray(col[j].getFloats());

                float pearsonCorrelation = Math.abs(StatLib.pearson(floatArrayA, floatArrayB));

                if (pearsonCorrelation > max && pearsonCorrelation > constNumber) {
                    max = pearsonCorrelation;
                    floatMax1 = col[i];
                    floatMax2 = col[j];
                }
            }

            if (floatMax1 != null && floatMax2 != null) {

                pointsArray = new Point[floatMax1.getFloats().size()];
                for (int j = 0; j < floatMax1.getFloats().size(); j++) {
                    Point p = new Point(floatMax2.getFloats().get(j), floatMax1.getFloats().get(j));
                    this.pointsArray[j] = p;
                }

                Line l = StatLib.linear_reg(pointsArray);
                float maxDev = 0;
                for (int j = 0; j < pointsArray.length; j++) {
                    float dev = Math.abs(StatLib.dev(pointsArray[j], l));
                    if (dev > maxDev) {
                        maxDev = dev;
                    }
                }
                features.add(new CorrelatedFeatures(floatMax1.getName(), floatMax2.getName(), max, l, maxDev * 1.1f));
            }
        }
    }

    public float[] convertFloatListToFloatArray(ArrayList<Float> floatArrayList) {
        final float[] arr = new float[floatArrayList.size()];
        int index = 0;
        for (final Float value : floatArrayList) {
            arr[index++] = value;
        }
        return arr;
    }


    private Point[] featuresPoints(TimeSeries ts, CorrelatedFeatures f) {
        int index1 = 0, index2 = 0;
        String fet1, fet2;
        fet1 = f.feature1;
        fet2 = f.feature2;
        for (int i = 0; i < ts.getCols().length; i++) {
            int s = fet1.compareTo(ts.getCols()[i].getName());
            int z = fet2.compareTo(ts.getCols()[i].getName());
            if (s == 0) {
                index1 = i;
            }
            if (z == 0) {
                index2 = i;
            }

        }
        int pointsSize = ts.getCols()[0].getFloats().size();
        Point[] newPointsArray = new Point[pointsSize];
        for (int j = 0; j < pointsSize; j++) {
            Point p = new Point(ts.getCols()[index2].getFloats().get(j), ts.getCols()[index1].getFloats().get(j));
            newPointsArray[j] = p;
        }

        return newPointsArray;
    }

    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        ArrayList<AnomalyReport> reports = new ArrayList<>();
        //for each 2 correlated features
        for (CorrelatedFeatures feature : features) {
            //build the point array
            Point[] points = featuresPoints(ts, feature);
            //for every point
            for (int i = 0; i < points.length; ++i) {
                float dev = StatLib.dev(points[i], feature.lin_reg);

                //check if that point's distance exceeds the threshold
                if (dev > feature.threshold) {
                    //if so, add it to the report list
                    reports.add(new AnomalyReport(feature.feature1 + "-" + feature.feature2, i + 1));
                }
            }
        }
        return reports;
    }

    public List<CorrelatedFeatures> getNormalModel() {
        return features;
    }
}
