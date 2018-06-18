package com.example.basit009.vaccs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.DoctorUser;
import com.example.basit009.vaccs.ClientsJson.NetworkUtils;
import com.example.basit009.vaccs.ClientsJson.ServiceGenerator;
import com.example.basit009.vaccs.ClientsJson.VaccinesUser;
import com.example.basit009.vaccs.ClientsJson.VaccsClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VaccinesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VaccinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaccinesFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnMenuItemClickListener {

    private VaccsClient vaccsClient;
    private RecyclerView rcvVaccines;
    private VaccinesAdapter vaccinesAdapter = null;
    private View view;
    private FloatingActionButton fabVaccinesData;
    private VaccinesUser vaccinesUser;
    private MenuItem itemDelete, itemSearch;
    private int vaccindeDeleteId = 0;
    private String vaccineDeleteName;
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

    public VaccinesFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VaccinesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VaccinesFragment newInstance(String param1, String param2) {
        VaccinesFragment fragment = new VaccinesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.vaccines_fragment_menu, menu);
        itemSearch = menu.findItem(R.id.action_search_vaccines);
        itemDelete = menu.findItem(R.id.action_delete_vaccines);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemSearch);
        MenuItemCompat.setShowAsAction(itemSearch, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(itemSearch, searchView);
        searchView.setOnQueryTextListener(this);
        itemDelete.setOnMenuItemClickListener(this);


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//      Toast.makeText(getActivity(),"vaccines Fragment",Toast.LENGTH_LONG).show();

        dataLoaded=false;
        view = inflater.inflate(R.layout.fragment_vaccines, container, false);
        fabVaccinesData = view.findViewById(R.id.fab_vaccines_id);
        vaccsClient = ServiceGenerator.createService(VaccsClient.class);
        rcvVaccines = view.findViewById(R.id.rcv_list_vaccines);
        progressBar=view.findViewById(R.id.progressbar_vaccines);
        rcvVaccines.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        rcvVaccines.setLayoutManager(new LinearLayoutManager(getActivity()));


        fabVaccinesData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VaccineSendActivity.class);
                startActivity(intent);

            }
        });
        getUserBooks();

        return view;
    }


    public void onLongClickListener() {

        vaccinesAdapter.setItemLongClickListener(new VaccinesAdapter.ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos, ArrayList<VaccinesUser.Vaccines> modelList) {
                // Toast.makeText(getActivity(), "long click", Toast.LENGTH_LONG).show();

                itemSearch.setVisible(false);
                itemDelete.setVisible(true);

                vaccindeDeleteId = modelList.get(pos).ID;
                vaccineDeleteName = modelList.get(pos).Name;

            }
        });


    }


    private void initListeners() {

        vaccinesAdapter.SetOnItemClickListener(new VaccinesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ArrayList<VaccinesUser.Vaccines> modelList) {
                Toast.makeText(getActivity(), "ID: " + modelList.get(position).ID + "\nName: " +
                        " " + modelList.get(position).Name, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), Main3Activity.class);
                intent.putExtra("VaccId", modelList.get(position).ID);
                startActivity(intent);

            }
        });
    }


    private void getUserBooks() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), "NO internet Available", Toast.LENGTH_LONG).show();
            return;
        }


        final Call<VaccinesUser> call = vaccsClient.vaccines();

        call.enqueue(new Callback<VaccinesUser>() {
            @Override
            public void onResponse(Call<VaccinesUser> call, Response<VaccinesUser> response) {

                vaccinesUser = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                rcvVaccines.setVisibility(View.VISIBLE);
                if (vaccinesUser != null) {
                    if (vaccinesUser.vaccinesList != null && vaccinesUser.vaccinesList.size() > 0) {
                        vaccinesAdapter = new VaccinesAdapter(getActivity(), vaccinesUser.vaccinesList, R.mipmap.ic_launcher_doctor);
                        rcvVaccines.setAdapter(vaccinesAdapter);
                        initListeners();
                        onLongClickListener();
                        dataLoaded=true;
                    }
                } else {
                    Toast.makeText(getActivity(), "No response available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VaccinesUser> call, Throwable t) {
                Toast.makeText(getActivity(), "Call Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void deleteVaccine() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete " + vaccineDeleteName).setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        vaccinesAdapter.unCheckItems();
                        itemSearch.setVisible(true);
                        itemDelete.setVisible(false);

                        final Call<VaccinesUser> call = vaccsClient.delete(vaccindeDeleteId);

                        call.enqueue(new Callback<VaccinesUser>() {
                            @Override
                            public void onResponse(Call<VaccinesUser> call, Response<VaccinesUser> response) {


                                VaccinesUser vaccinesDelete = response.body();
                                // Toast.makeText(getActivity(), "Yes button "+ vaccindeDeleteId, Toast.LENGTH_SHORT).show();

                                if (vaccinesDelete != null) {
                                    Toast.makeText(getActivity(), vaccinesDelete.Message, Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getActivity(), "No response available", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<VaccinesUser> call, Throwable t) {
                                Toast.makeText(getActivity(), "call failed", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        vaccinesAdapter.unCheckItems();
                        itemSearch.setVisible(true);
                        itemDelete.setVisible(false);
                        Toast.makeText(getActivity(), "No button", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Delete");
        alertDialog.show();


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
        Toast.makeText(getActivity(), "vaccines detach", Toast.LENGTH_LONG).show();
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
            ArrayList<VaccinesUser.Vaccines> vaccinesArrayList = new ArrayList<>();
            for (VaccinesUser.Vaccines list : vaccinesUser.vaccinesList) {

                if (list.Name.toLowerCase().contains(userInput)) {
                    vaccinesArrayList.add(list);
                }

            }
            vaccinesAdapter.upDateList(vaccinesArrayList);
        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_delete_vaccines:
                deleteVaccine();
                return true;


        }


        return false;
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
