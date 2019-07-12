package com.example.playground.feature.chat

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.example.playground.R
import de.hdodenhof.circleimageview.CircleImageView

class ChatListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    @BindView(R.id.messageTextView)
    lateinit var messageTextView: TextView

    @BindView(R.id.messageImageView)
    lateinit var messageImageView: ImageView

    @BindView(R.id.userNameTextView)
    lateinit var userNameTextView: TextView

    @BindView(R.id.userPicImageView)
    lateinit var userPicImageView: CircleImageView
}