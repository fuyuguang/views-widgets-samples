/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.viewpager2.integration.testapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.integration.testapp.cards.Card
import androidx.viewpager2.integration.testapp.cards.CardView


/**
 * Shows how to use a [androidx.viewpager2.widget.ViewPager2] with Fragments, via a
 * [androidx.viewpager2.adapter.FragmentStateAdapter]
 *
 * @see CardViewActivity for an example of using {@link androidx.viewpager2.widget.ViewPager2} with
 * Views.
 */
class CardFragmentActivity : BaseCardActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return CardFragment.create(Card.DECK[position])
            }

            override fun getItemCount(): Int {
                return Card.DECK.size
            }
        }

        // 设置Viewpager2   禁用预抓取， 和 禁用缓存，，， viewpager2 也可做到 fragment的懒加载和  fragment的缓存
        if (viewPager.getChildAt(0) is RecyclerView) {
            val recyclerView : RecyclerView = viewPager.getChildAt(0) as RecyclerView
            recyclerView.layoutManager!!.isItemPrefetchEnabled = false
            recyclerView.setItemViewCacheSize(0)

        }
    }

    class CardFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            Log.e("fyg","this.hashCode()  :  "+this.hashCode())
            val cardView = CardView(layoutInflater, container)
            cardView.bind(Card.fromBundle(arguments!!))
            return cardView.view
        }


        override fun onDestroy() {
            super.onDestroy()
            Log.e("fyg","this.hashCode()  :  "+this.hashCode() + "   onDestroy ")
        }

        companion object {

            /** Creates a Fragment for a given [Card]  */
            fun create(card: Card): CardFragment {
                val fragment = CardFragment()
                fragment.arguments = card.toBundle()
                return fragment
            }
        }
    }
}
