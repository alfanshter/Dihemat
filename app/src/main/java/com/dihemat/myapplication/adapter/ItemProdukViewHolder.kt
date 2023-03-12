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
import com.dihemat.myapplication.model.produktoko.ProdukTokoModel

import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class ItemProdukViewHolder(
    private val notesList: MutableList<ProdukTokoModel>,
    private val context: Context
) : RecyclerView.Adapter<ItemProdukViewHolder.ViewHolder>() {

    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note: ProdukTokoModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var produk: TextView
        internal var harga: TextView
        internal var keterangan: TextView
        internal var foto: RoundedImageView


        init {
            produk = view.findViewById(R.id.txtproduk)
            harga = view.findViewById(R.id.txtharga)
            keterangan = view.findViewById(R.id.txtketerangan)
            foto = view.findViewById(R.id.imgfoto)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_toko, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]

        holder.produk.text = note.namaProduk
        holder.keterangan.text = note.keterangan
        holder.harga.text = note.harga.toString()

        Picasso.get().load(Constant.STORAGE + note.foto).fit().centerCrop().into(holder.foto)
        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}