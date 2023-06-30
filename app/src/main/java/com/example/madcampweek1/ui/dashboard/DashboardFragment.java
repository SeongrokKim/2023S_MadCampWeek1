package com.example.madcampweek1.ui.dashboard;

import android.graphics.Color;
import android.net.ipsec.ike.SaProposal;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.madcampweek1.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private final int repeatedLines = 5 ;
    private final int numImagesInCol = 4;
    private DashboardViewModel dashboardViewModel ;
    private Button addButton;
    private LinearLayout vLayout;
    private TextView nolText;

    private int lineMaxSize ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vLayout = binding.linearLayoutGalleryVertical;
        nolText = binding.textNumberOfLines ;
        addButton = binding.buttonGalleryAddLine;
        lineMaxSize = binding.scrollView2.getMeasuredHeight() / 4;
        Toast.makeText(getContext(), Integer.toString(lineMaxSize), Toast.LENGTH_SHORT).show();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼을 클릭했을 때 수행할 동작을 여기에 작성합니다.
                // 예를 들어, 다른 UI 업데이트, 데이터 처리 등을 수행할 수 있습니다.
                addNewLine();
            }
        });
        dashboardViewModel.getNumLines().observe(getViewLifecycleOwner(), nolText::setText);
//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addNewLine(){
        //대충 새로운 line add 하기
        // numLines 추가
        dashboardViewModel.increaseNumLines();

        // linear layout 추가
        // horizontal 생성
        LinearLayout hLayout = createHorizontalLayout();
        fillHLayout(hLayout, dashboardViewModel.getNumLinesInt() % repeatedLines);
        // vertical 에 추가
        vLayout.addView(hLayout);
        // toast message
        String message = "Add " + Integer.toString(dashboardViewModel.getNumLinesInt()) + "th line";
        int duration = Toast.LENGTH_SHORT ;
        Toast.makeText(getContext(), message, duration).show();
    }
    private void fillHLayout(LinearLayout layout, int lineNum){
        ImageView[] images = new ImageView[numImagesInCol] ;
        Space[] spaces = new Space[numImagesInCol-1];

        for(int i = 0 ; i < numImagesInCol ; i++) {
            images[i] = new ImageView(requireContext());
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    0,
                    300,
                    1f
            );

            images[i].setLayoutParams(imageParams);
            int imageResId = getResources().getIdentifier(
                    "temp_picture_" + Integer.toString(i+1 + lineNum*numImagesInCol),
                    "drawable",
                    requireContext().getPackageName());
            images[i].setImageResource(imageResId);
            layout.addView(images[i]);

            //cut final space
            if(i == numImagesInCol-1) break;

            spaces[i] = new Space(requireContext());
            LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.5f
            );
            spaces[i].setLayoutParams(spaceParams);
            layout.addView(spaces[i]);
        }
        layout.setBackgroundColor(Color.parseColor("#"+Integer.toString(lineNum)+"0"+Integer.toString(lineNum)+"0"+Integer.toString(lineNum)+"0"));



    }

    private LinearLayout createHorizontalLayout() {
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        return layout;
    }

}