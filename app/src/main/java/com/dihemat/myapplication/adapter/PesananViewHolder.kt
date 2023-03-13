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
import com.dihemat.myapplication.model.pesan.PesananModel

import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class PesananViewHolder(
    private val notesList: MutableList<PesananModel>,
    private val context: Context
) : RecyclerView.Adapter<PesananViewHolder.ViewHolder>() {

    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note: PesananModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var kode: TextView
        internal var status: TextView


        init {
            kode = view.findViewById(R.id.txtkodepesanan)
            status = view.findViewById(R.id.txtstatus)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pesanan, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]

        holder.kode.text = "Kode ${note.kodePesanan}"
        if (note.isStatus == 0){
            holder.status.text = "Status : Sedang di proses"
        }

        else if (note.isStatus == 1){
            holder.status.text = "Status : Selesai"
        }
        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}