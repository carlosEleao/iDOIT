package br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.fragment;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.Date;

import br.com.milond.idoit.idoittodolist.R;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data.DbTodoItem;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data.TodoItem;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.listener.OnTodoItemAddedListener;

/**
 * Created by Carlos Leao (carloseduardogu@gmail.com) on 06/05/2015.
 */
public class AddTodoItemFragment extends Fragment {

    private ImageButton btAdd, btAlert;
    private EditText edTodoItem;
    private static OnTodoItemAddedListener mOnTodoItemAddedListener;
    private TodoItem mCurrentTodoItem;

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

                TodoItem todo = getCurrentTodoItem();
                todo.setComplete(false);
                todo.setCreationDate(new Date());
                todo.setDescription(stringTodo);

                // Add iTodo item to DB
                TodoItem todoItem = dbTodoItem.createTodoItem(todo);

                // Remove the current one
                mCurrentTodoItem = null;

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

    private View.OnClickListener alertOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, monthOfYear, dayOfMonth);
                    Date date = cal.getTime();
                    TodoItem todoItem = getCurrentTodoItem();
                    todoItem.setTodoDate(date);
                }
            },year,month,day);
            datePicker.show();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_item_fragment, container, false);

        btAdd = (ImageButton) rootView.findViewById(R.id.btAdd);
        edTodoItem = (EditText) rootView.findViewById(R.id.edTodoItem);
        btAlert = (ImageButton) rootView.findViewById(R.id.btAlert);
        btAdd.setOnClickListener(addOnClickListener);
        btAlert.setOnClickListener(alertOnClickListener);


        // TODO REMOVE THIS WHEN IMPLEMENT THIS BUTTON
        btAlert.setVisibility(View.GONE);

        return rootView;
    }

    public void setmOnTodoItemAddedListener(OnTodoItemAddedListener onTodoItemAddedListener) {
        mOnTodoItemAddedListener = onTodoItemAddedListener;
    }

    private TodoItem getCurrentTodoItem() {
        if(mCurrentTodoItem == null){
            mCurrentTodoItem = new TodoItem();
        }
        return mCurrentTodoItem;
    }
}
