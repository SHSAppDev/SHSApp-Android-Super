package appdev.shs.shsapp3;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SubdirectoryActivity extends ListActivity {
	private DirectoryDataSource datasource;
	private List<Contact> list;
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, ContactActivity.class);
		intent.putExtra("toDisplay", list.get(position));
		String filter = getIntent().getStringExtra("filter");
		intent.putExtra("filter",filter);
		startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datasource = new DirectoryDataSource(this);
		datasource.open();
		ArrayAdapter<Contact> contacts = null;
		
		String filter = getIntent().getStringExtra("filter");
		
		if(filter.equals("All")) {
			list = datasource.getAllContacts();
		} else {
			list = datasource.getFilterRoleContacts(filter);
		}
		contacts = new ArrayAdapter<Contact>(this, 
				android.R.layout.simple_list_item_1, list);	
		this.setTitle(this.getString(R.string.title_activity_directory) + ": " + filter);
		this.setListAdapter(contacts);
		if (getActionBar()!=null)getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onResume() {
		datasource.open();
		ArrayAdapter<Contact> contacts = null;
		String filter = getIntent().getStringExtra("filter");
		
		if(filter.equals("All")) {
			list = datasource.getAllContacts();
		} else {
			list = datasource.getFilterRoleContacts(filter);
		}
		contacts = new ArrayAdapter<Contact>(this, 
				android.R.layout.simple_list_item_1, list);	
		this.setListAdapter(contacts);
		super.onResume();
	}


	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}
