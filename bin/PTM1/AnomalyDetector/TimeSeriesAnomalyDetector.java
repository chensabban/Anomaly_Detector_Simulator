package PTM1.AnomalyDetector;

import PTM1.Helpclass.Point;
import PTM1.Helpclass.TimeSeries;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.List;

public interface TimeSeriesAnomalyDetector {
	void learnNormal(TimeSeries ts);
	HashMap<String,List<AnomalyReport>> detect(TimeSeries ts);
	List<XYChart.Series> paint(TimeSeries ts,String feature);
	float[] detect_P(TimeSeries ts,String feature,int time_step);
}
