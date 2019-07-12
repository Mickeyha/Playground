package com.example.playground.feature.chat

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.example.playground.R
import de.hdodenhof.circleimageview.CircleImageView

class ChatListViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val messageTextView: TextView = v.findViewById(R.id.messageTextView)
    val messageImageView: ImageView = v.findViewById(R.id.messageImageView)
    val userNameTextView: TextView = v.findViewById(R.id.userNameTextView)
    val userPicImageView: CircleImageView = v.findViewById(R.id.userPicImageView)
}