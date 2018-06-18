package com.example.basit009.vaccs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.ChildrenUser;
import com.example.basit009.vaccs.ClientsJson.DoctorUser;
import com.example.basit009.vaccs.ClientsJson.NetworkUtils;
import com.example.basit009.vaccs.ClientsJson.ServiceGenerator;
import com.example.basit009.vaccs.ClientsJson.VaccsClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChildrenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChildrenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildrenFragment extends Fragment implements SearchView.OnQueryTextListener{


    private VaccsClient vaccsClient;
    private RecyclerView rcvChildren;
    private ChildrensAdapter childrensAdapter=null;
    private View view;
    private ArrayList<ChildrenUser.Childrens> duplicateList;
    private boolean isScrolling=false;
    private ChildrenUser childrenUser;
    private ProgressBar progressBar;
    private boolean dataLoaded=false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChildrenFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildrenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildrenFragment newInstance(String param1, String param2) {
        ChildrenFragment fragment = new ChildrenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.children_fragment_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search_children);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(this);


    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        Toast.makeText(getActivity(),"children Fragment",Toast.LENGTH_LONG).show();
        dataLoaded=false;
        view=inflater.inflate(R.layout.fragment_children, container, false);
        vaccsClient = ServiceGenerator.createService(VaccsClient.class);
        rcvChildren = view.findViewById(R.id.rcv_list_children);
        progressBar=view.findViewById(R.id.progressbar_children);
        rcvChildren.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvChildren.setVisibility(View.INVISIBLE);
        getUserChildren();
        return view;

    }



    private void initListeners() {
        childrensAdapter.SetOnItemClickListener(new ChildrensAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ArrayList<ChildrenUser.Childrens> modelList) {
                Toast.makeText(getActivity(),  "Name:- "+modelList.get(position).Name , Toast.LENGTH_LONG).show();

//                Intent intent=new Intent(getActivity(),ChildrenEdit.class);
//                startActivity(intent);
            }
        });

    }

    private void getUserChildren() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), "NO internet Available", Toast.LENGTH_LONG).show();
            return;
        }



        final Call<ChildrenUser> call = vaccsClient.child();

        call.enqueue(new Callback<ChildrenUser>() {
            @Override
            public void onResponse(Call<ChildrenUser> call, Response<ChildrenUser> response) {
                childrenUser = response.body();
                rcvChildren.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                if (childrenUser != null) {
                    if (childrenUser.childrensList != null ) {
                        childrensAdapter = new ChildrensAdapter(getActivity(), childrenUser.childrensList,R.mipmap.ic_launcher_doctor);
                        rcvChildren.setAdapter(childrensAdapter);
                        initListeners();
                        dataLoaded=true;
                    }
                } else {
                    Toast.makeText(getActivity(), "No response available", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ChildrenUser> call, Throwable t) {
                Toast.makeText(getActivity(), "Call Failed", Toast.LENGTH_SHORT).show();
            }
        });



    }









    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {

            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(dataLoaded) {
            String userInput = newText.toLowerCase();
            ArrayList<ChildrenUser.Childrens> childrensArrayList = new ArrayList<>();
            for (ChildrenUser.Childrens list : childrenUser.childrensList) {

                if (list.Name.toLowerCase().contains(userInput)) {
                    childrensArrayList.add(list);
                }

            }
            childrensAdapter.upDateList(childrensArrayList);

        }
        return true;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
