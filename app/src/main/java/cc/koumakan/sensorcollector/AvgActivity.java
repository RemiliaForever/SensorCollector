package cc.koumakan.sensorcollector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by remilia on 16-11-9.
 */
public class AvgActivity extends AppCompatActivity {
	DataUtil dataUtil = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avg);
		dataUtil = new DataUtil(this);
		dataUtil.queryTable();
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dataUtil.queryTable());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				getAvg(adapter.getItem(i).toString());
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				
			}
			
		});
	}
	
//	final enum VIEW_ID {
//		R.id.accerleration,R.id.accerleration_x,R.id.accerleration_y,R.id.accerleration_z,
//		R.id.rotation,R.id.rotation_x,R.id.rotation_y,R.id.rotation_z,
//		R.id.magnetometer,R.id.magnetometer_x,R.id.magnetometer_y,R.id.magnetometer_z,
//		R.id.orientation,R.id.orientation_x,R.id.orientation_y,R.id.orientation_z,
//		R.id.gravity,R.id.gravity_x,R.id.gravity_y,R.id.gravity_z
//	}
	
	
	private void getAvg(String table) {
		ArrayList<TextView> list = new ArrayList<>();
		list.add((TextView)findViewById(R.id.accerleration));
		list.add((TextView)findViewById(R.id.accerleration_x));
		list.add((TextView)findViewById(R.id.accerleration_y));
		list.add((TextView)findViewById(R.id.accerleration_z));
		list.add((TextView)findViewById(R.id.rotation));
		list.add((TextView)findViewById(R.id.rotation_x));
		list.add((TextView)findViewById(R.id.rotation_y));
		list.add((TextView)findViewById(R.id.rotation_z));
		list.add((TextView)findViewById(R.id.magnetometer));
		list.add((TextView)findViewById(R.id.magnetometer_x));
		list.add((TextView)findViewById(R.id.magnetometer_y));
		list.add((TextView)findViewById(R.id.magnetometer_z));
		list.add((TextView)findViewById(R.id.orientation));
		list.add((TextView)findViewById(R.id.orientation_x));
		list.add((TextView)findViewById(R.id.orientation_y));
		list.add((TextView)findViewById(R.id.orientation_z));
		list.add((TextView)findViewById(R.id.gravity));
		list.add((TextView)findViewById(R.id.gravity_x));
		list.add((TextView)findViewById(R.id.gravity_y));
		list.add((TextView)findViewById(R.id.gravity_z));
		int no = 0;
		
		for(Map.Entry<String, Data> entry:dataUtil.avg(table).entrySet()){
			Data data = entry.getValue();
			list.get(no*4).setText(data.getSensor() + " (" +data.getTime() + ")");
			list.get(no*4+1).setText(String.valueOf(data.getX()/data.getTime()));
			list.get(no*4+2).setText(String.valueOf(data.getY()/data.getTime()));
			list.get(no*4+3).setText(String.valueOf(data.getZ()/data.getTime()));
			no++;
		}
		
	}
}
