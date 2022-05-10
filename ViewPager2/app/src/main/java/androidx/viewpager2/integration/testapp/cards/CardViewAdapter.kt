/*
 * Copyright 2019 The Android Open Source Project
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

package androidx.viewpager2.integration.testapp.cards

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CardViewAdapter : RecyclerView.Adapter<CardViewHolder>() {


    private val mHeaders = SparseArray<View>()
    private val mFooters = SparseArray<View>()

    private var BASE_ITEM_TYPE_HEADER = 100000
    private var BASE_ITEM_TYPE_FOOTER = 200000


    fun addHeaderView(view: View?) {
        //判断给View对象是否还没有处在mHeaders数组里面
        if (mHeaders.indexOfValue(view) < 0) {
            mHeaders.put(BASE_ITEM_TYPE_HEADER++, view)
            notifyDataSetChanged()
        }
    }

    fun addFooterView(view: View?) {
        //判断给View对象是否还没有处在mFooters数组里面
        if (mFooters.indexOfValue(view) < 0) {
            mFooters.put(BASE_ITEM_TYPE_FOOTER++, view)
            notifyDataSetChanged()
        }
    }

    // 移除头部
    fun removeHeaderView(view: View?) {
        val index = mHeaders.indexOfValue(view)
        if (index < 0) return
        mHeaders.removeAt(index)
        notifyDataSetChanged()
    }

    // 移除底部
    fun removeFooterView(view: View?) {
        val index = mFooters.indexOfValue(view)
        if (index < 0) return
        mFooters.removeAt(index)
        notifyDataSetChanged()
    }

    fun getHeaderCount(): Int {
        return mHeaders.size()
    }

    fun getFooterCount(): Int {
        return mFooters.size()
    }

    override fun getItemCount(): Int {
        val itemCount = Card.DECK.size
        return itemCount + mHeaders.size() + mFooters.size()
    }

    fun getOriginalItemCount(): Int {
        return getItemCount() - mHeaders.size() - mFooters.size()
    }

    private fun isFooterPosition(position: Int): Boolean {
        return position >= getOriginalItemCount() + mHeaders.size()
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < mHeaders.size()
    }



    override fun getItemViewType(position: Int): Int {
        var position = position
        if (isHeaderPosition(position)) {
            //返回该position对应的headerview的  viewType
            return mHeaders.keyAt(position)
        }
        if (isFooterPosition(position)) {
            //footer类型的，需要计算一下它的position实际大小
            position = position - getOriginalItemCount() - mHeaders.size()
            return mFooters.keyAt(position)
        }
        position = position - mHeaders.size()
        return super.getItemViewType(position)
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

//        if (mHeaders.indexOfKey(viewType) >= 0) {
//            val view = mHeaders[viewType]
//            return object : ViewHolder(view) {}
//        }
//
//        if (mFooters.indexOfKey(viewType) >= 0) {
//            val view = mFooters[viewType]
//            return object : ViewHolder(view) {} as VH
//        }


        return CardViewHolder(CardView(LayoutInflater.from(parent.context), parent))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(Card.DECK[position])
    }

//    override fun getItemCount(): Int {
//        return Card.DECK.size
//    }

//    //通过 该方法 让 viewpager2中每个页面是一个 listview 或其他类型
//    override fun getItemViewType(position: Int): Int {
////        return super.getItemViewType(position)
//        if (position %2 == 0){
//            return  1;
//        }else{
//            return 2;
//        }
//    }




}

class CardViewHolder internal constructor(private val cardView: CardView) :
    RecyclerView.ViewHolder(cardView.view) {
    internal fun bind(card: Card) {
        cardView.bind(card)
    }
}
