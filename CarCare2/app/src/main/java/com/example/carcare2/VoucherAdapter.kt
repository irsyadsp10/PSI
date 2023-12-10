package com.example.carcare2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carcare2.entity.Voucher

class VoucherAdapter(private val vouchers: List<Voucher>) : RecyclerView.Adapter<VoucherAdapter.ViewHolder>() {

    var selectedVouchers = mutableListOf<Voucher>()
    private var onVoucherSelectedListener: OnVoucherSelectedListener? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgVoucher: ImageView = itemView.findViewById(R.id.imgVoucher)
        val tvVoucherName: TextView = itemView.findViewById(R.id.tvVoucherName)
        val tvVoucherDescription: TextView = itemView.findViewById(R.id.tvVoucherDescription)
        val cardView: View = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.voucher_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voucher = vouchers[position]
        holder.imgVoucher.setImageResource(voucher.imageResourceId)
        holder.tvVoucherName.text = voucher.name
        holder.tvVoucherDescription.text = voucher.description

        // Tandai kartu terpilih atau tidak
        holder.cardView.isActivated = selectedVouchers.contains(voucher)

        // Atur listener untuk memantau pemilihan kartu
        holder.itemView.setOnClickListener {
            toggleSelection(voucher, holder)
            notifyListener()
        }
    }

    // Metode untuk membalik status pemilihan voucher
    private fun toggleSelection(voucher: Voucher, holder: ViewHolder) {
        if (selectedVouchers.contains(voucher)) {
            selectedVouchers.remove(voucher)
        } else {
            selectedVouchers.clear()
            selectedVouchers.add(voucher)
        }

        // Tambahkan efek visual untuk kartu yang dipilih
        holder.cardView.setBackgroundResource(
            if (holder.cardView.isActivated) R.drawable.selected_background else android.R.color.transparent
        )
    }

    override fun getItemCount(): Int {
        return vouchers.size
    }

    fun setOnVoucherSelectedListener(listener: OnVoucherSelectedListener) {
        onVoucherSelectedListener = listener
    }

    private fun notifyListener() {
        onVoucherSelectedListener?.onVoucherSelected(selectedVouchers)
    }

    interface OnVoucherSelectedListener {
        fun onVoucherSelected(selectedVouchers: List<Voucher>)
    }
}