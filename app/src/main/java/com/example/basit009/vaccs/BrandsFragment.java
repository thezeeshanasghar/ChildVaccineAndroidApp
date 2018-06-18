package com.example.basit009.vaccs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.BrandVaccines;
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
 * {@link BrandsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BrandsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrandsFragment extends Fragment implements MenuItem.OnMenuItemClickListener {


    private int vaccsId = 0;
    private VaccsClient vaccsClient;
    private RecyclerView rcvBrands;
    private BrandsAdapter brandsAdapter;
    private View view;
    private FloatingActionButton fabBrandsData;
    private int brandsDeleteId = 0;
    private String brandsDeleteName;
    private MenuItem brandsItemDelete;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BrandsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrandsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrandsFragment newInstance(String param1, String param2) {
        BrandsFragment fragment = new BrandsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.brands_fragment_menu, menu);
        brandsItemDelete = menu.findItem(R.id.action_delete_brands);
        brandsItemDelete.setOnMenuItemClickListener(this);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_brands, container, false);
       // Toast.makeText(getActivity(), "brands Fragment", Toast.LENGTH_LONG).show();

        vaccsId = getActivity().getIntent().getExtras().getInt("VaccId");
        vaccsClient = ServiceGenerator.createService(VaccsClient.class);
        fabBrandsData = view.findViewById(R.id.fab_brands_id);
        rcvBrands = view.findViewById(R.id.rcv_list_brands);
        rcvBrands.setLayoutManager(new LinearLayoutManager(getActivity()));

        fabBrandsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), BrandsSendActivity.class);
                intent.putExtra("VaccId", vaccsId);
                startActivity(intent);

            }
        });

        getUserBooks();


        return view;
    }


    public void onLongClickListener() {

        brandsAdapter.setItemLongClickListener(new BrandsAdapter.ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos, ArrayList<BrandVaccines.Brands> modelList) {
                Toast.makeText(getActivity(), "long click", Toast.LENGTH_LONG).show();

                brandsItemDelete.setVisible(true);

                brandsDeleteId = modelList.get(pos).ID;
                brandsDeleteName = modelList.get(pos).Name;

            }
        });


    }



    private void initListeners() {
        brandsAdapter.SetOnItemClickListener(new BrandsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ArrayList<BrandVaccines.Brands> modelList) {
                Toast.makeText(getActivity(), "ID: " + modelList.get(position).ID + "\nName: " +
                        " " + modelList.get(position).Name, Toast.LENGTH_LONG).show();


            }
        });
    }


    public void deleteBrands() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete " + brandsDeleteName).setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        brandsAdapter.unCheckItems();
                        brandsItemDelete.setVisible(false);

                        final Call<BrandVaccines> call = vaccsClient.deleteBrands(brandsDeleteId);

                        call.enqueue(new Callback<BrandVaccines>() {
                            @Override
                            public void onResponse(Call<BrandVaccines> call, Response<BrandVaccines> response) {

                                BrandVaccines brandDelete = response.body();
                                // Toast.makeText(getActivity(), "Yes button "+ vaccindeDeleteId, Toast.LENGTH_SHORT).show();

                                if (brandDelete != null) {
                                    Toast.makeText(getActivity(), brandDelete.IsSuccess, Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getActivity(), "No response available", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<BrandVaccines> call, Throwable t) {
                                Toast.makeText(getActivity(), "call failed", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        brandsAdapter.unCheckItems();
                        brandsItemDelete.setVisible(false);
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


        final Call<BrandVaccines> call = vaccsClient.brands(vaccsId);

        call.enqueue(new Callback<BrandVaccines>() {
            @Override
            public void onResponse(Call<BrandVaccines> call, Response<BrandVaccines> response) {
                BrandVaccines brandVaccines = response.body();

                if (brandVaccines != null) {
                    if (brandVaccines.brandsList != null) {
                        brandsAdapter = new BrandsAdapter(getActivity(), brandVaccines.brandsList, R.mipmap.ic_launcher_doctor);
                        rcvBrands.setAdapter(brandsAdapter);
                        onLongClickListener();
                        initListeners();
                    }
                } else {
                    Toast.makeText(getActivity(), "No response available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BrandVaccines> call, Throwable t) {
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
            case R.id.action_delete_brands:
                deleteBrands();
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
