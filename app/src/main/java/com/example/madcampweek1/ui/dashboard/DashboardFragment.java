package com.example.madcampweek1.ui.dashboard;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.ipsec.ike.SaProposal;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.BoringLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.example.madcampweek1.MainActivity;
import com.example.madcampweek1.R;
import com.example.madcampweek1.databinding.FragmentDashboardBinding;
import com.example.madcampweek1.ui.notifications.TimerDialogFragment;
import com.example.madcampweek1.ui.notifications.TimerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.Manifest;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private final int repeatedLines = 5 ;
    private final int numImagesInCol = 4;
    private final int INITIAL_IMAGE_NUMBER = 20;
    private final String TAG = "DashboardFragment";
    private DashboardViewModel dashboardViewModel ;
    private Button addButton;
    private Button timerButton;
    private TextView nolText;
    private RecyclerView recyclerViewGallery ;
    private List<Map<String, String>> dataList;
    private List<Map<String, String>> imageList; // dataList와 다른 점 : imageList는 저장소 데이터들이고, dataList는 보여지는 data들임
    private boolean isImageListLoaded = false;
    private GalleryAdapter adapter;
    private static final int REQUEST_STORAGE_PERMISSION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        recyclerViewGallery = binding.recyclerViewGallery;

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerViewGallery.setLayoutManager(layoutManager);
        dataList = dashboardViewModel.getDataList();
        //Toast.makeText(getContext(), Integer.toString(dataList.size()), Toast.LENGTH_SHORT).show();

        if (dataList.size() == 0){

            dataList = initDataList();  // 데이터를 어떻게 가져올지에 따라 구현되어야 합니다.
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
                String name = "image" + Integer.toString(dashboardViewModel.getNumLinesInt());
                String imagePath = "temp_picture_" + Integer.toString(dashboardViewModel.getNumLinesInt()); //temporal string

                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("path", imagePath);
                //Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                adapter.addItem(map);

//                //저장소 적용
//                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    // 권한이 이미 허용된 경우 이미지 가져오기
//                    loadImageList();
//                    adapter.addItem(getNextImage());
//
//                } else {
//                    // 권한이 없는 경우 권한 요청
//
//                    requestStoragePermission();
//
//                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//                }

            }
        });

        //Toast.makeText(getContext(), Integer.toString(lineMaxSize), Toast.LENGTH_SHORT).show();



        timerButton = binding.buttonTimer;
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String routineName = "Tmp Rt";
                int reps = 2;
                List<Map<String, String>> exercises = new ArrayList<>();
                Map<String, String> exMap = new HashMap<>();
                exMap.put("name", "Exc1");
                exMap.put("time", "00:01:03");
                exercises.add(exMap);
                Map<String, String> exMap2 = new HashMap<>();
                exMap2.put("name", "Exc2");
                exMap2.put("time", "00:00:02");
                exercises.add(exMap2);
                Map<String, String> exMap3 = new HashMap<>();
                exMap3.put("name", "Exc3");
                exMap3.put("time", "00:00:03");
                exercises.add(exMap3);
                TimerDialogFragment timerDialogFragment = TimerDialogFragment.getInstance(routineName, reps, exercises);
                //TimerDialogFragment timerDialogFragment = TimerDialogFragment.getInstance();
                timerDialogFragment.show(getChildFragmentManager(), TimerDialogFragment.TAG_TIMER_DIALOG);
