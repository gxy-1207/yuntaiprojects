package com.ytfu.yuntaifawu.ui.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ytfu.yuntaifawu.ui.chat.fragment.ChatListFragment
import com.ytfu.yuntaifawu.ui.falvguwen.fragment.FragmentLegalAdviser
import com.ytfu.yuntaifawu.ui.home.fragment.AdvisoryFragment
import com.ytfu.yuntaifawu.ui.users.fragment.UserHomeFragment
import com.ytfu.yuntaifawu.ui.users.fragment.UserPersonalFragment

class MainPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val cache = SparseArray<Fragment>()

    override fun getCount(): Int = 5

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UserHomeFragment.newInstance()
            1 -> ChatListFragment.newInstance()
            2 -> AdvisoryFragment.newInstance()
            3 -> FragmentLegalAdviser.newInstance()
            4 -> UserPersonalFragment.newInstance()
            else -> UserHomeFragment.newInstance()
        }


    }

}
