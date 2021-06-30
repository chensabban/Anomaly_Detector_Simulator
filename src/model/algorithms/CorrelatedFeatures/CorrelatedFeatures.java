package model.algorithms.CorrelatedFeatures;

 public class  CorrelatedFeatures {
	 public String feature1;

	 public String getFeature1() {
		 return feature1;
	 }

	 public float getCorrlation() {
		 return corrlation;
	 }

	 public float corrlation;

	 public CorrelatedFeatures(String f, float c) {
		 this.corrlation = c;
		 this.feature1 = f;
	 }
 }
//}
//public class CorrelatedFeatures{
//
//	public final String feature2;
//	public final Line lin_reg;
//	public final float threshold;
//	public final Point center;
//	public final double radius;
//	public final float zscore;
//
//
//	public class LineCorrelatedFeatures(String feature1, String feature2, float corrlation, Line lin_reg, float threshold)  extends Features  {
//		super(feature1,corrlation);
//		this.feature2 = feature2;
//		this.corrlation = corrlation;
//		this.lin_reg = lin_reg;
//		this.threshold = threshold;
//	}
//	public CircleCorrelatedFeatures(String feature1, String feature2, float corrlation, Point center, double radius) {
//		super(feature1,corrlation);
//		this.feature2 = feature2;
//		this.center= center;
//		this.radius= radius;
//	}
//
//	public class ZscoreCorrelatedFeatures extends
//	public ZscoreCorrelatedFeatures(String feature1, float corrlation, float zscore) {
//		super(feature1,corrlation);
//		this.zscore=zscore;
//	}
//
//
//}
