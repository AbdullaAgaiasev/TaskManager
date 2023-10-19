package com.example.taskmanager.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.databinding.ItemOnboardingBinding
import com.example.taskmanager.model.OnBoarding
import com.example.taskmanager.utils.loadImage

class OnBoardingAdapter(private val onClick: () -> Unit) :
    Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    private val list = arrayListOf(
        OnBoarding(
            "Добро пожаловать",
            "Добро пожаловать в наше приложение, в нашем приложении вы сможете написать и сохранить любую информацию",
            "https://cdn-icons-png.flaticon.com/512/762/762686.png"
        ),
        OnBoarding(
            "Обновление",
            "На данный момент наше приложение в стадии разроботки поэтому ожидайте новых обновлений.",
            "https://cdn-icons-png.flaticon.com/512/8028/8028200.png"
        ),
        OnBoarding(
            "До скорой встречи",
            "Надеюсь у вас не возникнет никаких проблем при использовании нашего приложения.",
            "https://cdn-icons-png.flaticon.com/512/4345/4345800.png"
        )
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            ItemOnboardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class OnBoardingViewHolder(private val binding: ItemOnboardingBinding) :
        ViewHolder(binding.root) {

        fun bind(onBoarding: OnBoarding) = with(binding) {
            tvTitle.text = onBoarding.title
            tvDesc.text = onBoarding.desc
            tvSkip.isVisible = adapterPosition != list.lastIndex
            btnStart.isVisible = adapterPosition == list.lastIndex

            btnStart.setOnClickListener {
                onClick
            }

            tvSkip.setOnClickListener {
                onClick
            }
            ivBoard.loadImage(onBoarding.image.toString())
        }

    }

}