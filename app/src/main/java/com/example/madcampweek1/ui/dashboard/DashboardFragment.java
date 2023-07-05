package com.example.madcampweek1.ui.dashboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.text.BoringLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampweek1.R;
import com.example.madcampweek1.databinding.FragmentDashboardBinding;
import com.example.madcampweek1.ui.notifications.TimerDialogFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.Manifest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFragment extends Fragment {
    private static final int REQUEST_PERMISSION = 1000;
    private int permissionCheckCamera;
    private final String TAG = "DASHBOARD FRAGMENT";
    private final int PICK_FROM_CAMERA = 1;
    private FragmentDashboardBinding binding;

    private DashboardViewModel dashboardViewModel ;
    private Button addButton;
    private TextView nolText;
    private RecyclerView recyclerViewGallery ;
    private List<Map<String, String>> dataList;
    private List<Map<String, String>> localList;
    //private GalleryAdapter adapter;
    private File tempFile;
    private final int STORAGE_PERMISSION_REQUEST_CODE = 49;
    private final int CAMERA_PERMISSION_REQUEST_CODE = 59;
    private final String STORAGE_PERMISSION = "android.permission.MANAGE_EXTERNAL_STORAGE";
    private ActivityResultLauncher<String> cameraPermissionLauncher;
    private ActivityResultLauncher<String> storagePermissionLauncher;
    private GalleryAdapter adapter;
    private ImageAugmentDialogFragment imageAugmentDialogFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // 프래그먼트에 관련된 코드 작성
        recyclerViewGallery = binding.recyclerViewGallery;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);

        recyclerViewGallery.setLayoutManager(layoutManager);
        dataList = dashboardViewModel.getDataList();
        //Toast.makeText(getContext(), Integer.toString(dataList.size()), Toast.LENGTH_SHORT).show();

        if (dataList.size() == 0){

            initDataList();  // 데이터를 어떻게 가져올지에 따라 구현되어야 합니다.
            dashboardViewModel.setDataList(dataList);
            for(Map<String,String> map : dataList) dashboardViewModel.increaseNumLines();
        }

        adapter = new GalleryAdapter(getContext(), dataList); // dataList는 표시할 데이터 목록

        recyclerViewGallery.setAdapter(adapter);

        nolText = binding.textNumberOfLines;
        addButton = binding.buttonGalleryAddLine;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardViewModel.increaseNumLines();
                int index = dashboardViewModel.getNumLinesInt()-1;
                adapter.addItem(localList.get(index % localList.size()));

            }
        });

        dashboardViewModel.getNumLines().observe(getViewLifecycleOwner(), nolText::setText);
        //dashboardViewModel.getDataListLive().observe(getViewLifecycleOwner(), )
        imageAugmentDialogFragment = ImageAugmentDialogFragment.getInstance(dataList);

        return root;
    }

    public void initDataList(){
          localList = getInternalStorageDrawableImages(getContext());
          for (int i = 0 ; i < 20 ; i++){
              dataList.add(localList.get(i));
          }
    }

    public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        private Context context;
        private List<Map<String, String>> dataList;

        public GalleryAdapter(Context context, List<Map<String, String>> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_grid_item_layout, parent, false);
            return new ViewHolder(view);
        }

        public void addItem(Map<String, String> item) {
            // JSON 하나 추가
            //잘못된 데이터명의 경우 Error로 이미지 변경
            if(getResources().getIdentifier(item.get("path"), "drawable", context.getPackageName()) == 0){
                item.replace("path", "error");
            }
            dataList.add(item);
            notifyItemInserted(dataList.size() - 1);
        }

        // RecyclerView 어댑터에 데이터를 추가하는 메서드
        public void addItem(List<Map<String, String>> maps) {
            //JSON Array 추가
            for (Map<String,String> item : maps) {
                dataList.add(item);
                notifyItemInserted(dataList.size() - 1);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // 아이템 데이터 설정

            Map<String,String> dataItem = dataList.get(position);
            // 아이템 데이터를 ViewHolder의 뷰에 바인딩하는 로직을 구현한다.
            // 예를 들어:
            String name = dataItem.get("name");
            String filePath = dataItem.get("path");// 실제 이미지 파일의 경로로 설정해야 함
            holder.textView.setText(name);
            //외부 말고 내부만

//            //썸네일로 이미지 할당
//            ////취소
//            //holder.imageView = pathToImageView(filePath);
//            int resourceId = getResources().getIdentifier(name, "drawable", context.getPackageName());
//            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), resourceId);
//            // 썸네일 크기를 결정합니다 (원하는 크기로 수정하세요)
//            int thumbnailWidth = 200;
//            int thumbnailHeight = 200;
//            // 이미지 크기를 조정하여 썸네일 Bitmap을 생성
//            Bitmap thumbnailBitmap = Bitmap.createScaledBitmap(originalBitmap, thumbnailWidth, thumbnailHeight, false);
//            // 사용이 끝난 Bitmap은 메모리 해제
//            originalBitmap.recycle();
//            holder.imageView.setImageBitmap(thumbnailBitmap);

            holder.imageView.setImageResource(getResources().getIdentifier(filePath, "drawable", context.getPackageName()));
            //holder.photoIndex = position;
            //Toast.makeText(getContext(), filePath, Toast.LENGTH_SHORT).show();

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ImageView가 클릭되었을 때 수행할 동작을 여기에 작성
                    // 예를 들어 해당 이미지를 확대해서 보여주는 등의 동작
                    //Toast.makeText(context, filePath, Toast.LENGTH_SHORT).show();
                    imageAugmentDialogFragment = ImageAugmentDialogFragment.getInstance(position, dataList);
                    imageAugmentDialogFragment.show(getParentFragmentManager(), ImageAugmentDialogFragment.TAG);

                }
            });

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            int photoIndex;
            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
        private ImageView pathToImageView(String filePath){
            ImageView imageView = new ImageView(context);
            if(filePath.contains("/")){
                //저장소 내부 파일을 다루는 경우
                File imageFile = new File(filePath);
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
            }else {
                //어플리케이션 내부 파일을 다루는 경우
                int resourceId = getResources().getIdentifier(filePath, "drawable", context.getPackageName());
                imageView.setImageResource(resourceId);
                //Toast.makeText(getContext(), filePath, Toast.LENGTH_SHORT).show();
            }
            imageView.setTag(filePath);
            return imageView ;
        }
    }

    public List<Map<String, String>> getInternalStorageDrawableImages(Context context) {
        List<Map<String, String>> imageList = new ArrayList<>();

        Field[] drawableFields = R.drawable.class.getDeclaredFields();
        Resources resources = context.getResources();

        for (Field field : drawableFields) {
            try {
                if (field.getType() == int.class) {
                    int resourceId = field.getInt(null);
                    String imageName = field.getName();

                    Drawable drawable = ContextCompat.getDrawable(context, resourceId);

                    if (drawable instanceof BitmapDrawable) {
                        // Drawable이 BitmapDrawable인 경우에만 이미지 파일로 간주
                        Map<String, String> imageMap = new HashMap<>();
                        imageMap.put("name", imageName);
                        imageMap.put("path", imageName);
                        imageList.add(imageMap);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return imageList;
    }

    private boolean isImageFile(String fileName) {
        String extension = getFileExtension(fileName);
        return extension != null && (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png"));
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return null;
    }
    private String getFileName(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(0, lastDotIndex);
        }
        return null;
    }
}

