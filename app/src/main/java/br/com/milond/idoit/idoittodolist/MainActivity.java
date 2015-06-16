package br.com.milond.idoit.idoittodolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data.TodoItem;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.fragment.AddTodoItemFragment;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.fragment.ListTodoItensFragment;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.listener.OnTodoItemAddedListener;


public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener,
        OnMenuItemLongClickListener {

    ListTodoItensFragment listFragment;
    AddTodoItemFragment addFragment;
    private ContextMenuDialogFragment mMenuDialogFragment;

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

        initMenuFragment();

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
    }

    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject todo = new MenuObject(getString(R.string.cTodoItens));
        todo.setResource(R.drawable.icn_5);

        MenuObject complete = new MenuObject(getString(R.string.cItensCompleted));
        complete.setResource(R.drawable.icn_2);

        menuObjects.add(close);
        menuObjects.add(todo);
        menuObjects.add(complete);
        return menuObjects;
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                mMenuDialogFragment.show(getSupportFragmentManager(), "ContextMenuDialogFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemLongClick(View view, int i) {

    }


    @Override
    public void onMenuItemClick(View view, int i) {
        View v = findViewById(R.id.fragment_add_item);


        switch (i) {
            case 1:

                listFragment = new ListTodoItensFragment();
                addFragment = new AddTodoItemFragment();

                v.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, listFragment)
                        .commit();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_add_item, addFragment)
                        .commit();
                break;
            case 2:

                getSupportFragmentManager().beginTransaction().remove(addFragment).commit();
                v.setVisibility(View.GONE);
                listFragment.setCompleteListAdapter();
                break;
        }
    }
}
