package model.algorithms.CorrelatedFeatures;
import model.algorithms.Helpclass.Point;
public class CircleCorrelatedFeatures  extends CorrelatedFeatures {

	public final String feature2;
	public final Point center;
	public final double radius;

    public CircleCorrelatedFeatures(String feature1, String feature2, float corrlation, Point center, double radius) {
		super(feature1,corrlation);
		this.feature2 = feature2;
	    this.center= center;
		this.radius= radius;
	}
}
