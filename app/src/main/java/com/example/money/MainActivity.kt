package com.example.money

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.money.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private var totalSum : Int = 0
    private var launcher: ActivityResultLauncher<Intent>? = null
    private val adapter = RecordAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Money"

        init()

        // Возвращение в основное окно из новых окон
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->

            if(result.resultCode == RESULT_OK){
                var record = Record()

                // Чтение записи
                record = result.data?.getSerializableExtra(Constants.RECORD) as Record

                // Прибавляем сумму к балансу
                totalSum += record.sum!!

                binding.apply {
                    textMoney.text = totalSum.toString()   // Обновляем баланс

                    // Обновляем записи в логе
                    textLastOperation2.text = binding.textLastOperation1.text
                    textLastOperation1.text = binding.textLastOperation.text
                    textLastOperation.text = record.createLog()

                    adapter.addRecord(record)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.zero -> {
                totalSum = 0
                binding.textMoney.text = totalSum.toString()   // Обновляем баланс
            }

        }

        return true
    }

    // Открытие окна внесения новой записи
    fun onClickGoAddRecord(view: View){
        // Создание интента на запуск ActivityAddRecord с данными
        val intent = Intent(this, ActivityAddRecord::class.java)
        intent.putExtra(Constants.TOTAL_SUM, totalSum)

        // Запуск интента
        launcher?.launch(intent)
    }

    // Скрыть/посмотреть историю записей
    fun onClickViewRecords(view: View){
        binding.apply {
            if (textLastOperation.visibility == View.GONE) {
                Log.d("MyLog", "enable log")

                textLastOperation.visibility = View.VISIBLE
                textLastOperation1.visibility = View.VISIBLE
                textLastOperation2.visibility = View.VISIBLE
            } else {
                Log.d("MyLog", "disable log")

                textLastOperation.visibility = View.GONE
                textLastOperation1.visibility = View.GONE
                textLastOperation2.visibility = View.GONE
            }
        }
    }

    private fun init(){
        binding.apply {
            rcView.layoutManager =  LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
        }
    }
}