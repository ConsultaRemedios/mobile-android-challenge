package br.com.weslleymaciel.gamesecommerce.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import br.com.weslleymaciel.gamesecommerce.common.models.Banner

class BannerAdapter(fragmentManager: FragmentManager, private val fragments: List<Fragment>): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}