//                //// PopupWindow는 날린다.
//                //인수 설정 (수정필요 Ward)
//                String routineName = "Tmp Rt";
//                int reps = 3;
//                List<Map<String, String>> exercises = new ArrayList<>();
//                Map<String, String> exMap = new HashMap<>();
//                exMap.put("name", "TmpEx");
//                exMap.put("time", "00:12:34");
//                //여기까지
//
//                //POPup 선언
//                View popupView = getLayoutInflater().inflate(R.layout.fragment_timer, null);
//                PopupWindow mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                ((MainActivity)getActivity()).setPopupWindow(mPopupWindow);
//
//                //Toast.makeText(getContext(), "!3", Toast.LENGTH_SHORT).show();
//
//                // 데이터 패키징
////                Bundle bundle = new Bundle();
////                bundle.putString("routineName", routineName); // String 데이터 전달
////                bundle.putInt("reps", reps); // int 데이터 전달
////                bundle.putSerializable("exercises", (Serializable) exercises); // List<Map<String, String>> 데이터 전달
//
//                // TimerFragment 인스턴스 생성 및 번들 전달
//                TimerFragment fragment = new TimerFragment();
//                //fragment.setArguments(bundle);
//
//                //Toast.makeText(getContext(), fragment.getArguments().getString("routineName") + Integer.toString(fragment.getArguments().getInt("reps")), Toast.LENGTH_SHORT).show();
//
//                // Fragment를 추가하기 위해 FragmentManager 인스턴스 가져오기
//                FragmentManager fragmentManager = getChildFragmentManager();
//                // FragmentTransaction 시작
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                // Transaction에 TimerFragment 추가하고 커밋
//                transaction.replace(R.id.fragmentContainerView, fragment);
//                transaction.commit();
//
//                // PopupWindow 설정
//                ColorDrawable colorDrawable = new ColorDrawable(Color.BLACK); // 검은색 배경
//                mPopupWindow.setBackgroundDrawable(colorDrawable);
//
//                mPopupWindow.setFocusable(true);
//                mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
//                //Toast.makeText(getContext(), "되냐?", Toast.LENGTH_SHORT).show();


            }
        });

        dashboardViewModel.getNumLines().observe(getViewLifecycleOwner(), nolText::setText);
        //dashboardViewModel.getDataListLive().observe(getViewLifecycleOwner(), )
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<Map<String, String>> initDataList(){
        List<Map<String, String>> dList = new ArrayList<>();
        List<String> images = getDrawablePNGList(getContext());
        for(String image : images) {
            Map<String, String> map = new HashMap<>();
            map.put("name", image);
            map.put("path", image);

            dList.add(map);

        }
//        for(int i = 0 ; i < INITIAL_IMAGE_NUMBER ; i++) {
//            addDataList(getNextImage());
//        }
        return dList;
    }
    private void addDataList(Map<String, String> map){
        dataList.add(map);
        dashboardViewModel.increaseNumLines();
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
            holder.imageView = pathToImageView(filePath);
            Toast.makeText(getContext(), filePath, Toast.LENGTH_SHORT).show();
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ImageView가 클릭되었을 때 수행할 동작을 여기에 작성
                    // 예를 들어 해당 이미지를 확대해서 보여주는 등의 동작
                    //Toast.makeText(context, filePath, Toast.LENGTH_SHORT).show();
                    String imagePath = (String) holder.imageView.getTag();
                    // 새로운 프래그먼트를 생성하고 전달할 데이터를 설정합니다.
                    ImageAugmentFragment imageFragment = ImageAugmentFragment.newInstance(imagePath);
                    // 프래그먼트 트랜잭션을 시작합니다.
                    FragmentManager fragmentManager =  requireActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, imageFragment)
                            .addToBackStack(null)
                            .commit();
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
                Toast.makeText(getContext(), filePath, Toast.LENGTH_SHORT).show();
            }
            imageView.setTag(filePath);
            return imageView ;
        }
    }
    public List<String> getDrawablePNGList(Context context) {
        List<String> pngList = new ArrayList<>();
        Resources resources = context.getResources();
        String packageName = context.getPackageName();

        Field[] fields = R.drawable.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                String resourceName = field.getName();
                if (resourceName.endsWith(".png")) {
                    int resourceId = resources.getIdentifier(resourceName, "drawable", packageName);
                    pngList.add(resourceName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return pngList;
    }


    private void loadImageList() {
        //Toast.makeText(getContext(), Boolean.toString(isImageListLoaded), Toast.LENGTH_SHORT).show();
        if (isImageListLoaded) {
            // 이미지 목록이 이미 로드되었다면 다시 가져올 필요 없음
            return;
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 내부 저장소 권한이 허용되지 않은 경우 권한 요청
            requestStoragePermission();
        } else {
            // 내부 저장소 권한이 허용된 경우 이미지 파일 목록을 가져오기
            retrieveImageList();
        }
    }


    private ActivityResultLauncher<String> requestExternalStoragePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // 권한이 허용된 경우 처리 수행
                    // ...
                    retrieveImageList();
                } else {
                    // 권한이 거부된 경우 처리 수행
                    // ...
                    Toast.makeText(getContext(), "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestExternalStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
    private void retrieveImageList() {
        ContentResolver contentResolver = requireContext().getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            imageList = new ArrayList<>();

            do {
                String name = cursor.getString(Math.max(0, cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                String path = cursor.getString(Math.max(0, cursor.getColumnIndex(MediaStore.Images.Media.DATA)));

                Map<String, String> imageMap = new HashMap<>();
                imageMap.put("name", name);
                imageMap.put("path", path);

                imageList.add(imageMap);
            } while (cursor.moveToNext());

            cursor.close();
            isImageListLoaded = true;
        }
    }

    public HashMap<String, String> getNextImage(){
        if (!isImageListLoaded){
            loadImageList();
        }

        int index = dashboardViewModel.getNumLinesInt();
        HashMap<String, String> map = (HashMap<String, String>) imageList.get(index);
        dashboardViewModel.increaseNumLines();
        return map;
    }

}