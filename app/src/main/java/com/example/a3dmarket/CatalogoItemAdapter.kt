package com.example.a3dmarket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class CatalogoItemAdapter(private var mList: List<Order>) :
    RecyclerView.Adapter<CatalogoItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.catalogo_item_view, parent, false)


        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val order = mList[position]

        holder.textViewTitulo.text = order.title
        holder.textViewDescripcion.text = order.desc

        holder.itemView.setOnClickListener {
            val orderView = OrderView().apply {
                arguments = Bundle().apply {
                    putString("id", order.id)
                }
            }

            val context = holder.itemView.context
            if (context is FragmentActivity) {
                context.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView2, orderView)
                    .addToBackStack(null)
                    .commit()
            }
        }

    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewTitulo: TextView = itemView.findViewById(R.id.titulo)
        val textViewDescripcion: TextView = itemView.findViewById(R.id.descripcion)

    }
    fun updateData(newOrders: List<Order>) {
        mList = newOrders
        notifyDataSetChanged()
    }
}
