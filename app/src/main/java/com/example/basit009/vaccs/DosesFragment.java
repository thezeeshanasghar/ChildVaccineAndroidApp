package com.example.basit009.vaccs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.BrandVaccines;
import com.example.basit009.vaccs.ClientsJson.DossesVaccines;
import com.example.basit009.vaccs.ClientsJson.DossesVaccinesDelete;
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
 * {@link DosesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DosesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DosesFragment extends Fragment implements MenuItem.OnMenuItemClickListener {



    private int vaccsId=0;
    private VaccsClient vaccsClient;
    private RecyclerView rcvDosses;
    private DosesAdapter dosesAdapter;
    private View view;
    private FloatingActionButton fabDosesData;
    private int dosesDeleteId = 0;
    private String dosesDeleteName;
    private MenuItem dosesItemDelete;






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DosesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DosesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DosesFragment newInstance(String param1, String param2) {
        DosesFragment fragment = new DosesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.doses_fragment_menu, menu);
        dosesItemDelete = menu.findItem(R.id.action_delete_doses);
        dosesItemDelete.setOnMenuItemClickListener(this);

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

        view=inflater.inflate(R.layout.fragment_doses, container, false);
//        Toast.makeText(getActivity(),"doses Fragment",Toast.LENGTH_LONG).show();

        vaccsId=getActivity().getIntent().getExtras().getInt("VaccId");
        fabDosesData=view.findViewById(R.id.fab_doses_id);
        vaccsClient = ServiceGenerator.createService(VaccsClient.class);
        rcvDosses = view.findViewById(R.id.rcv_list_doses);
        rcvDosses.setLayoutManager(new LinearLayoutManager(getActivity()));

        fabDosesData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),DosesSendActivity.class);
                intent.putExtra("VaccId",vaccsId);
                startActivity(intent);
            }
        });
        getUserBooks();
        return view;

    }



    private void initListeners() {
        dosesAdapter.SetOnItemClickListener(new DosesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ArrayList<DossesVaccines.Dosses> modelList) {
                Toast.makeText(getActivity(), "ID: " + modelList.get(position).ID + "\nName: "+
                        " "+modelList.get(position).Name , Toast.LENGTH_LONG).show();

            }
        });
    }


    public void onLongClickListener() {

        dosesAdapter.setItemLongClickListener(new DosesAdapter.ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos, ArrayList<DossesVaccines.Dosses> modelList) {
                Toast.makeText(getActivity(), "long click", Toast.LENGTH_LONG).show();

                dosesItemDelete.setVisible(true);

                dosesDeleteId = modelList.get(pos).ID;
                dosesDeleteName = modelList.get(pos).Name;

            }
        });


    }


    public void deleteDoses() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete " + dosesDeleteName).setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dosesAdapter.unCheckItems();
                        dosesItemDelete.setVisible(false);

                        final Call<DossesVaccinesDelete> call = vaccsClient.deleteDoses(dosesDeleteId);

                        call.enqueue(new Callback<DossesVaccinesDelete>() {
                            @Override
                            public void onResponse(Call<DossesVaccinesDelete> call, Response<DossesVaccinesDelete> response) {

                                DossesVaccinesDelete dosesDelete = response.body();
                                // Toast.makeText(getActivity(), "Yes button "+ vaccindeDeleteId, Toast.LENGTH_SHORT).show();

                                if (dosesDelete != null) {

                                    if (dosesDelete.IsSuccess) {
                                        Toast.makeText(getActivity(), dosesDelete.ResponseData, Toast.LENGTH_LONG).show();
                                        getUserBooks();

                                    } else
                                        Toast.makeText(getActivity(), dosesDelete.Message, Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getActivity(), "No response available", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<DossesVaccinesDelete> call, Throwable t) {
                                Toast.makeText(getActivity(), "call failed", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dosesAdapter.unCheckItems();
                        dosesItemDelete.setVisible(false);
                        Toast.makeText(getActivity(), "No button", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Delete");
        alertDialog.show();


    }





    private void getUserBooks() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), "NO internet Available", Toast.LENGTH_LONG).show();
            return;
        }


        final Call<DossesVaccines> call = vaccsClient.dosses(vaccsId);

        call.enqueue(new Callback<DossesVaccines>() {
            @Override
            public void onResponse(Call<DossesVaccines> call, Response<DossesVaccines> response) {
                DossesVaccines dossesVaccines = response.body();

                if (dossesVaccines != null) {
                    if (dossesVaccines.dossesList != null ) {
                        dosesAdapter= new DosesAdapter(getActivity(), dossesVaccines.dossesList,R.mipmap.ic_launcher_doctor);
                        rcvDosses.setAdapter(dosesAdapter);
                        initListeners();
                        onLongClickListener();
                    }
                } else {
                    Toast.makeText(getActivity(), "No response available", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DossesVaccines> call, Throwable t) {
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
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_delete_doses:
                deleteDoses();
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
