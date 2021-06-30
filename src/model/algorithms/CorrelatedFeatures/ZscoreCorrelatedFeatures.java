package model.algorithms.CorrelatedFeatures;

public class ZscoreCorrelatedFeatures extends CorrelatedFeatures{
     public final float zscore;

    public ZscoreCorrelatedFeatures(String feature1, float corrlation, float zscore) {
        super(feature1,corrlation);
        this.zscore=zscore;
    }
}
