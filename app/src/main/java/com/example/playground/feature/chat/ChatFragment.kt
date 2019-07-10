package com.example.playground.feature.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playground.R
import javax.inject.Inject

class ChatFragment : Fragment() {


    @field:[Inject]
    lateinit var presenter: ChatFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_chat, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInjections()
        presenter.create()
    }

    private fun initInjections() {
        DaggerChatFragmentComponent.builder()
            .chatFragmentModule(ChatFragmentModule(this))
            .build()
            .inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
    }
}