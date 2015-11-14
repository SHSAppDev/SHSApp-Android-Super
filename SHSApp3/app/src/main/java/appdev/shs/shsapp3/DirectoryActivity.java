package appdev.shs.shsapp3;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DirectoryActivity extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DirectoryDataSource datasource = new DirectoryDataSource(this);
		datasource.open();
		ArrayAdapter<String> subdirectories = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, 
				Contact.categories
		);
		
		this.setListAdapter(subdirectories);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}



	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, SubdirectoryActivity.class);
		intent.putExtra("filter", Contact.categories[position]);
		startActivity(intent);
	}
}
