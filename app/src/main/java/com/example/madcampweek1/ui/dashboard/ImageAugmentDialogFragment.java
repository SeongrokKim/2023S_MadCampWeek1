package com.example.madcampweek1.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.madcampweek1.databinding.FragmentImageAugmentDialogBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageAugmentDialogFragment extends DialogFragment {
    public static final String TAG = "DIALOG_IMAGE_AUG";
    private FragmentImageAugmentDialogBinding binding;
    private int photoIndex;
    private List<Map<String, String>> dataList;
    private ViewPager2 viewPager;
    private Button rightButton;
    private Button leftButton;
    private ImageAugmentPagerAdapter pagerAdapter;

    public static ImageAugmentDialogFragment getInstance() {
        ImageAugmentDialogFragment e = new ImageAugmentDialogFragment();
        e.photoIndex = 0;
        e.dataList = new ArrayList<>();
        return e;
    }
    public static ImageAugmentDialogFragment getInstance(List<Map<String, String>> dataList) {
        ImageAugmentDialogFragment e = new ImageAugmentDialogFragment();
        e.photoIndex = 0;
        e.dataList = dataList;
        return e;
    }
    public static ImageAugmentDialogFragment getInstance(int photoIndex, List<Map<String, String>> dataList) {
        ImageAugmentDialogFragment e = new ImageAugmentDialogFragment();
        e.photoIndex = photoIndex;
        e.dataList = dataList;
        return e;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 다이얼로그의 크기를 디바이스 크기의 95%로 설정
        if (getDialog() != null) {
            // 디바이스의 가로 크기 가져오기
            int deviceWidth = getResources().getDisplayMetrics().widthPixels;
            // 디바이스의 세로 크기 가져오기
            int deviceHeight = getResources().getDisplayMetrics().heightPixels;
            int standard ;
            // 더 작은 쪽을 기준으로 크기 설정
            if (deviceHeight > deviceWidth)standard = deviceWidth;
            else standard= (int) (deviceHeight*0.8);
            // 보정 (0.9배)
            standard = (int) (standard*0.9);
            // 다이얼로그의 크기를 설정
            getDialog().getWindow().setLayout(standard, (int) (standard * 1.3));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentImageAugmentDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager = binding.viewPager;
        leftButton = binding.buttonLeft;
        rightButton = binding.buttonRight;

        // ViewPager 어댑터 설정
        pagerAdapter = new ImageAugmentPagerAdapter(getChildFragmentManager(), getLifecycle(), dataList);

        viewPager.setAdapter(pagerAdapter);
        setPhoto(photoIndex);
        // 왼쪽 버튼 클릭 이벤트 처리
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // photoIndex 감소 및 현재 인덱스로 이동
                changePhoto(-1);
            }
        });

        // 오른쪽 버튼 클릭 이벤트 처리
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // photoIndex 증가 및 현재 인덱스로 이동
                changePhoto(+1);
            }
        });

        return root;
    }

    public void changePhoto(int indexDiff){
        int photoIndex = viewPager.getCurrentItem();
        if (photoIndex + indexDiff >= 0 && photoIndex + indexDiff < dataList.size() ) {
            photoIndex += indexDiff;
        }
        viewPager.setCurrentItem(photoIndex);
    }
    public void setPhoto(int photoIndex){
        viewPager.setCurrentItem(photoIndex);
    }
    public class ImageAugmentPagerAdapter extends FragmentStateAdapter {
        private List<Map<String, String>> imageList;

        public ImageAugmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Map<String, String>> imageList) {
            super(fragmentManager, lifecycle);
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Map<String,String> imageMap = imageList.get(position);
            return ImageAugmentFragment.newInstance(imageMap);
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }
    }
}