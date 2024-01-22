package com.example.exam
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam.databinding.ActivityMainBinding
import kotlin.concurrent.thread
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView


private lateinit var bind: ActivityMainBinding//Переменная для биндинга
lateinit var db : DB_work//Переменная для БД
var work : ArrayList<RecycleItem> = ArrayList()//Массив для заполнения RecycleView

class MainActivity : AppCompatActivity(), RecycleAdapter.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        work.clear()
        db = DB_work.getDb(this)//Инициализация базы данных

        bind.btn1.setOnClickListener{//Обработка нажатия кнопки добавления записи(Диалоговое окно)
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_window, null)
            val myDialog = Dialog(this)
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val btnLike = dialogBinding.findViewById<Button>(R.id.btn2)
            btnLike.text = "Добавить"
            val txtName = dialogBinding.findViewById<TextView>(R.id.nameText)
            val txtCost = dialogBinding.findViewById<TextView>(R.id.costText)
            val txtClient = dialogBinding.findViewById<TextView>(R.id.clientText)

            btnLike.setOnClickListener{
                thread {
                    db.getDao().insertItem(DataClass(null, txtName.text.toString(), txtCost.text.toString().toInt(),txtClient.text.toString() ))
                    myDialog.dismiss()
                    update()
                }
            }
        }

        //Переход на другое активити
        bind.goToTotal.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }


        //Обработчик нажатия поиска
        bind.btnSearch.setOnClickListener{
            if (bind.textSearch.text.toString() == ""){
                update()
            }
            else {
                thread{
                    work.clear()
                    val data = db.getDao().returnClient(bind.textSearch.text.toString())
                    for (i in data) {
                        work.add(RecycleItem(i.id!!, i.name, i.cost, i.client))
                    }
                    this.runOnUiThread {
                        val rView: RecyclerView? = findViewById(R.id.rw)
                        rView?.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
        //Берём данные из бд и отправляем заполнять RecycleView
        thread {
            val data = db.getDao().returnAll()
            for (i in data) {
                work.add(RecycleItem(i.id!!, i.name, i.cost, i.client))
            }

            this.runOnUiThread {
                val recyclerView: RecyclerView? = findViewById(R.id.rw)
                recyclerView!!.layoutManager = GridLayoutManager(this, 1)
                recyclerView.adapter = RecycleAdapter(work, this)
            }

        }


    }



    //Функция изменения данных о записи
    override fun OnClick(recItem: RecycleItem) {
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_window, null)
            val myDialog = Dialog(this)
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val btnLike = dialogBinding.findViewById<Button>(R.id.btn2)
            btnLike.text = "Изменить"
            val txtName = dialogBinding.findViewById<TextView>(R.id.nameText)
            val txtCost = dialogBinding.findViewById<TextView>(R.id.costText)
            val txtClient = dialogBinding.findViewById<TextView>(R.id.clientText)
        thread{
            val item = db.getDao().returnItem(recItem.id)
            txtName.text = item.name
            txtCost.text = item.cost.toString()
            txtClient.text = item.client
        }
        btnLike.setOnClickListener{
            thread {
                db.getDao().updateNameItem(recItem.id, txtName.text.toString())
                db.getDao().updateCostItem(recItem.id, txtCost.text.toString().toInt())
                db.getDao().updateClientItem(recItem.id, txtClient.text.toString())
                myDialog.dismiss()
                update()
            }
        }
    }

    override fun OnClickDelete(recItem: RecycleItem) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_window1, null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
        val btnLike = dialogBinding.findViewById<Button>(R.id.btn_fav)
        val btnCancel = dialogBinding.findViewById<Button>(R.id.btn_cancel)
        btnLike.setOnClickListener{
            thread {
                db.getDao().delete(recItem.id)
                myDialog.dismiss()
                update()
            }
        }
        btnCancel.setOnClickListener{
            myDialog.dismiss()
        }
    }

    //Функция обновления данных
     fun update() {
         thread{
             work.clear()
             val data = db.getDao().returnAll()
             for (i in data) {
                 work.add(RecycleItem(i.id!!, i.name, i.cost, i.client))
             }
             this.runOnUiThread {
                 val rView: RecyclerView? = findViewById(R.id.rw)
                 rView?.adapter?.notifyDataSetChanged()
             }
         }


     }

}