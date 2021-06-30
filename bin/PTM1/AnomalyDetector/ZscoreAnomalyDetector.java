package PTM1.AnomalyDetector;

import PTM1.CorrelatedFeatures.LineCorrelatedFeatures;
import PTM1.Helpclass.Point;
import PTM1.Helpclass.StatLib;
import PTM1.Helpclass.TimeSeries;
import javafx.scene.chart.XYChart;

import java.util.*;

public class ZscoreAnomalyDetector implements TimeSeriesAnomalyDetector {

    private HashMap<String, Float> zscoremap;
    public HashMap<String, List<AnomalyReport>> anomalyReportList;

   public  ZscoreAnomalyDetector(){
      zscoremap = new HashMap<>();
      anomalyReportList = new HashMap<>();
    }


    @Override
    public void learnNormal(TimeSeries ts) {
        String[] features = ts.getFeaturesList();
        for (int i = 0; i < ts.getHashMap().size(); i++) {
            float[] curColToCheck = ts.getHashMap().get(features[i]);
            float max_zscore = MaxcheckZScore(curColToCheck);
            if(max_zscore!=0)
                this.zscoremap.put(features[i], max_zscore);
        }
    }

    @Override
    public HashMap<String,List<AnomalyReport>> detect(TimeSeries ts){
        float  curZscore = 0;
        Float[] curArray;
        int vectorsize = ts.getSizeOfVector();
        String[] features = zscoremap.keySet().toArray(new String[0]);
        for (int j = 0; j < features.length; j++) {
            float[] curColToCheck = ts.getHashMap().get(features[j]);
            ArrayList<Float> curArrayList = new ArrayList<>();
            curArrayList.add(curColToCheck[0]);
            curArrayList.add(curColToCheck[1]);
            for (int i = 2; i < vectorsize; i++) {
                curArray = curArrayList.toArray(new Float[0]);
                curZscore = StatLib.checkZScore(curColToCheck[i], curArray);
                curArrayList.add(curColToCheck[i]);
                if (curZscore > this.zscoremap.get(features[j])) {
                    if(!anomalyReportList.containsKey(features[j])){
                        anomalyReportList.put(features[j],new LinkedList<>());
                    }
                    anomalyReportList.get(features[j]).add(new AnomalyReport("division in col " + features[j],features[j], (long) i + 1));

                }
            }
        }
        return anomalyReportList;




//        XYChart.Series series= new XYChart.Series();
//        XYChart.Series series2= new XYChart.Series();
//        List<XYChart.Series> points =new LinkedList<>();
//        series.setName("Good-Detected Points");
//        series2.setName("Bad-Detected Points");
//        ArrayList<Float> curArrayList = new ArrayList<>();
//        float[] feature_vals;
//        float curZscore,correlated_zscore;
//        Float[] curArray;
//        feature_vals = ts.getHashMap().get(feature);
//        correlated_zscore = zscoremap.get(feature);
//        curArrayList.add(feature_vals[0]);
//        curArrayList.add(feature_vals[1]);
//        series.getData().add(new XYChart.Data(0,0,0));
//        series.getData().add(new XYChart.Data(1,0,1));
//        for (int i = 2; i < feature_vals.length; i++) {
//            curArray = curArrayList.toArray(new Float[0]);
//            curZscore = StatLib.checkZScore(feature_vals[i], curArray);
//            curArrayList.add(feature_vals[i]);
//            if (curZscore > correlated_zscore) {
//                series2.getData().add(new XYChart.Data( i, curZscore,i));
//            } else {
//                series.getData().add(new XYChart.Data( i, curZscore,i));
//            }
//
//        }
//        points.add(series);
//        points.add(series2);
//        return points;
    }

    @Override
    public List<XYChart.Series> paint(TimeSeries ts,String feature){
        XYChart.Series learning_points = new XYChart.Series<>();
        XYChart.Series algo_points = new XYChart.Series<>();
        XYChart.Series setting_algo = new XYChart.Series();
        List<XYChart.Series> points =new LinkedList<>();
        float selected_f_size,zscore;
        if(zscoremap.containsKey(feature)) {
            zscore = zscoremap.get(feature);
            selected_f_size = ts.getSizeOfVector();
            algo_points.getData().add(new XYChart.Data(0, zscore));
            algo_points.getData().add(new XYChart.Data(selected_f_size, zscore));
            setting_algo.getData().add(new XYChart.Data(0, selected_f_size));
            setting_algo.getData().add(new XYChart.Data(0, zscore));
            setting_algo.getData().add(new XYChart.Data(0, selected_f_size));
            algo_points.setName("Zscore-Algo");
            setting_algo.setName("setting-Algo");
            learning_points.setName("learning-points");
            points.add(learning_points);
            points.add(algo_points);
            points.add(setting_algo);
            return points;
        }
        return null;
    }

    @Override
    public float[] detect_P(TimeSeries ts, String feature, int time_step) {
            if(zscoremap.containsKey(feature)){
                float zscore = zscoremap.get(feature);
                float[] vals = ts.getHashMap().get(feature);
                vals = Arrays.copyOfRange(vals,0,time_step);
                float curZ=StatLib.checkZScore(ts.valueAtIndex(time_step,feature),vals);
                if (curZ > zscore)
                    return new float[]{time_step,curZ, (float) 1};
                else
                    return new float[]{time_step,curZ, (float) 2};
            }
            return null;
    }

    public float MaxcheckZScore(float[] curColToCheck) {
        ArrayList<Float> curArrayList = new ArrayList<>();
        float curZscore = 0, maxZscore = 0;
        Float[] curArray;
        curArrayList.add(curColToCheck[0]);
        for (int j = 1; j < curColToCheck.length; j++) {
            curArray = curArrayList.toArray(new Float[0]);
            curZscore = StatLib.checkZScore(curColToCheck[j], curArray);
            curArrayList.add(curColToCheck[j]);
            if (curZscore > maxZscore) {
                maxZscore = curZscore;
            }
        }
        return maxZscore;
    }

//    public List<XYChart.Series> detect_P(TimeSeries ts,String feature,int time_step){
//        List<XYChart.Series> detect_points = new LinkedList<>();
//        XYChart.Series series= new XYChart.Series();
//        XYChart.Series series1= new XYChart.Series();
//        float curZscore,correlated_zscore,curval;
//        Float[] curArray;
//        float[]feature_vals;
//
//        curval=ts.valueAtIndex(time_step,feature);
//        feature_vals = ts.getHashMap().get(feature);
//        ArrayList<Float> curArrayList = new ArrayList<>();
//        correlated_zscore = zscoremap.get(feature);
//
//        for (int i = 0; i < time_step-1; i++) {
//            curArrayList.add(feature_vals[i]);
//        }
//        curArray = curArrayList.toArray(new Float[0]);
//
//        curZscore = checkZScore(curval, curArray);
//
//            if (curZscore > correlated_zscore) {
//                series1.getData().add(new XYChart.Data(time_step, curval));
//            } else {
//                series.getData().add(new XYChart.Data(time_step, curval));
//            }
//
//        detect_points.add(series);
//        detect_points.add(series1);
//        return detect_points;
//    }
}