package com.example.exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exam.databinding.ActivityMain2Binding
import com.example.exam.databinding.ActivityMainBinding
import kotlin.concurrent.thread

private lateinit var bind: ActivityMain2Binding//Переменная для биндинга
lateinit var db1 : DB_work//Переменная для БД
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(bind.root)

        db1 = DB_work.getDb(this)//Инициализация базы данных

        //Обработка высчитывания суммы
        bind.button3.setOnClickListener {
            thread {
                if (bind.editTextText.text.toString() == "") {
                    this.runOnUiThread {
                        bind.textCost.text = "Не найдено"
                    }
                } else {
                    val data = db1.getDao().returnClient(bind.editTextText.text.toString())
                    var total_cost = 0
                    for (i in data) {
                        total_cost += i.cost
                    }
                    this.runOnUiThread {
                        bind.textCost.text = total_cost.toString()
                    }
                }
            }
        }
        //Переход на другое активити
        bind.goToBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}