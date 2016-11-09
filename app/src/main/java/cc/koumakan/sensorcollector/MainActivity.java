package cc.koumakan.sensorcollector;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	SensorManager sm = null;
	SensorListener sl = null;
	DataUtil dataUtil = null;
	boolean isCollecting = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataUtil = new DataUtil(this);
		setContentView(R.layout.activity_main);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dataUtil.reset();
			}
		});
		findViewById(R.id.btn_avg).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AvgActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn_collect).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isCollecting) {
					sm.unregisterListener(sl);
					findViewById(R.id.btn_export).setClickable(true);
					findViewById(R.id.btn_collect).setBackgroundColor(getResources().getColor(R.color.blue));
					((Button) findViewById(R.id.btn_collect)).setText(R.string.b_start);
				} else {
					String table = ((EditText) findViewById(R.id.input)).getText().toString();
					if (table.isEmpty()) {
						Toast.makeText(MainActivity.this, "empty motion name", Toast.LENGTH_SHORT).show();
						return;
					}
					dataUtil.create(table);
					sl = new SensorListener();
					sm.registerListener(sl, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
					sm.registerListener(sl, sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
					sm.registerListener(sl, sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
					sm.registerListener(sl, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
					sm.registerListener(sl, sm.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
					
					findViewById(R.id.btn_export).setClickable(false);
					findViewById(R.id.btn_collect).setBackgroundColor(getResources().getColor(R.color.green));
					((Button) findViewById(R.id.btn_collect)).setText(R.string.b_stop);
				}
				isCollecting = !isCollecting;
			}
		});
		findViewById(R.id.btn_export).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dataUtil.export();
			}
		});
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	class SensorListener implements SensorEventListener {
		public void onSensorChanged(SensorEvent sensorEvent) {
			dataUtil.recode(new Data(System.currentTimeMillis(), sensorEvent.sensor.getName(),
				sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
		}
		
		
		public void onAccuracyChanged(Sensor sensor, int i) {
			Log.d("tag", "onAccuracyChanged: " + sensor + ", accuracy: " + i);
		}
	}
}
