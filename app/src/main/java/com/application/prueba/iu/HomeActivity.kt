package com.application.prueba.iu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.application.prueba.R
import com.application.prueba.databinding.ActivityHomeBinding
import com.application.prueba.iu.ui.dashboard.DashboardFragment
import com.application.prueba.iu.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {
    private val viewModel:HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpvViewPager(binding.viewpager)

        binding.tabs.setupWithViewPager(binding.viewpager)

        binding.tabs.getTabAt(0)?.setIcon(R.drawable.ic_home_)?.setText("Home");
        binding.tabs.getTabAt(1)?.setIcon(R.drawable.ic_list_view)?.setText("List");

        binding.tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    binding.tabs.getTabAt(0)?.setIcon(R.drawable.ic_home_)
                } else if (tab.position == 1) {
                    binding.tabs.getTabAt(1)?.setIcon(R.drawable.ic_list_view)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    binding.tabs.getTabAt(0)?.setIcon(R.drawable.ic_home_)
                } else if (tab.position == 1) {
                    binding.tabs.getTabAt(1)?.setIcon(R.drawable.ic_list_view)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setUpvViewPager(viewpager: ViewPager) {

        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFrag(HomeFragment(), "Home")
        adapter.addFrag(DashboardFragment(), "Lista")

        viewpager.adapter = adapter;
    }

    internal class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}