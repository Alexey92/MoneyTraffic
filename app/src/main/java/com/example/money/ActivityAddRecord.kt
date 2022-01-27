package com.example.money

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat

import com.example.money.databinding.ActivityAddRecordBinding

class ActivityAddRecord : AppCompatActivity() {

    lateinit var bindingClass : ActivityAddRecordBinding

    private val dirInput : Int = 1
    private val dirOutput : Int = 2
    private var direction : Int = dirOutput

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        // Чтение текущего баланса и вывод на экран
        val message = intent.getIntExtra(Constants.TOTAL_SUM, 0)
        bindingClass.textBalance.text = "Текущий баланс: " + message
    }


    // Выбор режима "Доход"
    fun onClickIncome(view: View){
        direction = dirInput;

        // Замена фонов кнопок Доход и Расход
        bindingClass.btnOutcome.setBackgroundColor(ContextCompat.getColor(this, R.color.empty))
        bindingClass.btnIncome.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_pale))

        // Замена размеров текста кнопок Доход и Расход
        bindingClass.btnOutcome.textSize = 14F
        bindingClass.btnIncome.textSize = 16F

    }

    // Выбор режима "Доход"
    fun onClickOutcome(view: View){
        direction = dirOutput;

        // Замена фонов кнопок Доход и Расход
        bindingClass.btnOutcome.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_pale))
        bindingClass.btnIncome.setBackgroundColor(ContextCompat.getColor(this, R.color.empty))

        // Замена размеров текста кнопок Доход и Расход
        bindingClass.btnOutcome.textSize = 16F
        bindingClass.btnIncome.textSize = 14F
    }

    // Внесение записи
    fun onClickGoMainActivity(view: View){

        // Если нажата кнопка Расход - записываем значение со знаком минус, иначе просто значение
        var data : Int
        if (direction == dirInput) data = bindingClass.editTextSum.text.toString().toInt()
        else                       data = -bindingClass.editTextSum.text.toString().toInt()

        // Передача внесенной записи в основное окно
        intent.putExtra(Constants.SUM, data)
        intent.putExtra(Constants.DESCRIPTION, bindingClass.editTextDescription.text.toString())
        setResult(RESULT_OK, intent)
        finish()    // Закрытие окна
    }
}

