package com.example.calculadoraimposto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.math.max


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editSalario = findViewById<EditText>(R.id.editSalario)
        val editGastos= findViewById<EditText>(R.id.editGastos)
        val editDependentes = findViewById<EditText>(R.id.editDependentes)
        val buttonCalcular = findViewById<Button>(R.id.buttonCalcular)
        val textValor = findViewById<TextView>(R.id.textValor)

        buttonCalcular.setOnClickListener{

            try{

                val salario = editSalario.text.toString().toFloatOrNull()
                val gastos = editGastos.text.toString().toFloatOrNull()
                val dependentes = editDependentes.text.toString().toIntOrNull()


                if (salario == null) {
                    Toast.makeText(applicationContext, "Por favor, preencha o campo Salário.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (gastos == null) {
                    Toast.makeText(applicationContext, "Por favor, preencha o campo Gastos.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (dependentes == null) {
                    Toast.makeText(applicationContext, "Por favor, preencha o campo Dependentes.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                if(salario == 0f){
                    Toast.makeText(applicationContext,"Por favor, preencha o salário com um valor maior do que zero",Toast.LENGTH_SHORT).show()
                }

                if (salario < 0 || gastos < 0) {
                    Toast.makeText(applicationContext, "Salário e gastos não podem ser valores negativos.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (dependentes < 0) {
                    Toast.makeText(applicationContext, "O número de dependentes não pode ser negativo.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (gastos > salario) {
                    Toast.makeText(applicationContext, "Os gastos não podem ser maiores que o salário.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val deducaoPorDependente = 189.59f
                val baseCalculo = salario - (dependentes * deducaoPorDependente) - gastos

                val (aliquota, deducao) = when {
                    baseCalculo <= 2420.00f -> 0f to 0f
                    baseCalculo <= 2826.65f -> 7.5f to 182.16f
                    baseCalculo <= 3751.05f -> 15f to 394.16f
                    baseCalculo <= 4664.68f -> 22.5f to 675.49f
                    else -> 27.5f to 908.73f
                }
                val imposto = max((baseCalculo*(aliquota/100))-deducao,0f)
                textValor.text = String.format("R$ %.2f", imposto)
                Toast.makeText(applicationContext,"Imposto de Renda Calculado com Sucesso!",Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(applicationContext,"Falha em Calcular!",Toast.LENGTH_SHORT).show()
            }
        }


    }
}