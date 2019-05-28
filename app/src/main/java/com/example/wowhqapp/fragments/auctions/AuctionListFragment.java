package com.example.wowhqapp.fragments.auctions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wowhqapp.R;
import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.databases.entity.Lot;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AuctionListFragment extends Fragment implements MainContract.AuctionsListFragView {

    public static final String KEY_TYPE = "TYPE";
    private RecyclerView.OnScrollListener mOnScrollListener;


    //Можно генерить код на N колонок

    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private AuctionListRecyclerViewAdapter mAuctionListRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AuctionListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //http://qaru.site/questions/25851/how-to-implement-endless-list-with-recyclerview - TOP
                //https://stackoverflow.com/questions/31000964/how-to-implement-setonscrolllistener-in-recyclerview
                super.onScrolled(recyclerView, dx, dy);
            }
        };
    }


    // Тот же setContentView() - только во фрагментах
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auctionlist_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.addOnScrollListener(mOnScrollListener);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Создаем слушателя нажатия на лот, реализованного в Activity
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void initAdapter(List<Lot> lotList) {
        //Потом тут будет установка не DummyContent, а simpleLotList
        mAuctionListRecyclerViewAdapter = new AuctionListRecyclerViewAdapter(lotList, mListener);
        mRecyclerView.setAdapter(mAuctionListRecyclerViewAdapter);

    }

    @Override
    public void notifyAuctionsChange() {
        mAuctionListRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Lot item);
    }


}
