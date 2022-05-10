package androidx.viewpager2.integration.testapp.test

/**
 * Created by fuyuguang on 2022/5/10 5:12 下午.
 * E-Mail ：2355245065@qq.com
 * Wechat :fyg13522647431
 * Tel : 13522647431
 * 修改时间：
 * 类描述：
 * 备注：
 */

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.integration.testapp.R
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL

class ParallelNestedScrollingActivity2 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewPager = ViewPager2(this).apply {
            layoutParams = matchParent()
            orientation = ORIENTATION_HORIZONTAL
            adapter = VpAdapter()
        }
        setContentView(viewPager)
    }

    class DataBean{
        var type :Int = 0
        var name :String = ""
    }
//    class VpAdapter : RecyclerView.Adapter<VpAdapter.ViewHolder>() {
    class VpAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var list = mutableListOf<DataBean>();
        init {
            var t: DataBean? = null;
            t = DataBean();
            t.name = "1"
            t.type = 1
            list.add(t)


            t = DataBean();
            t.name = "2"
            t.type = 2
            list.add(t)


            t = DataBean();
            t.name = "3"
            t.type = 1
            list.add(t)

            t = DataBean();
            t.name = "4"
            t.type = 2
            list.add(t)
        }


        override fun getItemCount(): Int {
            return list.size
        }

        @SuppressLint("ResourceType")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            if (viewType == 1){

                val root = inflater.inflate(R.layout.item_nested_recyclerviews, parent, false)
                return ViewHolder(root).apply {
                    rv1.setUpRecyclerView(RecyclerView.HORIZONTAL)
                    rv2.setUpRecyclerView(RecyclerView.VERTICAL)
                    if (viewType == 0){

                        (this.itemView as ViewGroup).getChildAt(0).visibility = View.VISIBLE;
                        (this.itemView as ViewGroup).getChildAt(1).visibility = View.VISIBLE;
                        (this.itemView as ViewGroup).getChildAt(2).visibility = View.VISIBLE;
//                    title.visibility = View.VISIBLE;
//                    rv1.visibility = View.VISIBLE;
                    }else{
//                    title.visibility = View.GONE;
//                    rv1.visibility = View.GONE;

                        (this.itemView as ViewGroup).getChildAt(0).visibility = View.GONE;
                        (this.itemView as ViewGroup).getChildAt(1).visibility = View.GONE;
                        (this.itemView as ViewGroup).getChildAt(2).visibility = View.GONE;

                    }
                }

            }else if (viewType == 2){

                val root = inflater.inflate(R.layout.item_card_layout, parent, false)
                return ViewHolder2(root)

            }
            val root = inflater.inflate(R.layout.controls, parent, false)
            return ViewHolder(root)

        }

        override fun getItemViewType(position: Int): Int {
//            return super.getItemViewType(position)
            return list[position].type
        }

//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            with(holder) {
//                title.text = title.context.getString(R.string.page_position, adapterPosition)
//                itemView.setBackgroundResource(PAGE_COLORS[position % PAGE_COLORS.size])
//            }
//        }

    override fun onBindViewHolder( holder: androidx.recyclerview.widget.RecyclerView.ViewHolder,position: Int) {


        if (getItemViewType(position) == 1){
            with(holder as ViewHolder){
                title.text = title.context.getString(R.string.page_position, adapterPosition)
                itemView.setBackgroundResource(PAGE_COLORS[position % PAGE_COLORS.size])
            }
        }else  if (getItemViewType(position) == 2){
            with(holder as ViewHolder2){
                title.text = title.context.getString(R.string.page_position, adapterPosition)
                itemView.setBackgroundResource(PAGE_COLORS[position % PAGE_COLORS.size])

                labelCenter.text = title.text
                labelBottom.text = title.text
            }
        }
    }


        private fun RecyclerView.setUpRecyclerView(orientation: Int) {
            layoutManager = LinearLayoutManager(context, orientation, false)
            adapter = RvAdapter(orientation)
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.page_title)
            val rv1: RecyclerView = itemView.findViewById(R.id.first_rv)
            val rv2: RecyclerView = itemView.findViewById(R.id.second_rv)
        }


        class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.label_top)
            val labelCenter: TextView = itemView.findViewById(R.id.label_center)
            val labelBottom: TextView = itemView.findViewById(R.id.label_bottom)
        }


}

    class RvAdapter(private val orientation: Int) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
        override fun getItemCount(): Int {
            return 40
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val tv = TextView(parent.context)
            tv.layoutParams = matchParent().apply {
                if (orientation == RecyclerView.HORIZONTAL) {
                    width = WRAP_CONTENT
                } else {
                    height = WRAP_CONTENT
                }
            }
            tv.textSize = 20f
            tv.gravity = Gravity.CENTER
            tv.setPadding(20, 55, 20, 55)
            return ViewHolder(tv)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder) {
                tv.text = tv.context.getString(R.string.item_position, adapterPosition)
                tv.setBackgroundResource(CELL_COLORS[position % CELL_COLORS.size])
            }
        }

        class ViewHolder(val tv: TextView) : RecyclerView.ViewHolder(tv)
    }
}

internal fun matchParent(): LayoutParams {
    return LayoutParams(MATCH_PARENT, MATCH_PARENT)
}

internal val PAGE_COLORS = listOf(
    R.color.yellow_300,
    R.color.green_300,
    R.color.teal_300,
    R.color.blue_300
)

internal val CELL_COLORS = listOf(
    R.color.grey_100,
    R.color.grey_300
)
