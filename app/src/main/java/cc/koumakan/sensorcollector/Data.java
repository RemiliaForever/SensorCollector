package cc.koumakan.sensorcollector;

import android.content.ContentValues;

/**
 * Created by remilia on 16-11-8.
 */
public class Data {
	private long time = 0;
	private String sensor = null;
	
	public long getTime() {
		return time;
	}
	
	public String getSensor() {
		return sensor;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	private float x = 0f;
	private float y = 0f;
	private float z = 0f;
	
	Data(long time, String sensor, float x, float y, float z) {
		this.time = time;
		this.sensor = sensor;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	ContentValues toContentValues() {
		ContentValues contentValues = new ContentValues();
		contentValues.put("time", time);
		contentValues.put("sensor", sensor);
		contentValues.put("x", x);
		contentValues.put("y", y);
		contentValues.put("z", z);
		return contentValues;
	}
	
	public String toString() {
		return "{\n" +
			"\ttime : " + time + "\n" +
			"\tsensor : " + sensor + "\n" +
			"\tx : " + x + "\n" +
			"\ty : " + y + "\n" +
			"\tz : " + z + "\n}";
	}
}
