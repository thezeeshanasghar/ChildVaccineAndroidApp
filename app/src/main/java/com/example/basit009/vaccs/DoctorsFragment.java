package com.example.basit009.vaccs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.Toast;

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
 * {@link DoctorsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoctorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private VaccsClient vaccsClient;
    private RecyclerView rcvBooks;
    private DoctorsAdapter doctorsAdapter = null;
    private View view;
    private DoctorUser doctorUser;
    private ProgressBar progressBar;
    private boolean dataLoaded = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DoctorsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorsFragment newInstance(String param1, String param2) {
        DoctorsFragment fragment = new DoctorsFragment();
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
        inflater.inflate(R.menu.doctor_fragment_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(this);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


//        Toast.makeText(getActivity(),"doctor Fragment",Toast.LENGTH_LONG).show();
        dataLoaded=false;
        view = inflater.inflate(R.layout.fragment_doctors, container, false);
        vaccsClient = ServiceGenerator.createService(VaccsClient.class);
        rcvBooks = view.findViewById(R.id.rcv_list_doctors);
        progressBar = view.findViewById(R.id.progressbar_doctors);
        progressBar.setVisibility(View.VISIBLE);
        rcvBooks.setVisibility(View.INVISIBLE);
        rcvBooks.setLayoutManager(new LinearLayoutManager(getActivity()));

        getUserBooks();

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void toolBarChange() {

        Toast.makeText(getActivity(), "tool bar change", Toast.LENGTH_LONG).show();

    }

    private void initListeners() {
        doctorsAdapter.SetOnItemClickListener(new DoctorsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ArrayList<DoctorUser.Doctors> modelList) {
                Toast.makeText(getActivity(), "Position: " + position + "\nName: " +
                        " " + modelList.get(position).LastName, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), DoctorsEdit.class);
                intent.putExtra("doctorid", modelList.get(position).ID);
                startActivity(intent);

            }
        });
    }


    private void getUserBooks() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), "NO internet Available", Toast.LENGTH_LONG).show();
            return;
        }


        final Call<DoctorUser> call = vaccsClient.doctor();

        call.enqueue(new Callback<DoctorUser>() {
            @Override
            public void onResponse(Call<DoctorUser> call, Response<DoctorUser> response) {

                doctorUser = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                rcvBooks.setVisibility(View.VISIBLE);

                if (doctorUser != null) {
                    if (doctorUser.doctorsList != null && doctorUser.doctorsList.size() > 0) {
                        doctorsAdapter = new DoctorsAdapter(getActivity(), doctorUser.doctorsList, R.mipmap.ic_launcher_doctor);
                        rcvBooks.setAdapter(doctorsAdapter);
                        initListeners();
                        dataLoaded = true;

                    }

                } else {
                    Toast.makeText(getActivity(), "No response available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorUser> call, Throwable t) {
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

        if (dataLoaded) {
            String userInput = newText.toLowerCase();
            ArrayList<DoctorUser.Doctors> doctorsArrayList = new ArrayList<>();
            for (DoctorUser.Doctors list : doctorUser.doctorsList) {

                if (list.FirstName.toLowerCase().contains(userInput) || list.LastName.toLowerCase().contains(userInput)) {
                    doctorsArrayList.add(list);
                }

            }
            doctorsAdapter.upDateList(doctorsArrayList);
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
