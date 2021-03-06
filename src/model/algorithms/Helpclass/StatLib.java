package model.algorithms.Helpclass;

import  java.lang.Math;
public class StatLib {

	public static float max(float[] arr){
		float max = -10000000;
		for(int i =0;i<arr.length;i++){
			if(arr[i]>max){
				max=arr[i];
			}
		}
		return max;
	}
	public static float min(float[] arr){
		float max = 100000000;
		for(int i =0;i<arr.length;i++){
			if(arr[i]<max){
				max=arr[i];
			}
		}
		return max;
	}
	// simple average
	public static float avg(float[] x){
		float sum=0;
		for (int i = 0; x.length > i; i++){
			sum+=x[i];
		}
		return sum/x.length;
	}
	public static float avg(Float[] x){
		float sum=0;
		for (int i = 0; x.length > i; i++){
			sum+= x[i];
		}
		return sum/x.length;
	}

	// returns the variance of X and Y
	public static float var(float[] x){
		float sum=0;
		for(int i=0;i<x.length;i++){
			sum+=(x[i]*x[i]);
		}
		sum/=x.length;
		float average=avg(x);
		average*=average;
		return sum-average;
	}
	public static float var(Float[] x){
		float sum=0;
		for(int i=0;i<x.length;i++){
			sum+=(x[i]*x[i]);
		}
		sum/=x.length;
		float average=avg(x);
		average*=average;
		return sum-average;
	}

	// returns the covariance of X and Y
	public static float cov(float[] x, float[] y){
		int size=x.length;
		float [] temp = new float[size];
		float xAvg=avg(x);
		float yAvg=avg(y);
		for(int i=0;i<size;i++) {
			temp[i]=(x[i]-xAvg)*(y[i]-yAvg);
		}
		return avg(temp);
	}


	// returns the Pearson correlation coefficient of X and Y
	public static float pearson(float[] x, float[] y){
		double sqrt=Math.sqrt(var(x))*Math.sqrt(var(y));
		float corr=cov(x,y)/(float)sqrt;
		return corr;
	}

	// performs a linear regression and returns the line equation
	public static Line linear_reg(Point[] points){
		float [] Xarry=new float[points.length];
		float [] Yarry=new float[points.length];
		for(int i=0;i<points.length;i++) {
			Xarry[i]=points[i].x;
			Yarry[i]=points[i].y;
		}

		float a=cov(Xarry,Yarry)/var(Xarry);
		float b=avg(Yarry)-a*avg(Xarry);
		Line ln=new Line(a,b);
		return ln;
	}

	// returns the deviation between point p and the line equation of the points
	public static float dev(Point p,Point[] points){
		Line ln=linear_reg(points);
		return dev(p,ln);
	}

	// returns the deviation between point p and the line
	public static float dev(Point p,Line l){
		float y=l.f(p.x);
		if((y-p.y)<0)
			return -(y-p.y);
		else
			return (y-p.y);
	}

	public static float checkZScore(float num, Float[] curColToCheck) {
		float curAvg = 0, curStiya = 0, curZscore = 0;
		curStiya = (float) Math.sqrt(var(curColToCheck));
		if (curStiya == 0) {
			return 0;
		}
		curAvg = avg(curColToCheck);
		curZscore = Math.abs(num - curAvg) / curStiya;
		return curZscore;
	}
	public static float checkZScore(float num, float[] curColToCheck) {
		float curAvg = 0, curStiya = 0, curZscore = 0;
		curStiya = (float) Math.sqrt(var(curColToCheck));
		if (curStiya == 0) {
			return 0;
		}
		curAvg = avg(curColToCheck);
		curZscore = Math.abs(num - curAvg) / curStiya;
		return curZscore;
	}
}