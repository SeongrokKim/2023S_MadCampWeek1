package com.example.madcampweek1.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.madcampweek1.R;
import com.example.madcampweek1.databinding.FragmentImageAugmentBinding;

import java.util.Map;

public class ImageAugmentFragment extends Fragment {
    private static final String ARG_IMAGE_RESOURCE_ID = "imageResourceId";
    private int imageResourceId;
    private String photoName;
    private String photoPath;
    private FragmentImageAugmentBinding binding;

    public static ImageAugmentFragment newInstance(Map<String, String> map) {
        ImageAugmentFragment fragment = new ImageAugmentFragment();
        fragment.photoName = map.get("name");
        fragment.photoPath = map.get("path");
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // ImageAugmentFragment의 레이아웃을 inflate하고 반환합니다.
        binding = FragmentImageAugmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView imageView = binding.imageView;
        TextView textView = binding.textView;
        // imageResourceId를 사용하여 이미지를 로드하고 ImageView에 설정하는 로직을 구현
        textView.setText(photoName);
        //외부 말고 내부만
        //holder.imageView = pathToImageView(filePath);
        imageView.setImageResource(getResources().getIdentifier(photoPath, "drawable", getContext().getPackageName()));


        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}