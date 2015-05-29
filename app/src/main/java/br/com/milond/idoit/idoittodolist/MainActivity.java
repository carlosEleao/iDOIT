package br.com.milond.idoit.idoittodolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.WindowManager;

import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data.TodoItem;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.fragment.AddTodoItemFragment;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.fragment.ListTodoItensFragment;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.listener.OnTodoItemAddedListener;


public class MainActivity extends AppCompatActivity {

    ListTodoItensFragment listFragment;
    AddTodoItemFragment addFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFragment = new ListTodoItensFragment();
        addFragment = new AddTodoItemFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, listFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_add_item, addFragment)
                .commit();

        // Listener
        addFragment.setmOnTodoItemAddedListener(new OnTodoItemAddedListener() {
            @Override
            public void onTodoItemAdded(TodoItem todoItem) {
                listFragment.refreshList();
            }
        });


        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
