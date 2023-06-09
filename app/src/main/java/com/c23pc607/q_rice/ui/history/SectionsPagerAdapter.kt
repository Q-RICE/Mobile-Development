package com.c23pc607.q_rice.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        val fragment = ServiceFragment()
        fragment.arguments = Bundle().apply {
            putInt(ServiceFragment.ARG_POSITION, position + 1)
            putString(ServiceFragment.ARG_USERNAME, username)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 4
    }
}