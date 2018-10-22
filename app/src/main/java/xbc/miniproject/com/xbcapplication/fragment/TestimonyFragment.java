package xbc.miniproject.com.xbcapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import xbc.miniproject.com.xbcapplication.AddTestimonyActivity;
import xbc.miniproject.com.xbcapplication.R;
import xbc.miniproject.com.xbcapplication.adapter.TestimonyListAdapter;
import xbc.miniproject.com.xbcapplication.dummyModel.TestimonyModel;

public class TestimonyFragment extends Fragment {
    private EditText testimonyEditTextSearch;
    private Button testimonyButtonInsert;
    private RecyclerView testimonyRecyclerViewList;
    private List<TestimonyModel> testimonyModelList =  new ArrayList<>();
    private TestimonyListAdapter testimonyListAdapter;
    public TestimonyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_testimony,container,false);
        testimonyRecyclerViewList = (RecyclerView) view.findViewById(R.id.testimonyRecyclerViewList);
        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(getContext(),
                LinearLayout.VERTICAL,
                false);
        testimonyRecyclerViewList.setLayoutManager(layoutManager);
        testimonyEditTextSearch =  (EditText)view.findViewById(R.id.testimonyEditTextSearch);
        testimonyRecyclerViewList.setVisibility(view.INVISIBLE);
        testimonyEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(testimonyEditTextSearch.getText().toString().trim().length()==0){
                    testimonyRecyclerViewList.setVisibility(View.INVISIBLE);
                }else{
                    testimonyRecyclerViewList.setVisibility(view.VISIBLE);
                }
            }
        });
        testimonyButtonInsert = (Button) view.findViewById(R.id.testimonyButtonInsert);
        testimonyButtonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //panggil activity add Testimony
                Intent intent = new Intent(getContext(), AddTestimonyActivity.class);
                startActivity(intent);
            }
        });
        tampilkanListTestimony();
        return  view;
    }
    public  void filter(String text){
        ArrayList<TestimonyModel> filteredList = new ArrayList<>();
        for(TestimonyModel item : testimonyModelList){
            if (item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        testimonyListAdapter.filterList(filteredList);
    }
    public void  tampilkanListTestimony(){
        addDummyList();
        if(testimonyListAdapter==null){
            testimonyListAdapter =  new TestimonyListAdapter(getContext(), testimonyModelList);
            testimonyRecyclerViewList.setAdapter(testimonyListAdapter);
        }
    }
    public void addDummyList(){
        int index =1;
        for(int i =0; i<5; i++){
            TestimonyModel data = new TestimonyModel();
            data.setName("Dummy Testy Name"+index);
            testimonyModelList.add(data);
            index++;
        }
    }
}