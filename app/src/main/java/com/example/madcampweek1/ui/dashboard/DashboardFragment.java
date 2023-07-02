package com.example.madcampweek1.ui.dashboard;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ipsec.ike.SaProposal;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampweek1.R;
import com.example.madcampweek1.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private final int repeatedLines = 5 ;
    private final int numImagesInCol = 4;
    private DashboardViewModel dashboardViewModel ;
    private Button addButton;
    private TextView nolText;
    private RecyclerView recyclerViewGallery ;
    private JSONArray dataList;

    private GalleryAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        recyclerViewGallery = binding.recyclerViewGallery;

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerViewGallery.setLayoutManager(layoutManager);

        if (dataList == null){
            dataList = initDataList();
            for( int i = 0 ; i < dataList.length() ; i++)dashboardViewModel.increaseNumLines() ;
        }
        adapter = new GalleryAdapter(getContext(), dataList); // dataList는 표시할 데이터 목록

        recyclerViewGallery.setAdapter(adapter);

        nolText = binding.textNumberOfLines;
        addButton = binding.buttonGalleryAddLine;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardViewModel.increaseNumLines();
                String name = "image" + Integer.toString(dashboardViewModel.getNumLinesInt());
                String imagePath = "temp_picture_" + Integer.toString(dashboardViewModel.getNumLinesInt()); //temporal string

                JSONObject jsonObject = new JSONObject();
                Toast.makeText(getContext(), imagePath, Toast.LENGTH_SHORT).show();
                try {
                    jsonObject.put("name", name);
                    jsonObject.put("path", imagePath);
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    adapter.addItem(jsonObject);
                }catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Here", Toast.LENGTH_SHORT);
                }


            }
        });

        //Toast.makeText(getContext(), Integer.toString(lineMaxSize), Toast.LENGTH_SHORT).show();

        dashboardViewModel.getNumLines().observe(getViewLifecycleOwner(), nolText::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private JSONArray initDataList(){
        JSONArray dataList = new JSONArray();
        //String imagePath = imageDataProvider.getNextImagePath();
        for(int i = 1 ; i <= 20 ; i++) {
            String name = "image" + Integer.toString(i);
            String imagePath = "temp_picture_" + Integer.toString(i); //temporal string

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("path", imagePath);

                dataList.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Here", Toast.LENGTH_SHORT);
            }
        }
        return dataList;
    }

    public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        private Context context;
        private JSONArray dataList;

        public GalleryAdapter(Context context, JSONArray dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_grid_item_layout, parent, false);
            return new ViewHolder(view);
        }

        public void addItem(JSONObject item) {
            // JSON 하나 추가
            dataList.put(item);
            notifyItemInserted(dataList.length() - 1);
        }

        // RecyclerView 어댑터에 데이터를 추가하는 메서드
        public void addItem(JSONArray jsonArray) {
            //JSON Array 추가
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject item = jsonArray.getJSONObject(i);
                    dataList.put(item);
                    notifyItemInserted(dataList.length() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // 아이템 데이터 설정
            try {
                JSONObject dataItem = dataList.getJSONObject(position);
                // 아이템 데이터를 ViewHolder의 뷰에 바인딩하는 로직을 구현한다.
                // 예를 들어:
                String name = dataItem.getString("name");
                String filePath = dataItem.getString("path");
                holder.textView.setText(name);
                int resourceId = getResources().getIdentifier(filePath, "drawable", context.getPackageName());

                holder.imageView.setImageResource(resourceId);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ImageView가 클릭되었을 때 수행할 동작을 여기에 작성
                        // 예를 들어 해당 이미지를 확대해서 보여주는 등의 동작
                        //Toast.makeText(context, filePath, Toast.LENGTH_SHORT).show();
                        int imageResId = resourceId; // 클릭된 이미지 리소스 ID

                        // 새로운 프래그먼트를 생성하고 전달할 데이터를 설정합니다.
                        ImageAugmentFragment imageFragment = ImageAugmentFragment.newInstance(imageResId);
                        // 프래그먼트 트랜잭션을 시작합니다.
                        FragmentManager fragmentManager =  requireActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, imageFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return dataList.length();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);

            }
        }

    }
}