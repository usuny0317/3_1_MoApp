package com.moworkspace.myfinal


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import com.moworkspace.myfinal.FragmentA
import com.moworkspace.myfinal.FragmentB
import com.moworkspace.myfinal.FragmentC
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var logOut: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var email = intent.getStringExtra("email")


        textView.text= email + "님 환영합니다."

        with(supportFragmentManager.beginTransaction()){
            val fragment1= FragmentA()
            replace(R.id.container,fragment1)
            commit()
        }
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.tab1 ->{
                    Toast.makeText(applicationContext, "달력화면", Toast.LENGTH_SHORT).show()
                    with(supportFragmentManager.beginTransaction()){
                        val fragment1= FragmentA()
                        replace(R.id.container,fragment1)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab2 ->{
                    Toast.makeText(applicationContext, "기록화면", Toast.LENGTH_SHORT).show()
                    with(supportFragmentManager.beginTransaction()){
                        val fragment2= FragmentB()
                        replace(R.id.container,fragment2)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tab3 ->{
                    Toast.makeText(applicationContext, "설정화면", Toast.LENGTH_SHORT).show()
                    with(supportFragmentManager.beginTransaction()){
                        val fragment3= FragmentC()
                        replace(R.id.container,fragment3)
                        commit()
                    }
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }
}