package com.example.madcampweek1.ui.notifications;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.madcampweek1.databinding.FragmentImageAugmentBinding;
import com.example.madcampweek1.databinding.FragmentTimerBinding;
import com.example.madcampweek1.ui.dashboard.ImageAugmentFragment;

public class TimerFragment extends Fragment {

    private int imageResourceId;
    private FragmentTimerBinding binding;

    public static ImageAugmentFragment newInstance(String imagePath) {
        ImageAugmentFragment fragment = new ImageAugmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_RESOURCE_ID, imagePath);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(), "first", Toast.LENGTH_SHORT).show();
        if (getArguments() != null) {
            imageResourceId = getArguments().getInt(ARG_IMAGE_RESOURCE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // ImageAugmentFragment의 레이아웃을 inflate하고 반환합니다.
        binding = FragmentTimerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView imageView = binding.imageView;
        if (getArguments() != null) {
            imageResourceId = getArguments().getInt(ARG_IMAGE_RESOURCE_ID);
        }

        // imageResourceId를 사용하여 이미지를 로드하고 ImageView에 설정하는 로직을 구현
        imageView.setImageResource(imageResourceId);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 프래그먼트를 종료하여 이전 화면으로 돌아감
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
