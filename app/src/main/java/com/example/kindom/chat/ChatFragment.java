package com.example.kindom.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kindom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatFragment extends Fragment {

    private RecyclerView mChatList;
    private RecyclerView.Adapter mChatListAdapter;
    private RecyclerView.LayoutManager mChatListLayoutManager;

    ArrayList<ChatObject> chatList;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        mChatList = rootView.findViewById(R.id.chatList);

        //bug identified!! dont let the chatIds be the same or else messages wont display correctly
        //this chunk of code is dummy code for adding chat for this user/needs a add contact portion
        //stuck because if I want to chat with someone, I need to access their key to add this chat to their chatListKeys
        String testChatID = "iaggsddiuahs&^(&(";
        String testChatTitle = "test1";
        ChatObject tester = new ChatObject(testChatTitle, testChatID);
        chatList.add(tester);
        Map<String, Object> testMap = new HashMap<>();
        testMap.put(testChatID, testChatTitle);
        DatabaseReference chatListRef = FirebaseDatabase.getInstance().getReference().child("chatLists").push();
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("chatListKeys").updateChildren(testMap);
        //this will add the chat to that persons chatListKeys
        FirebaseDatabase.getInstance().getReference().child("users").child("paERsLtvYahiSa8xMxzDrYtDQuT2").child("chatListKeys").updateChildren(testMap);

        initializeRecyclerView();
        getUserChatList();

        return rootView;
    }

    //retrieves the users' chatList from the database
    private void getUserChatList() {
        DatabaseReference mUserChatDB = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("chatList");

        mUserChatDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        //might be swapped for the chatId and the title
                        ChatObject mChat = new ChatObject("test",childSnapshot.getKey());
                        chatList.add(mChat);
                        mChatListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Initializes Chat list with recycler view of Chat Objects
    private void initializeRecyclerView() {
        mChatList.setNestedScrollingEnabled(false);
        mChatList.setHasFixedSize(false);
        mChatListLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mChatList.setLayoutManager(mChatListLayoutManager);
        mChatListAdapter = new ChatListAdapter(chatList);
        mChatList.setAdapter(mChatListAdapter);
    }
}