package PTM1.AnomalyDetector;

public class AnomalyReport {
	public final String description;
	public final long timeStep;
	public final String feature2;

	public AnomalyReport(String description,  String feature2, long timeStep) {
		this.description = description;
		this.timeStep = timeStep;
		this.feature2 = feature2;
	}
}