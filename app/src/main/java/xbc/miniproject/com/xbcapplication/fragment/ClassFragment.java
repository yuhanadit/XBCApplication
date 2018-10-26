package xbc.miniproject.com.xbcapplication.fragment;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xbc.miniproject.com.xbcapplication.R;
import xbc.miniproject.com.xbcapplication.adapter.ClassListAdapter;
import xbc.miniproject.com.xbcapplication.dummyModel.ClassModel;
import xbc.miniproject.com.xbcapplication.model.kelas.Batch;
import xbc.miniproject.com.xbcapplication.model.kelas.DataList;
import xbc.miniproject.com.xbcapplication.model.kelas.ModelClass;
import xbc.miniproject.com.xbcapplication.retrofit.APIUtilities;
import xbc.miniproject.com.xbcapplication.retrofit.RequestAPIServices;

public class ClassFragment extends Fragment {
    private EditText classEditTextSearch;
    private RecyclerView classRecyclerViewList;

    private List<DataList> listClass = new ArrayList<>();
    private ClassListAdapter classListAdapter;

    private RequestAPIServices apiServices;

    public ClassFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);

        getDataFromAPI();

        classRecyclerViewList = (RecyclerView) view.findViewById(R.id.classRecyclerViewList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        classRecyclerViewList.setLayoutManager(layoutManager);

        classEditTextSearch = (EditText) view.findViewById(R.id.classEditTextSearch);
        classRecyclerViewList.setVisibility(View.INVISIBLE);
        classEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (classEditTextSearch.getText().toString().trim().length() == 0){
                    classRecyclerViewList.setVisibility(View.INVISIBLE);
                }
                else {
                    classRecyclerViewList.setVisibility(View.VISIBLE);
                    filter(editable.toString());
                }
            }
        });
        tampilkanListClass();

        return view;
    }

    private void getDataFromAPI(){
        apiServices = APIUtilities.getAPIServices();
        apiServices.getListClass().enqueue(new Callback<ModelClass>() {
            @Override
            public void onResponse(Call<ModelClass> call, Response<ModelClass> response) {
                if(response.code() == 200){
                    List<DataList> tmp = response.body().getDataList();
                    for (int i = 0; i<tmp.size(); i++){
                        DataList data = tmp.get(i);
                        listClass.add(data);
                    }
                } else {
                    Toast.makeText(getContext(),"Gagal Mendapatkan List Class: " + response.code() + " msg: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelClass> call, Throwable t) {
                Toast.makeText(getContext(), "List Class onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void filter(String text) {
        ArrayList<DataList> filteredList = new ArrayList<>();

        for (DataList item : listClass) {
            if (item.getClass().toString().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        classListAdapter.filterList(filteredList);
    }

    private void tampilkanListClass() {
//        addDummyList();
        if (classListAdapter == null) {
            classListAdapter = new ClassListAdapter(getContext(), listClass);
            classRecyclerViewList.setAdapter(classListAdapter);
        }
    }

//    private void addDummyList() {
//        int index = 1;
//        for (int i = 0; i < 5; i++) {
//            ClassModel data = new ClassModel();
//            data.setBatch("Dummy Batch " + index);
//            data.setName("Dummy Name " + index);
//            listClass.add(data);
//            index++;
//        }
//    }
}
