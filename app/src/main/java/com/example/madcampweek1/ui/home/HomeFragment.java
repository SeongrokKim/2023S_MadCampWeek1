package com.example.madcampweek1.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampweek1.R;
import com.example.madcampweek1.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerViewContacts;
    private Button buttonAddContact;
    private JSONArray contactList;
    private ContactAdapter contactAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (contactList == null) {
            contactList = getInitialContactList();
        }

        recyclerViewContacts = root.findViewById(R.id.recyclerViewContacts);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getActivity()));

        contactAdapter = new ContactAdapter(contactList);
        recyclerViewContacts.setAdapter(contactAdapter);

        buttonAddContact = root.findViewById(R.id.buttonAddContact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
                dialogBuilder.setTitle("새 연락처 추가");

                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_contact, null);
                dialogBuilder.setView(dialogView);

                EditText editTextName = dialogView.findViewById(R.id.editTextName);
                EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);

                dialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextName.getText().toString();
                        String phoneNumber = editTextPhoneNumber.getText().toString();
                        try{
                            JSONObject newContact = new JSONObject();
                            newContact.put("name", name);
                            newContact.put("phoneNumber", phoneNumber);
                            contactList.put(newContact);

                            contactAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });

                dialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });
        return root;
    }


    public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

        private JSONArray contactList;

        public ContactAdapter(JSONArray contactlist) {
            this.contactList = contactlist;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            try{
                JSONObject jsonObject = contactList.getJSONObject(position);
                String name = jsonObject.getString("name");
                String phoneNumber = jsonObject.getString("phoneNumber");
                holder.textViewName.setText(name);
                holder.textViewPhoneNumber.setText(phoneNumber);
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showPopupMenu(holder.itemView, position);
                        return true;
                    }
                });

            }catch (JSONException e){
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return contactList.length();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName;
            TextView textViewPhoneNumber;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.nameItem);
                textViewPhoneNumber = itemView.findViewById(R.id.numItem);
            }
        }
        private void showPopupMenu(View view, final int position) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.menu_edit) {
                        showEditDialog(position);
                        return true;
                    } else if (itemId == R.id.menu_delete) {
                        deleteContact(position);
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            popupMenu.show();
        }

        private void showEditDialog(final int position) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
            dialogBuilder.setTitle("연락처 편집");

            View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_contact, null);
            dialogBuilder.setView(dialogView);

            EditText editTextName = dialogView.findViewById(R.id.editTextName);
            EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);

            try {
                JSONObject contact = contactList.getJSONObject(position);
                String name = contact.getString("name");
                String phoneNumber = contact.getString("phoneNumber");
                editTextName.setText(name);
                editTextPhoneNumber.setText(phoneNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            dialogBuilder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = editTextName.getText().toString();
                    String phoneNumber = editTextPhoneNumber.getText().toString();
                    try {
                        JSONObject updatedContact = contactList.getJSONObject(position);
                        updatedContact.put("name", name);
                        updatedContact.put("phoneNumber", phoneNumber);
                        contactList.put(position, updatedContact);
                        contactAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            dialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }

        private void deleteContact(int position) {
            contactList.remove(position);
            contactAdapter.notifyItemRemoved(position);
            contactAdapter.notifyItemRangeChanged(position, contactList.length());
        }
    }

    private JSONArray getInitialContactList() {
        JSONArray jsonArray = new JSONArray();
        try{
            JSONObject contact1 = new JSONObject();
            contact1.put("name", "철수");
            contact1.put("phoneNumber", "010-1234-5678");
            jsonArray.put(contact1);

            JSONObject contact2 = new JSONObject();
            contact2.put("name", "영희");
            contact2.put("phoneNumber", "010-2345-6789");
            jsonArray.put(contact2);

            JSONObject contact3 = new JSONObject();
            contact3.put("name", "엄마");
            contact3.put("phoneNumber", "010-3456-7890");
            jsonArray.put(contact3);

            JSONObject contact4 = new JSONObject();
            contact4.put("name", "아빠");
            contact4.put("phoneNumber", "010-4567-8901");
            jsonArray.put(contact4);

            JSONObject contact5 = new JSONObject();
            contact5.put("name", "동생");
            contact5.put("phoneNumber", "010-5678-9012");
            jsonArray.put(contact5);

            JSONObject contact6 = new JSONObject();
            contact6.put("name", "형");
            contact6.put("phoneNumber", "010-6789-0123");
            jsonArray.put(contact6);

            JSONObject contact7 = new JSONObject();
            contact7.put("name", "누나");
            contact7.put("phoneNumber", "010-7890-1234");
            jsonArray.put(contact7);

            JSONObject contact8 = new JSONObject();
            contact8.put("name", "할머니");
            contact8.put("phoneNumber", "010-8901-2345");
            jsonArray.put(contact8);

            JSONObject contact9 = new JSONObject();
            contact9.put("name", "할아버지");
            contact9.put("phoneNumber", "010-9012-3456");
            jsonArray.put(contact9);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}