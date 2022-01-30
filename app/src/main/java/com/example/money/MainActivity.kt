package com.example.money

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.money.databinding.ActivityMainBinding
import com.example.money.db.RecordDbManager

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private var balance : Int = 0  // Текущий баланс
    private var launcher: ActivityResultLauncher<Intent>? = null    // Переменная для запуска интента
    private val adapter = RecordAdapter()       // Переменная для адаптера, необходим для заполнения RecyclerView
    var pref: SharedPreferences? = null         // Переменная для сохранения баланса при закрытии приложения
    val recordDbManager = RecordDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Money"   // Заголовок

        // Инициализация области памяти для хранения баланса
        pref = getSharedPreferences("TABLE", Context.MODE_PRIVATE)
        // Чтение баланса из памяти
        balance = pref?.getInt("balance", 0)!!
        binding.textMoney.text = balance.toString()

        // Открываем ДМ
        recordDbManager.openDb()
        // Чтение записей из ДБ
        val dataList = recordDbManager.readDbData()
        for (item in dataList){
            var recordFromDb = item
            // Передаем новый элемент в RecyclerView
            adapter.addRecord(recordFromDb)
        }

        // Инициализация RecyclerView
        init()

        // Слушатель нажатий элементов бокового меню
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

            // Закрытие бокового меню
            binding.drawer.closeDrawer(GravityCompat.START)
            true
        }

        // Слушатель нажатий элементов Bottom Menu
        binding.bottomMenu.setOnItemSelectedListener {
            //  Какой элемент выбран
            when(it.itemId){
                // Открытие окна внесения новой записи
                R.id.add -> {
                    // Создание интента на запуск ActivityAddRecord с данными
                    val intent = Intent(this, ActivityAddRecord::class.java)
                    intent.putExtra(Constants.TOTAL_SUM, balance)

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
                balance += record.sum!!

                binding.apply {
                    textMoney.text = balance.toString()   // Обновляем баланс

                    // Передаем новый элемент в RecyclerView
                    adapter.addRecord(record)

                    // Сохраняем в БД
                    recordDbManager.insertToDb(record)
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
                balance = 0
                binding.textMoney.text = balance.toString()   // Обновляем баланс
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

    // Функция для сохранения баланса в памяти
    fun saveData(res: Int){
        val editor = pref?.edit()
        editor?.putInt("balance",res)
        editor?.apply()
    }

    // Функция для удаления баланса из памяти
    fun clearData(res: Int){
        val editor = pref?.edit()
        editor?.clear()
        editor?.apply()
    }

    // Сохраняем баланс при закрытии приложения
    override fun onDestroy() {
        super.onDestroy()
        saveData(balance)
        recordDbManager.closeDb()
    }
}