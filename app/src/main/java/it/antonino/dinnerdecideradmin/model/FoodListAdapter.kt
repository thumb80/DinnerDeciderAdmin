package it.antonino.dinnerdecideradmin.model

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.antonino.dinnerdecideradmin.R
import it.antonino.dinnerdecideradmin.databinding.DinnerCardBinding


class FoodListAdapter(
    private val context: Context,
    private val items: List<String>
) : BaseAdapter() {

    var selectedPosition = -1

    override fun getCount() = items.size
    override fun getItem(position: Int) = items[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.dinner_card, parent, false)

        val textView = view.findViewById<TextView>(R.id.dinner_name) // Assume you have a textView
        textView.text = items[position]

        // Highlight if selected
        if (position == selectedPosition) {
            view.setBackgroundColor(Color.LTGRAY) // selected state
        } else {
            view.setBackgroundColor(Color.TRANSPARENT) // default state
        }

        view.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged() // Refresh the list to reflect selection
        }

        return view
    }
}
