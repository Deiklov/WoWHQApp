package com.example.wowhqapp.fragments.talents;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wowhqapp.R;
import com.example.wowhqapp.contracts.TalentsContract;
import com.example.wowhqapp.databases.entity.WowSpec;

import java.util.ArrayList;
import java.util.List;

public class TalentsWowSpecsFragment extends Fragment {
    private TalentsContract.TalentsPresenter mTalentsPresenter;
    private TalentsWowSpecsFragment.TalentsWowSpecsAdapter mWowSpecsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_talents_wow_classes_or_specs_list, container, false);
        mTalentsPresenter = ((TalentsContract.TalentsView) getActivity()).getTalentsPresenter(); // TODO

        RecyclerView recyclerView = fragmentView.findViewById(R.id.talents_recycler_view_wow_classes_or_specs_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));

        mWowSpecsAdapter = new TalentsWowSpecsAdapter();

        recyclerView.setAdapter(mWowSpecsAdapter);

        TalentsWowSpecsAsyncLoader talentsWowSpecsAsyncLoader = new TalentsWowSpecsAsyncLoader();
        talentsWowSpecsAsyncLoader.execute(mWowSpecsAdapter);

        return fragmentView;
    }


    class TalentsWowSpecsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mNameView;
        private TextView mDescriptionView;

        private TextView mIdView;
        private TextView mOrderView;


        TalentsWowSpecsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.fragment_talents_wow_spec_elem_img);
            mNameView = itemView.findViewById(R.id.fragment_talents_wow_spec_elem_name);
            mDescriptionView = itemView.findViewById(R.id.fragment_talents_wow_spec_elem_description);

            mIdView = itemView.findViewById(R.id.fragment_talents_wow_spec_elem_id);
            mOrderView = itemView.findViewById(R.id.fragment_talents_wow_spec_elem_order);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TextView idView = v.findViewById(R.id.fragment_talents_wow_spec_elem_id);
                    // TextView orderViewView = v.findViewById(R.id.fragment_talents_wow_spec_elem_order);

                    int id = Integer.parseInt(mIdView.getText().toString());
                    int order = Integer.parseInt(mOrderView.getText().toString());

                    mTalentsPresenter.getSettingRepository().setTalentsWowSpecId(id);
                    mTalentsPresenter.getSettingRepository().setTalentsWowSpecOrder(order);
                    mTalentsPresenter.loadStage(false);
                }
            });
        }
    }

    class TalentsWowSpecsAdapter extends RecyclerView.Adapter<TalentsWowSpecsViewHolder> {
        private List<WowSpec> mWowSpecs;
        private Bitmap mBitmap;

        TalentsWowSpecsAdapter() {
            mBitmap = Bitmap.createBitmap(mTalentsPresenter.fillColorsTemp(), 200, 200, Bitmap.Config.ARGB_8888);
            mWowSpecs = new ArrayList<>();
        }

        @NonNull
        @Override
        public TalentsWowSpecsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.fragment_talents_wow_spec_elem, viewGroup, false);

            Log.d("TAG", "onCreateViewHolder for element type " + i);

            return new TalentsWowSpecsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TalentsWowSpecsViewHolder talentsWowSpecsViewHolder, int i) {
            WowSpec wowSpec = mWowSpecs.get(i);

            talentsWowSpecsViewHolder.mIdView.setText(Integer.toString(wowSpec.getId()));
            talentsWowSpecsViewHolder.mOrderView.setText(Integer.toString(wowSpec.getSpecOrder()));

            talentsWowSpecsViewHolder.mNameView.setText(wowSpec.getName());
            talentsWowSpecsViewHolder.mDescriptionView.setText(wowSpec.getDescription());

            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), mBitmap);
            talentsWowSpecsViewHolder.mImageView.setBackground(bitmapDrawable);

            Log.d("TAG", "binding element at position " + i);
        }

        @Override
        public int getItemCount() {
            return mWowSpecs.size();
        }

        public void setWowSpecs(List<WowSpec> wowSpecs) {
            mWowSpecs = wowSpecs;
        }
    }

    class TalentsWowSpecsAsyncLoader extends AsyncTask<TalentsWowSpecsAdapter, Void, TalentsWowSpecsAdapter> {
        @Override
        protected TalentsWowSpecsAdapter doInBackground(TalentsWowSpecsAdapter... talentsWowSpecsAdapters) {
            talentsWowSpecsAdapters[0].setWowSpecs(mTalentsPresenter.getTalentsRepository().getWowSpecs());
            return talentsWowSpecsAdapters[0];
        }

        @Override
        protected void onPostExecute(TalentsWowSpecsAdapter talentsWowSpecsAdapter) {
            super.onPostExecute(talentsWowSpecsAdapter);
            talentsWowSpecsAdapter.notifyDataSetChanged();
        }
    }
}
