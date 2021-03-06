package com.example.kindom.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kindom.R;
import com.example.kindom.chat.ChatListAdapter;
import com.example.kindom.chat.ChatObject;
import com.example.kindom.utils.FirebaseHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ChatFragment extends Fragment {

    private RecyclerView mChatList;
    private RecyclerView.Adapter mChatListAdapter;

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
        initializeRecyclerView(rootView);
        getUserChatList(rootView);

        return rootView;
    }

    /**
     * Retrieve the users' chatList from the database
     *
     * @param view the view
     */
    private void getUserChatList(final View view) {
        final DatabaseReference mUserChatDB = FirebaseDatabase.getInstance().getReference("users").child(FirebaseHandler.getCurrentUserUid()).child("chatListKeys");

        mUserChatDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (final DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        final String chatUserId = Objects.requireNonNull(childSnapshot.getValue()).toString();
                        mUserChatDB.getParent().getParent().child(chatUserId).child("name").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    ChatObject mChat = new ChatObject(childSnapshot.getKey(), snapshot.getValue().toString(), chatUserId);
                                    chatList.add(mChat);
                                    view.findViewById(R.id.chat_empty).setVisibility(View.GONE);
                                    mChatListAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Do nothing
            }
        });
    }

    /**
     * Initialize chat list with recycler view of Chat Objects
     *
     * @param view the view
     */
    private void initializeRecyclerView(View view) {
        mChatList.setNestedScrollingEnabled(false);
        mChatList.setHasFixedSize(false);
        RecyclerView.LayoutManager mChatListLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mChatList.setLayoutManager(mChatListLayoutManager);
        mChatListAdapter = new ChatListAdapter(this.getContext(), chatList);
        mChatList.setAdapter(mChatListAdapter);

        if (chatList.size() == 0) {
            view.findViewById(R.id.chat_empty).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.chat_empty).setVisibility(View.GONE);
        }
    }
}