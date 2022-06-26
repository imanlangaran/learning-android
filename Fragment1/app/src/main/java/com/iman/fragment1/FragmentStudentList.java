package com.iman.fragment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.List;

public class FragmentStudentList extends ListFragment {

    List<Student> list;
    private CallBacks activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (CallBacks) getActivity();
        list = Student.sampleStudentList();
        ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(getContext(), android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Student student = list.get(position);
        activity.onItemSelected(student);
    }

    public interface CallBacks{
        public void onItemSelected(Student student);
    }


}
