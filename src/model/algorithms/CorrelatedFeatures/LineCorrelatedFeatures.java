package model.algorithms.CorrelatedFeatures;

import model.algorithms.Helpclass.Line;

public class LineCorrelatedFeatures extends CorrelatedFeatures {
    public final String feature2;
    public final Line lin_reg;
    public final float max_div;

    public LineCorrelatedFeatures(String feature1, String feature2, float corrlation, Line lin_reg,
                                  float max_div) {
		super(feature1,corrlation);
		this.feature2 =feature2;
		this.lin_reg =lin_reg;
		this.max_div =max_div;
    }
}