package com.example.madcampweek1.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampweek1.R;
import com.example.madcampweek1.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerViewContacts;
    private Button buttonAddContact;
    private ArrayList<Contact> contactList = new ArrayList<>();
    private ContactAdapter contactAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (contactList.size()==0) {
            // contactList가 null인 경우에만 초기화
            contactList = getInitialContactList();
        }

        // RecyclerView 초기화
        recyclerViewContacts = root.findViewById(R.id.recyclerViewContacts);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 연락처 어댑터 초기화
        contactAdapter = new ContactAdapter(contactList);
        recyclerViewContacts.setAdapter(contactAdapter);



        // 새 연락처 추가 버튼 클릭 이벤트 처리
        buttonAddContact = root.findViewById(R.id.buttonAddContact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 생성
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
                dialogBuilder.setTitle("새 연락처 추가");

                // 다이얼로그에 표시될 레이아웃 설정
                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_contact, null);
                dialogBuilder.setView(dialogView);

                // 다이얼로그 내의 뷰 요소 가져오기
                EditText editTextName = dialogView.findViewById(R.id.editTextName);
                EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);

                // 확인 버튼 클릭 시 동작 설정
                dialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 사용자가 입력한 값 가져오기
                        String name = editTextName.getText().toString();
                        String phoneNumber = editTextPhoneNumber.getText().toString();

                        // 새로운 연락처 데이터 생성
                        Contact newContact = new Contact(name, phoneNumber);

                        // contactList에 새로운 연락처 데이터 추가
                        contactList.add(newContact);

                        // 어댑터에 데이터 변경을 알려줍니다.
                        contactAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                // 취소 버튼 클릭 시 동작 설정
                dialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // 다이얼로그 보여주기
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });


        return root;
    }

    public class Contact {
        private String name;
        private String phoneNumber;

        public Contact(String name, String phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
    }

    public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

        private ArrayList<Contact> contactList;

        public ContactAdapter(ArrayList<Contact> contactList) {
            this.contactList = contactList;
        }

        // ViewHolder 클래스와 메소드 구현 생략

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // ViewHolder 생성 및 반환하는 코드 작성
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // ViewHolder에 데이터를 바인딩하는 코드 작성
            Contact contact = contactList.get(position);
            holder.textViewName.setText(contact.getName());
            holder.textViewPhoneNumber.setText(contact.getPhoneNumber());
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // ViewHolder 내부의 뷰 요소 선언
            TextView textViewName;
            TextView textViewPhoneNumber;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // 뷰 요소 초기화 작업
                textViewName = itemView.findViewById(R.id.nameItem);
                textViewPhoneNumber = itemView.findViewById(R.id.numItem);
            }
        }
    }

    private ArrayList<Contact> getInitialContactList() {
        ArrayList<Contact> initialList = new ArrayList<>();

        // 9개의 초기 연락처 데이터 생성 및 추가
        initialList.add(new Contact("철수", "010-1234-5678"));
        initialList.add(new Contact("영희", "010-2345-6789"));
        initialList.add(new Contact("엄마", "010-3456-7890"));
        initialList.add(new Contact("아빠", "010-4567-8901"));
        initialList.add(new Contact("동생", "010-5678-9012"));
        initialList.add(new Contact("형", "010-6789-0123"));
        initialList.add(new Contact("누나", "010-7890-1234"));
        initialList.add(new Contact("할머니", "010-8901-2345"));
        initialList.add(new Contact("할아버지", "010-9012-3456"));

        return initialList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}