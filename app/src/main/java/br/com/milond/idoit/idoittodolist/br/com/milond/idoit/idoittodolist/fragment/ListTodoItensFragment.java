package br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.OnClickWrapper;
import com.github.johnpersano.supertoasts.util.Style;

import java.util.ArrayList;
import java.util.List;

import br.com.milond.idoit.idoittodolist.R;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.adapter.TodoListAdapter;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data.DbTodoItem;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data.TodoItem;
import br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.listener.SwipeDismissListViewTouchListener;

public class ListTodoItensFragment extends Fragment {

    private ListView lvTodoItens;
    private TodoListAdapter mAdapter;
    private ArrayList<SuperActivityToast> superActivityToasts = new ArrayList<SuperActivityToast>();

    public ListTodoItensFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.todo_list, container, false);
        lvTodoItens = (ListView) rootView.findViewById(R.id.lvTodoList);

        setListAdapter();

        return rootView;
    }

    public void setListAdapter() {

        mAdapter = new TodoListAdapter(getActivity(), getTodoItensList());
        lvTodoItens.setAdapter(mAdapter);

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        lvTodoItens,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    DbTodoItem dbTodoItem = new DbTodoItem(getActivity());

                                    dbTodoItem.open();
                                    final TodoItem todoItem = (TodoItem) mAdapter.getItem(position);
                                    dbTodoItem.deleteTodoItem(todoItem);
                                    dbTodoItem.close();

                                    refreshList();

                                    /**
                                     * The OnClickWrapper is needed to reattach SuperToast.OnClickListeners on orientation changes.
                                     * It does this via a unique String tag defined in the first parameter so each OnClickWrapper's tag
                                     * should be unique.
                                     */
                                    OnClickWrapper onClickWrapper = new OnClickWrapper(SuperToast.class.getName(), new SuperToast.OnClickListener() {

                                        @Override
                                        public void onClick(View view, Parcelable token) {

                                            DbTodoItem dbTodoItem = new DbTodoItem(getActivity());
                                            dbTodoItem.open();
                                            dbTodoItem.createTodoItem(todoItem.getDescription());
                                            dbTodoItem.close();

                                            refreshList();

                                        }

                                    });

                                    SuperActivityToast superActivityToast = new SuperActivityToast(getActivity(), SuperToast.Type.BUTTON, Style.getStyle(Style.BLUE, SuperToast.Animations.FLYIN));
                                    superActivityToast.setDuration(SuperToast.Duration.MEDIUM);
                                    superActivityToast.setText(getText(R.string.cItemCompleted));
                                    superActivityToast.setButtonIcon(SuperToast.Icon.Dark.UNDO, getText(R.string.cUndo));
                                    superActivityToast.setOnClickWrapper(onClickWrapper);
                                    superActivityToast.setTouchToDismiss(true);
                                    superActivityToast.show();

                                    superActivityToasts.add(superActivityToast);
                                }
                            }
                        });
        lvTodoItens.setOnTouchListener(touchListener);
        lvTodoItens.setOnScrollListener(touchListener.makeScrollListener());

    }

    private List<TodoItem> getTodoItensList() {

        DbTodoItem dbTodoItem = new DbTodoItem(getActivity());

        dbTodoItem.open();
        List<TodoItem> listTodoItens = dbTodoItem.getAllTodoItens();
        dbTodoItem.close();

        return listTodoItens;
    }

    @Override
    public void onPause() {
        super.onPause();

        // Dismiss Toasts in case they are in the screen yet
        for (SuperActivityToast toast : superActivityToasts) {
            toast.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Dismiss Toasts in case they are in the screen yet
        for (SuperActivityToast toast : superActivityToasts) {
            toast.dismiss();
        }
    }

    public void refreshList() {
        mAdapter.setTodoItemList(getTodoItensList());
        mAdapter.notifyDataSetChanged();
    }


}