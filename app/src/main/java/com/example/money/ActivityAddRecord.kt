package com.example.money

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat

import com.example.money.databinding.ActivityAddRecordBinding
import java.util.*

class ActivityAddRecord : AppCompatActivity() {

    lateinit var binding : ActivityAddRecordBinding

    private val dirInput : Int = 1
    private val dirOutput : Int = 2
    private var direction : Int = dirOutput
    private var category : Int = ItemCategory.Home.Value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Чтение текущего баланса и вывод на экран
        val message = intent.getIntExtra(Constants.TOTAL_SUM, 0)
        binding.textBalance.text = "Текущий баланс: " + message
    }


    // Выбор режима "Доход"
    fun onClickIncome(view: View){
        direction = dirInput;
        category = ItemCategory.Income.Value

        // Замена фонов кнопок Доход и Расход
        binding.btnOutcome.setBackgroundColor(ContextCompat.getColor(this, R.color.empty))
        binding.btnIncome.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_pale))

        binding.btnOutcome.setTextColor(ContextCompat.getColor(this, R.color.grey))
        binding.btnIncome.setTextColor(ContextCompat.getColor(this, R.color.black))

        // Замена размеров текста кнопок Доход и Расход
        binding.btnOutcome.textSize = 14F
        binding.btnIncome.textSize = 16F

        binding.linLayout.visibility = View.GONE
    }

    // Выбор режима "Расход"
    fun onClickOutcome(view: View){
        direction = dirOutput;
        category = ItemCategory.Home.Value

        // Замена фонов кнопок Доход и Расход
        binding.btnOutcome.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_pale))
        binding.btnIncome.setBackgroundColor(ContextCompat.getColor(this, R.color.empty))

        binding.btnOutcome.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.btnIncome.setTextColor(ContextCompat.getColor(this, R.color.grey))

        // Замена размеров текста кнопок Доход и Расход
        binding.btnOutcome.textSize = 16F
        binding.btnIncome.textSize = 14F

        binding.linLayout.visibility = View.VISIBLE
    }

    // Внесение записи
    fun onClickGoMainActivity(view: View){

        // Если нажата кнопка Расход - записываем значение со знаком минус, иначе просто значение
        var data : Int
        if (direction == dirInput) data = binding.editTextSum.text.toString().toInt()
        else                       data = -binding.editTextSum.text.toString().toInt()

        // Передача внесенной записи в основное окно
        intent.putExtra(Constants.SUM, data)
        intent.putExtra(Constants.DESCRIPTION, binding.editTextDescription.text.toString())
        intent.putExtra(Constants.CATEGORY, category)
        setResult(RESULT_OK, intent)
        finish()    // Закрытие окна
    }

    fun onClickHome(view: View){
        category = ItemCategory.Home.Value

        binding.apply {
            imageHome.setImageResource(R.drawable.home_ok)
            imageCar.setImageResource(R.drawable.car)
            imageFood.setImageResource(R.drawable.food)
        }
    }

    fun onClickCar(view: View){
        category = ItemCategory.Car.Value

        binding.apply {
            imageHome.setImageResource(R.drawable.home)
            imageCar.setImageResource(R.drawable.car_ok)
            imageFood.setImageResource(R.drawable.food)
        }
    }

    fun onClickFood(view: View){
        category = ItemCategory.Food.Value

        binding.apply {
            imageHome.setImageResource(R.drawable.home)
            imageCar.setImageResource(R.drawable.car)
            imageFood.setImageResource(R.drawable.food_ok)
        }
    }
}

