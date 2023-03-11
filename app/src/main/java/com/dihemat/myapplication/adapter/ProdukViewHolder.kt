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
import com.dihemat.myapplication.model.TokoTerdekatModel
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class ProdukViewHolder(
    private val notesList: MutableList<TokoTerdekatModel>,
    private val context: Context
) : RecyclerView.Adapter<ProdukViewHolder.ViewHolder>() {

    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note: TokoTerdekatModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var namatoko: TextView
        internal var alamat: TextView
        internal var jarak: TextView
        internal var foto: RoundedImageView


        init {
            namatoko = view.findViewById(R.id.txtnamatoko)
            alamat = view.findViewById(R.id.txtalamat)
            jarak = view.findViewById(R.id.txtjarak)
            foto = view.findViewById(R.id.imgfoto)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_toko, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]

        val jarak = note.distance
        val number3digits: Double = (jarak!! * 1000.0).roundToInt() / 1000.0
        val number2digits: Double = (number3digits * 100.0).roundToLong() / 100.0
        val hasiljarak: Double = (number2digits * 10.0).roundToLong() / 10.0

        holder.jarak.text = "${hasiljarak.toString()} KM"
        holder.namatoko.text = note.namaToko
        holder.alamat.text = note.alamat

        Picasso.get().load(Constant.STORAGE + note.foto).fit().centerCrop().into(holder.foto)
        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}