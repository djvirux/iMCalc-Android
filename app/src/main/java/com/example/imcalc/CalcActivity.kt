package com.example.imcalc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class CalcActivity : AppCompatActivity() {

    //Variables para almacenar los datos actuales de peso, edad y altura
    private var currentWeight = 37
    private var currentAge = 18
    private var currentHeight = 120

    //Variables para tratar los elementos en pantalla, textvies, botones y demas.
    private lateinit var tvHeightValue: AppCompatTextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubtractWeight: FloatingActionButton
    private lateinit var btnAddWeight: FloatingActionButton
    private lateinit var tvWeightValue: AppCompatTextView
    private lateinit var tvAgeValue: AppCompatTextView
    private lateinit var btnSubtractAge: FloatingActionButton
    private lateinit var btnAddAge: FloatingActionButton
    private lateinit var tvResultStatus: AppCompatTextView
    private lateinit var tvResultIMC: AppCompatTextView
    private lateinit var tvResultInfo: AppCompatTextView

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)
        //enlazamos los componentes
        initComponents()
        //iniciamos los listeners
        initListeners()
        //asignamos los valores a los distintos Textviews
        setValues()
        //mostramos los resultados del IMC
        printResultIMC()
    }

    private fun initComponents() {

        //Enlazamos con los elementos del layout.
        tvHeightValue = findViewById(R.id.tvHeightValue)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        btnAddWeight = findViewById(R.id.btnAddWeight)
        tvWeightValue = findViewById(R.id.tvWeightValue)
        tvAgeValue = findViewById(R.id.tvAgeValue)
        btnSubtractAge = findViewById(R.id.btnSubtractAge)
        btnAddAge = findViewById(R.id.btnAddAge)
        tvResultIMC = findViewById(R.id.tvResultIMC)
        tvResultInfo = findViewById(R.id.tvResultInfo)
        tvResultStatus = findViewById(R.id.tvResultStatus)
    }

    @SuppressLint("SetTextI18n")
    private fun initListeners() {

        /*cada elemento tiene su listener, al final de cada listener añadimos la llamada a
        printResultIMC para mostrar la actualizacion de los datos en los distintos elementos
         */
        rsHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            currentHeight = df.format(value).toInt()
            tvHeightValue.text = "$currentHeight cm"
            printResultIMC()
        }

        btnAddWeight.setOnClickListener {
            currentWeight++
            setValues()
            printResultIMC()
        }

        btnSubtractWeight.setOnClickListener {
            if (currentWeight>20){
                currentWeight--
                setValues()
                printResultIMC()
            }
        }

        btnAddAge.setOnClickListener {
            currentAge++
            setValues()
            printResultIMC()
        }

        btnSubtractAge.setOnClickListener {
            if (currentAge>6){
                currentAge--
                setValues()
                printResultIMC()
            }
        }

    }

    /*funcion para mostrar los resultados de la funcion calculateIMC y mostrar los resultados en los
    distintos elementos.
    */
    private fun printResultIMC(){
        when (calculateIMC() - 1) {
            in 0.01..18.50 -> { //Underweight
                tvResultStatus.text = getString(R.string.underweight)
                tvResultIMC.text = calculateIMC().toString()
                tvResultInfo.text = getString(R.string.underweight_description)
                tvResultStatus.setTextColor(ContextCompat.getColor(this, R.color.bajopeso))
            }

            in 18.51..24.99 -> { //Normal
                tvResultStatus.text = getString(R.string.normal)
                tvResultIMC.text = calculateIMC().toString()
                tvResultInfo.text = getString(R.string.normal_description)
                tvResultStatus.setTextColor(ContextCompat.getColor(this, R.color.normal))
            }

            in 25.00..29.99 -> { //Overweight
                tvResultStatus.text = getString(R.string.overweight)
                tvResultIMC.text = calculateIMC().toString()
                tvResultInfo.text = getString(R.string.overweight_description)
                tvResultStatus.setTextColor(ContextCompat.getColor(this, R.color.sobrepeso))
            }

            in 30.00..40.99 -> { //Obese
                tvResultStatus.text = getString(R.string.obese)
                tvResultIMC.text = calculateIMC().toString()
                tvResultInfo.text = getString(R.string.obese_description)
                tvResultStatus.setTextColor(ContextCompat.getColor(this, R.color.obeso))
            }

            in 41.00..90.00 -> { //Morbidly Obese
                tvResultStatus.text = getString(R.string.morbidly_obese)
                tvResultIMC.text = calculateIMC().toString()
                tvResultInfo.text = getString(R.string.morbidly_obese_description)
                tvResultStatus.setTextColor(ContextCompat.getColor(this, R.color.morbido))
            }

            else -> {
                tvResultStatus.text = getString(R.string.error)
                tvResultIMC.text = getString(R.string.error)
                tvResultInfo.text = getString(R.string.error)
            }
        }
    }

    //funcion para calcular el IMC con los datos proporcionados por el usuario
    private fun calculateIMC(): Double {
        val df = DecimalFormat("#.##")
        val imc = currentWeight / (currentHeight.toDouble() / 100 * currentHeight.toDouble() / 100)
        return df.format(imc).toDouble()
    }

    /* asignamos los valores de currentWeight y de currentAge a sus respectivos textviews para mostrar
    los datos por pantalla.
    */
    @SuppressLint("SetTextI18n")//No es necesaria traduccion
    private fun setValues(){
        tvHeightValue.text = "120 cm"
        tvWeightValue.text = currentWeight.toString()
        tvAgeValue.text = currentAge.toString()
    }
}