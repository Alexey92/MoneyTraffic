package com.example.money

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
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

        // Инициализация RecyclerView
        init()

        binding.navigation.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.records -> {
                    Toast.makeText(this, "records", Toast.LENGTH_SHORT).show()
                }
                R.id.statistics -> {
                    Toast.makeText(this, "statistics", Toast.LENGTH_SHORT).show()
                }
                R.id.settings -> {
                    Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show()
                }
            }
            binding.drawer.closeDrawer(GravityCompat.START)
            true
        }

        // Фиксируем нажатия на элементы Bottom Menu
        binding.bottomMenu.setOnItemSelectedListener {
            //  Какой элемент выбран
            when(it.itemId){
                // Открытие окна внесения новой записи
                R.id.add -> {
                    // Создание интента на запуск ActivityAddRecord с данными
                    val intent = Intent(this, ActivityAddRecord::class.java)
                    intent.putExtra(Constants.TOTAL_SUM, totalSum)

                    // Запуск интента
                    launcher?.launch(intent)
                }
                R.id.list -> Toast.makeText(this, "list", Toast.LENGTH_SHORT).show()
                R.id.find -> Toast.makeText(this, "find", Toast.LENGTH_SHORT).show()
            }
            true
        }

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

                    // Передаем новый элемент в RecyclerView
                    adapter.addRecord(record)
                }
            }
        }
    }

    // Заполнение ActionBar при запуске Activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    // Фиксируем нажатие на элементы ActionBar
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){

            // Обработка кнопки "Назад"
            android.R.id.home -> finish()

            // Обработка кнопки "Обнулить"
            R.id.zero -> {
                totalSum = 0
                binding.textMoney.text = totalSum.toString()   // Обновляем баланс
            }
        }
        return true
    }

    // Инициализация RecyclerView
    private fun init(){
        binding.apply {
            rcView.layoutManager =  LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
        }
    }
}