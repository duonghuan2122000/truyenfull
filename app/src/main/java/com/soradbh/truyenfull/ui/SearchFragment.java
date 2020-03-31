package com.soradbh.truyenfull.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.adapter.ListStoryAdapter;
import com.soradbh.truyenfull.model.ListStoryModel;
import com.soradbh.truyenfull.viewmodel.SearchStoryViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private SearchStoryViewModel viewModel;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListStoryAdapter adapter = new ListStoryAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity()).get(SearchStoryViewModel.class);
        final EditText editText = view.findViewById(R.id.edittext_search);
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    viewModel.getListStory("https://truyenfull.vn/tim-kiem/?tukhoa=" + editText.getText().toString())
                            .observe(getViewLifecycleOwner(), new Observer<PagedList<ListStoryModel>>() {
                                @Override
                                public void onChanged(PagedList<ListStoryModel> data) {
                                    Log.d("Search", String.valueOf(data.size()));
                                    adapter.submitList(data);
                                }
                            });
                    View view = getActivity().getCurrentFocus();
                    if(view != null){
                        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });

        adapter.setOnItemClickListener(new ListStoryAdapter.OnItemClickListener() {
            @Override
            public void setClick(String urlStory) {
                Bundle args = new Bundle();
                args.putString(InfoStoryFragment.URL_STORY, urlStory);
                Navigation.findNavController(requireView()).navigate(R.id.action_search_dest_to_infostory_dest, args);
            }
        });
    }
}
