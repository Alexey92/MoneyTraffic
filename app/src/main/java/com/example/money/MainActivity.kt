package com.example.money

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.example.money.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var bindingClass : ActivityMainBinding
    private var total_sum : Int = 0
    private var launcher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        // Возвращение в основное окно из новых окон
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->

            if(result.resultCode == RESULT_OK){
                var record = Record()

                // Чтение суммы
                record.sum = result.data?.getIntExtra(Constants.SUM, 0)

                // Прибавляем сумму к балансу
                total_sum += record.sum!!

                // Чтение комментария
                record.description = result.data?.getStringExtra(Constants.DESCRIPTION).toString()

                bindingClass.apply {
                    textMoney.text = total_sum.toString()   // Обновляем баланс

                    // Обновляем записи в логе
                    textLastOperation2.text = bindingClass.textLastOperation1.text
                    textLastOperation1.text = bindingClass.textLastOperation.text
                    textLastOperation.text = record.createLog()
                }
            }
        }

    }

    // Открытие окна внесения новой записи
    fun onClickGoAddRecord(view: View){
        // Создание интента на запуск ActivityAddRecord с данными
        val intent = Intent(this, ActivityAddRecord::class.java)
        intent.putExtra(Constants.TOTAL_SUM, total_sum)

        // Запуск интента
        launcher?.launch(intent)
    }

    // Скрыть/посмотреть историю записей
    fun onClickViewRecords(view: View){
        if (bindingClass.textLastOperation.visibility == View.GONE) {
            Log.d("MyLog", "enable log")

            bindingClass.textLastOperation.visibility = View.VISIBLE
            bindingClass.textLastOperation1.visibility = View.VISIBLE
            bindingClass.textLastOperation2.visibility = View.VISIBLE
        }
        else{
            Log.d("MyLog", "disable log")

            bindingClass.textLastOperation.visibility = View.GONE
            bindingClass.textLastOperation1.visibility = View.GONE
            bindingClass.textLastOperation2.visibility = View.GONE
        }
    }
}