package com.example.playground.feature.chat.state

import com.example.playground.data.entity.MessageEntity
import com.example.playground.feature.chat.ChatListViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter

sealed class State {
    object StartLoading: State()
    object FinishLoading: State()
    object LaunchSignInPage: State()
    object ShowConfirmSignOutDialog: State()
    data class ShowRecyclerView(val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<MessageEntity, ChatListViewHolder>): State()
}