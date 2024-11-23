package com.example.pmdm_calculadorasalarioneto

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pmdm_calculadorasalarioneto.MainActivity.Companion.DEDUCC_KEY
import com.example.pmdm_calculadorasalarioneto.MainActivity.Companion.IRPF_KEY
import com.example.pmdm_calculadorasalarioneto.MainActivity.Companion.SALN_KEY
import com.example.pmdm_calculadorasalarioneto.MainActivity.Companion.SAL_KEY
import java.util.Locale

class ResultSalaryActivity : AppCompatActivity() {

    private lateinit var tvSalarioBruto:TextView
    private lateinit var tvSalarioNeto:TextView
    private lateinit var tvRetencionIRPF:TextView
    private lateinit var tvDeducciones:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result_salary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Valor Salario Key
        var resultSal = intent.extras?.getDouble(SAL_KEY)?: -1.0
        var resultSalN = intent.extras?.getDouble(SALN_KEY)?: -1.0
        var resultIRPF = intent.extras?.getDouble(IRPF_KEY)?: -1.0
        var deducciones = intent.extras?.getDouble(DEDUCC_KEY)?: -1.0

        initUI(resultSal, resultSalN, resultIRPF, deducciones)
        initComponents()

    }

    private fun initComponents(){
        this.tvDeducciones = findViewById(R.id.tvDeducciones)
        this.tvSalarioNeto = findViewById(R.id.tvSalarioNeto)
        this.tvRetencionIRPF = findViewById(R.id.tvRetencionIRPF)
        this.tvSalarioBruto = findViewById(R.id.tvSalarioBruto)
    }

    private fun initUI (resultSal: Double, resultSalN: Double,
        retencionIRPF:Double, deducciones:Double){
        this.tvSalarioBruto.text = resultSal.toString().toString()
        this.tvSalarioNeto.text = resultSalN.toString()
        this.tvRetencionIRPF.text = retencionIRPF.toString()
        this.tvDeducciones.text = deducciones.toString()
    }




}