package br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import br.com.milond.idoit.idoittodolist.R;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data.DbTodoItem;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data.TodoItem;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.listener.OnTodoItemAddedListener;

/**
 * Created by Carlos Leao (carloseduardogu@gmail.com) on 06/05/2015.
 */
public class AddTodoItemFragment extends Fragment {

    private ImageButton btAdd;
    private EditText edTodoItem;
    private static OnTodoItemAddedListener mOnTodoItemAddedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Create DB object
            DbTodoItem dbTodoItem = new DbTodoItem(getActivity());
            dbTodoItem.open();

            String stringTodo = edTodoItem.getText().toString().trim();

            if(!stringTodo.isEmpty()) {

                // Add iTodo item to DB
                TodoItem todoItem = dbTodoItem.createTodoItem(stringTodo);

                // Verify if there is a listener setted
                if (mOnTodoItemAddedListener != null) {
                    mOnTodoItemAddedListener.onTodoItemAdded(todoItem);
                }
            }

            // Close DB connection
            dbTodoItem.close();

            // Clear edittext
            edTodoItem.setText("");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_item_fragment, container, false);

        btAdd = (ImageButton) rootView.findViewById(R.id.btAdd);
        edTodoItem = (EditText) rootView.findViewById(R.id.edTodoItem);
        btAdd.setOnClickListener(addOnClickListener);

        return rootView;
    }

    public void setmOnTodoItemAddedListener(OnTodoItemAddedListener onTodoItemAddedListener) {
        mOnTodoItemAddedListener = onTodoItemAddedListener;
    }
}
