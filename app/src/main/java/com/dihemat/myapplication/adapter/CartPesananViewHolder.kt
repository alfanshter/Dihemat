package com.dihemat.myapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dihemat.myapplication.R
import com.dihemat.myapplication.Utils.Constant
import com.dihemat.myapplication.model.cart.CartOrderModel

import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class CartPesananViewHolder(
    private val notesList: MutableList<CartOrderModel>,
    private val context: Context
) : RecyclerView.Adapter<CartPesananViewHolder.ViewHolder>() {

    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note: CartOrderModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var nama: TextView
        internal var harga: TextView
        internal var jumlah: TextView


        init {
            nama = view.findViewById(R.id.txtnama)
            harga = view.findViewById(R.id.txtharga)
            jumlah = view.findViewById(R.id.txtjumlah)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cart_pesanan, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]

        holder.nama.text = note.nama
        holder.jumlah.text = "${note.jumlah.toString()}X"
        holder.harga.text = note.harga.toString()

        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}