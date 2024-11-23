package com.example.pmdm_calculadorasalarioneto

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    //Objeto Companion
    companion object{
        const val SALN_KEY = "SALN_RESULT"
        const val SAL_KEY = "SAL_RESULT"
        const val IRPF_KEY = "IRPF_RESULT"
        const val DEDUCC_KEY = "DEDUCC_RESULT"
    }

    //Creamos variable privada
    private lateinit var etSalario:EditText
    private lateinit var etPagas:EditText
    private lateinit var tvEdad:TextView
    private lateinit var btnMenosEdad:FloatingActionButton
    private lateinit var btnMasEdad:FloatingActionButton
    private lateinit var tvGrupoPro:TextView
    private lateinit var btnRestarGrupo:FloatingActionButton
    private lateinit var btnSumarGrupo:FloatingActionButton
    private lateinit var tvHijos:TextView
    private lateinit var btnMenosHijos: FloatingActionButton
    private lateinit var btnMasHijos: FloatingActionButton
    private lateinit var etEstado:EditText
    private lateinit var etDiscap:EditText
    private lateinit var btnCalcular:Button

    //Atributos Logicos
    private var currentAge = 25
    private var currentGroup = 1
    private var currentSons = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        initListeners()
        initUI()
    }

    private fun initUI() {
        this.setAge()
        this.setSons()
        this.setGroup()
    }


    private fun initComponents() {
        this.etSalario = findViewById(R.id.etSalario)
        this.etPagas = findViewById(R.id.etPagas)
        this.etDiscap = findViewById(R.id.etDiscap)
        this.etEstado = findViewById(R.id.etEstado)
        this.tvEdad = findViewById(R.id.tvEdad)
        this.tvHijos = findViewById(R.id.tvHijos)
        this.tvGrupoPro = findViewById(R.id.tvGrupoPro)
        this.btnCalcular = findViewById(R.id.btnCalcular)
        this.btnMasEdad = findViewById(R.id.btnMasEdad)
        this.btnMenosEdad = findViewById(R.id.btnMenosEdad)
        this.btnMasHijos = findViewById(R.id.btnMasHijos)
        this.btnMenosHijos = findViewById(R.id.btnMenosHijos)
        this.btnSumarGrupo = findViewById(R.id.btnSumarGrupo)
        this.btnRestarGrupo = findViewById(R.id.btnRestarGrupo)
    }

    private fun initListeners() {

        this.btnMasHijos.setOnClickListener{
            this.currentSons += 1
            setSons()
        }

        this.btnMenosHijos.setOnClickListener{
            this.currentSons -= 1
            setSons()
        }

        this.btnMasEdad.setOnClickListener{
            this.currentAge += 1
            setAge()
        }

        this.btnMenosEdad.setOnClickListener {
            this.currentAge -= 1
            setAge()
        }

        this.btnSumarGrupo.setOnClickListener {
            this.currentGroup += 1
            setGroup()
        }

        this.btnRestarGrupo.setOnClickListener {
            this.currentGroup -=1
            setGroup()
        }

        this.btnCalcular.setOnClickListener {
            val resultSalBruto= calcularSalarioBruto()
            val resultSalNeto = calcularSalarioNeto()
            val resultIRPF = retencionIRPF()
            val deducciones = deducciones()
            navigateToResult(resultSalBruto, resultSalNeto, resultIRPF, deducciones)
        }

    }

    private fun navigateToResult(resultSal: Double, resultSalN: Double,
                                 resultIRPF:Double, deducciones: Double) {
        val intent = Intent(this, ResultSalaryActivity::class.java)
        intent.putExtra(SAL_KEY, resultSal)
        intent.putExtra(SALN_KEY, resultSalN)
        intent.putExtra(IRPF_KEY, resultIRPF)
        intent.putExtra(DEDUCC_KEY, deducciones)
        this.startActivity(intent)
    }

    private fun calcularSalarioBruto(): Double {
        // Obtenemos los valores de los EditText y los convertimos a Double
        val salario = etSalario.text.toString().toDoubleOrNull() // Convierte el texto a Double, o null si no es un número válido
        val pagas = etPagas.text.toString().toDoubleOrNull() // Lo mismo con el campo de pagas

        // Verificamos si ambos valores son válidos
        if (salario != null && pagas != null) {
            // Realizamos la multiplicación
            return salario * pagas
        } else {
            // Si los valores no son válidos, retornamos 0 o puedes mostrar un mensaje de error
            return 0.0
        }

    }

    private fun calcularSalarioNeto(): Double {
        var salario = etSalario.text.toString().toDoubleOrNull()?: -1.0
        var pagas = etPagas.text.toString().toDoubleOrNull()?: -1.0
        var retencion: Double
        var salarioNeto:Double

        if (salario <= 35000.0){
            retencion = salario*pagas*0.15
            salarioNeto = salario-retencion
        }
        else if (salario >= 35001.0){
            retencion = salario*pagas*0.20
            salarioNeto = salario-retencion
        }
        else {
            error("Error")
        }
        return salarioNeto
    }

    private fun retencionIRPF(): Double {
        var salario = etSalario.text.toString().toDoubleOrNull()?: -1.0
        var pagas = etPagas.text.toString().toDoubleOrNull()?: -1.0
        var retencion: Double


        if (salario <= 35000.0){
            retencion = salario*pagas*0.15
        }
        else if (salario >= 35001.0){
            retencion = salario*pagas*0.20

        }
        else {
            error("Error")
        }
        return retencion
    }

    private fun deducciones(): Double{
        var estado = etEstado.text.toString()
        var deduccion: Double
        val isCasado=true

        if (!isCasado){
            deduccion=-1000.0
        }
        else if(isCasado){
            deduccion= 1000.0
        } else {
            error("Error")
        }

        return deduccion
    }


    private fun setGroup() {
        this.tvGrupoPro.text = this.currentGroup.toString()
    }

    private fun setAge() {
        this.tvEdad.text = this.currentAge.toString()
    }

    private fun setSons() {
        this.tvHijos.text = this.currentSons.toString()
    }
}