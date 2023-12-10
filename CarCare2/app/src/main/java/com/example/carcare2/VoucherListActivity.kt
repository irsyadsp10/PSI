package com.example.carcare2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carcare2.entity.Voucher

class VoucherListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher_list)

        val layoutHeader: View = findViewById(R.id.layoutHeader)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewVouchers)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val btnSelect: Button = findViewById(R.id.btnSelect)

        // Set warna StatusBar
        window.apply {
            statusBarColor = getColor(R.color.green)

            // Set teks dan ikon Navigation Bar berwarna putih
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }

        // Atur padding di atas untuk layoutHeader
        ViewCompat.setOnApplyWindowInsetsListener(layoutHeader) { _, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            layoutHeader.setPadding(0, systemInsets.top, 0, 0)
            insets
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        val vouchers = listOf(
            Voucher("Siapa Cepat Dia Dapat!", "Diskon service mobil Rp50 ribu.", R.drawable.otoklik),
            Voucher("30% BOSS!", "Potongan 30% di Bengkel Bos", R.drawable.bengkelbos),
            Voucher("Hemat 20% Sekarang Juga!", "Diskon 20% service apapun dengan menggunakan metode pembayaran gopay! ", R.drawable.diskongopay),
            Voucher("Makan Kenyang Mobil Senang!", "Dapatkan voucher bakmi sebesar Rp50 ribu!", R.drawable.voucherbakmi)
            // Tambahkan voucher lainnya sesuai kebutuhan
        )

        val voucherAdapter = VoucherAdapter(vouchers)
        recyclerView.adapter = voucherAdapter

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        voucherAdapter.setOnVoucherSelectedListener(object : VoucherAdapter.OnVoucherSelectedListener {
            override fun onVoucherSelected(selectedVouchers: List<Voucher>) {
                btnSelect.isEnabled = selectedVouchers.isNotEmpty()
                btnSelect.setBackgroundResource(
                    if (selectedVouchers.isNotEmpty()) R.color.green else R.color.grey
                )
            }
        })

        // Ganti warna background tombol "Pilih"
        btnSelect.setBackgroundResource(R.color.grey)

        btnSelect.setOnClickListener {
            // Tindakan yang diambil saat tombol "Pilih" diklik
            intent = Intent(this, Servis::class.java)
            intent.putExtra("voucher",voucherAdapter.selectedVouchers[0].name)
            Toast.makeText(applicationContext, voucherAdapter.selectedVouchers[0].name, Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}