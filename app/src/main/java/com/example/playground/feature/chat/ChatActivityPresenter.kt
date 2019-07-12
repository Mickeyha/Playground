package com.example.playground.feature.chat

import android.content.Context
import android.net.Uri
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playground.R
import com.example.playground.data.entity.MessageEntity
import com.example.playground.feature.chat.state.State
import com.example.playground.view.CommonConfirmDialog
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject
import com.firebase.ui.database.FirebaseRecyclerOptions.Builder as Builder1


class ChatActivityPresenter @Inject constructor(val view: ChatActivity,
                                                val context: Context
) {

    companion object {
        const val ANONYMOUS = "anonymous"
        const val MESSAGES_CHILD = "messages"   // top level of db
    }
    private var userName: String? = null
    private var userPhotoUrl: String? = null
    private val compositeDisposable = CompositeDisposable()

    // firebase
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
    private val googleApiClient = GoogleApiClient.Builder(context).enableAutoManage(view, view).addApi(Auth.GOOGLE_SIGN_IN_API).build()

    // firebase instance variables
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    lateinit var firebaseAdapter: FirebaseRecyclerAdapter<MessageEntity, ChatListViewHolder>

    fun create() {
        Timber.d("create")
        userName = ANONYMOUS

        view.render(State.StartLoading)
        // Not sign in yet, launch the SignInActivity
        if (firebaseUser == null) {
            view.render(State.LaunchSignInPage)
        } else {    // get the pic of user
            userName = firebaseUser.displayName
            userPhotoUrl = firebaseUser.photoUrl?.toString()
        }
        Timber.d("userName = $userName, userPhotoUrl = $userPhotoUrl")
        readMessageFromFirebase()
    }

    private fun readMessageFromFirebase() {
        // get message from remote
        val parser = SnapshotParser<MessageEntity> { dataSnapshot ->
            val message = dataSnapshot.getValue<MessageEntity>(MessageEntity::class.java)
            if (message != null) {
                Timber.d("Message != null")
                message.id = dataSnapshot.key
                return@SnapshotParser message
            }
            Timber.d("Message is null")
            MessageEntity()
        }

        val messagesRef: DatabaseReference = databaseReference
            .child(MESSAGES_CHILD)
        val options = FirebaseRecyclerOptions.Builder<MessageEntity>()
            .setQuery(messagesRef, parser)
            .build()

        // set up adapter
        firebaseAdapter = object : FirebaseRecyclerAdapter<MessageEntity, ChatListViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
                Timber.d("onCreateViewHolder")
                return ChatListViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_message_in, parent, false)
                )
            }

            override fun onBindViewHolder(holder: ChatListViewHolder, positoin: Int, message: MessageEntity) {
                view.render(State.FinishLoading)

                Timber.d("onBindViewHolder")
                // message view
                if (message.text != null) {
                    holder.messageTextView.apply {
                        text = message.text
                        visibility = View.VISIBLE
                    }
                    holder.messageImageView.visibility = View.GONE

                } else if (message.imageUrl != null) {

                }

                // user view
                holder.userNameTextView.text = message.name
                if (message.photoUrl != null) {
                    Glide.with(context)
                        .load(message.photoUrl)
                        .into(holder.userPicImageView)
                }
            }
        }

        firebaseAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
            }
        })

        view.render(State.ShowRecyclerView(firebaseAdapter))
    }

    fun destroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        Auth.GoogleSignInApi.signOut(googleApiClient)
        userName = ChatActivityPresenter.ANONYMOUS
        view.render(State.LaunchSignInPage)
    }

    fun pause() {
        Timber.d("pause()")
        firebaseAdapter.stopListening()
    }

    fun resume() {
        Timber.d("resume()")
        firebaseAdapter.startListening()
    }

    fun sendMessage(textMessage: String) {
        val messageEntity = MessageEntity(
            id = null,
            text = textMessage,
            name = userName,
            photoUrl = userPhotoUrl,
            imageUrl = null
        )

        databaseReference
            .child(MESSAGES_CHILD)
            .push()
            .setValue(messageEntity)

        view.render(State.ClearMessageEditTextView)
    }
}