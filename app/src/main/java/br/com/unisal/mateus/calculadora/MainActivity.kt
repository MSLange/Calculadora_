package br.com.unisal.mateus.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.unisal.mateus.calculadora.ui.theme.CalculadoraTheme
import br.com.unisal.mateus.calculadora.ui.theme.TemaDoAPP
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan


class MainActivity : ComponentActivity() {

    var visor by mutableStateOf("0")
    val pilhaOperador = mutableListOf<String>()
    val pilhaOperando = mutableListOf<String>()
    var aguardandoOperando = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var temaSelecionado by remember {
                mutableStateOf(TemaDoAPP.CLARO)
            }

            CalculadoraTheme(temaSelecionado) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        SeletorDeTemas(
                            temaSelecionado = temaSelecionado,
                            onTemaselecionado = {
                                temaSelecionado = it
                            }
                        )

                        criaCalculadora(visor)
                    }
                }
            }
        }
    }


    @Composable
    fun criaCalculadora(visor: String) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                fontSize = 32.sp,
                text = visor
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("%", BotaoOperacao.PERCENTUAL)
                criaBotaoPequeno("/", BotaoOperacao.DIVISAO)
                criaBotaoPequeno("*", BotaoOperacao.MULTIPLICACAO)
                criaBotaoPequeno("-", BotaoOperacao.SUBTRACAO)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("Sin", BotaoOperacao.SIN)
                criaBotaoPequeno("Cos", BotaoOperacao.COS)
                criaBotaoPequeno("Tan", BotaoOperacao.TAN)
                criaBotaoPequeno("Pi", BotaoOperacao.PI)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("Sqrt", BotaoOperacao.SQRT)
                criaBotaoPequeno("^", BotaoOperacao.POTENCIA)
                criaBotaoPequeno("!", BotaoOperacao.FATORIAL)
                criaBotaoPequeno("Inv", BotaoOperacao.INVERSO)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("7", BotaoOperacao.SETE)
                criaBotaoPequeno("8", BotaoOperacao.OITO)
                criaBotaoPequeno("9", BotaoOperacao.NOVE)
                criaBotaoPequeno("+", BotaoOperacao.SOMA)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("4", BotaoOperacao.QUATRO)
                criaBotaoPequeno("5", BotaoOperacao.CINCO)
                criaBotaoPequeno("6", BotaoOperacao.SEIS)
                criaBotaoPequeno(".", BotaoOperacao.VIRGULA)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("1", BotaoOperacao.UM)
                criaBotaoPequeno("2", BotaoOperacao.DOIS)
                criaBotaoPequeno("3", BotaoOperacao.TRES)
                criaBotaoPequeno("=", BotaoOperacao.IGUALDADE)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("+/-", BotaoOperacao.INVERTER_SINAL)
                criaBotaoPequeno("0", BotaoOperacao.ZERO)
                criaBotaoPequeno("C", BotaoOperacao.LIMPAR)
                criaBotaoPequeno("<-", BotaoOperacao.APAGAR)
            }
        }
    }


    @Composable
    fun criaBotaoPequeno(
        texto: String,
        identificador: BotaoOperacao
    ) {

        Button(
            modifier = Modifier
                .width(80.dp)
                .height(50.dp),

            onClick = {

                if (identificador.ordinal <= BotaoOperacao.VIRGULA.ordinal) {
                    numPress(identificador)
                } else {
                    opPress(identificador)
                }
            },

            colors = if (identificador == BotaoOperacao.IGUALDADE) {

                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )

            } else {

                ButtonDefaults.buttonColors()

            }
        ) {
            Text(texto)
        }
    }


    fun numPress(identificador: BotaoOperacao) {

        if (aguardandoOperando) {
            visor = "0"
            aguardandoOperando = false
        }

        if ((visor == "0") &&
            (identificador.name == BotaoOperacao.ZERO.name)
        ) {
            return
        }

        if ((visor.contains(",")) &&
            (identificador.name == BotaoOperacao.VIRGULA.name)
        ) {
            return
        }

        var tmp = identificador.name

        if (tmp == BotaoOperacao.VIRGULA.name) {
            tmp = ","
        } else {
            tmp = identificador.ordinal.toString()
        }

        if (visor.length == 1 && visor == "0" && identificador != BotaoOperacao.VIRGULA) {
            visor = tmp
        } else {
            visor += tmp
        }

    }


    fun opPress(identificador: BotaoOperacao) {

        // Limpar
        if (identificador == BotaoOperacao.LIMPAR) {

            pilhaOperador.clear()
            pilhaOperando.clear()

            aguardandoOperando = false
            visor = "0"

            return
        }


        // Apagar último número
        if (identificador == BotaoOperacao.APAGAR) {

            if (visor.length > 1) {
                visor = visor.substring(0, visor.length - 1)
            } else {
                visor = "0"
            }

            return
        }


        // Inverter sinal
        if (identificador == BotaoOperacao.INVERTER_SINAL) {

            if (visor != "0" && visor != "Erro") {

                val valor = visor
                    .replace(",", ".")
                    .toFloat()

                visor = (-valor).toString().replace(".", ",")
            }

            return
        }


        // Constante Pi
        if (identificador == BotaoOperacao.PI) {

            visor = "3,14"
            aguardandoOperando = false

            return
        }


        // Operações unárias
        if (
            identificador == BotaoOperacao.SIN ||
            identificador == BotaoOperacao.COS ||
            identificador == BotaoOperacao.TAN ||
            identificador == BotaoOperacao.SQRT ||
            identificador == BotaoOperacao.FATORIAL ||
            identificador == BotaoOperacao.INVERSO
        ) {

            operacaoUnaria(identificador)

            return
        }


        // Igualdade
        if (identificador == BotaoOperacao.IGUALDADE) {

            igualdade()

            return
        }


        // Se apertar outro operador enquanto está
        // aguardando o próximo número, troca o operador
        if (aguardandoOperando) {

            if (pilhaOperador.isNotEmpty()) {
                pilhaOperador[pilhaOperador.lastIndex] = identificador.name
            }

            return
        }


        // Primeira operação
        if (pilhaOperador.isEmpty()) {

            pilhaOperador.add(identificador.name)
            pilhaOperando.add(visor)

        } else {

            // Executar a operação anterior
            igualdade()

            // Guardar a nova operação
            pilhaOperador.add(identificador.name)
            pilhaOperando.add(visor)
        }

        aguardandoOperando = true
    }


    fun operacaoUnaria(identificador: BotaoOperacao) {

        if (visor == "Erro") {
            return
        }

        try {

            val valor = visor
                .replace(",", ".")
                .toFloat()


            // Seno
            if (identificador == BotaoOperacao.SIN) {

                visor = sin(valor)
                    .toString()
                    .replace(".", ",")

            }


            // Cosseno
            else if (identificador == BotaoOperacao.COS) {

                visor = cos(valor)
                    .toString()
                    .replace(".", ",")

            }


            // Tangente
            else if (identificador == BotaoOperacao.TAN) {

                visor = tan(valor)
                    .toString()
                    .replace(".", ",")

            }


            // Raiz quadrada
            else if (identificador == BotaoOperacao.SQRT) {

                if (valor < 0) {

                    visor = "Erro"

                } else {

                    visor = sqrt(valor)
                        .toString()
                        .replace(".", ",")
                }
            }


            // Inverso
            else if (identificador == BotaoOperacao.INVERSO) {

                if (valor == 0f) {

                    visor = "Erro"

                } else {

                    visor = (1 / valor)
                        .toString()
                        .replace(".", ",")
                }
            }


            // Fatorial
            else if (identificador == BotaoOperacao.FATORIAL) {

                if (
                    valor < 0 ||
                    valor % 1 != 0f ||
                    valor > 170
                ) {

                    visor = "Erro"

                } else {

                    var resultado = 1.0
                    var i = 1

                    while (i <= valor.toInt()) {

                        resultado *= i
                        i++
                    }

                    visor = resultado
                        .toString()
                        .replace(".", ",")
                }
            }

            aguardandoOperando = true

        } catch (e: Exception) {

            visor = "Erro"
            aguardandoOperando = true
        }
    }


    fun igualdade() {

        if (
            pilhaOperador.isEmpty() ||
            pilhaOperando.isEmpty()
        ) {
            return
        }

        if (visor == "Erro") {
            return
        }

        try {

            val operador = pilhaOperador
                .removeAt(pilhaOperador.lastIndex)

            val operando = pilhaOperando
                .removeAt(pilhaOperando.lastIndex)
                .replace(",", ".")
                .toFloat()

            val aux = visor
                .replace(",", ".")
                .toFloat()


            // Soma
            if (operador == BotaoOperacao.SOMA.name) {

                visor = (operando + aux)
                    .toString()
                    .replace(".", ",")

            }


            // Subtração
            else if (operador == BotaoOperacao.SUBTRACAO.name) {

                visor = (operando - aux)
                    .toString()
                    .replace(".", ",")

            }


            // Multiplicação
            else if (operador == BotaoOperacao.MULTIPLICACAO.name) {

                visor = (operando * aux)
                    .toString()
                    .replace(".", ",")

            }


            // Divisão
            else if (operador == BotaoOperacao.DIVISAO.name) {

                if (aux == 0f) {

                    visor = "Erro"

                } else {

                    visor = (operando / aux)
                        .toString()
                        .replace(".", ",")
                }
            }


            // Percentual
            else if (operador == BotaoOperacao.PERCENTUAL.name) {

                visor = ((operando * aux) / 100)
                    .toString()
                    .replace(".", ",")

            }


            // Potenciação
            else if (operador == BotaoOperacao.POTENCIA.name) {

                visor = Math.pow(
                    operando.toDouble(),
                    aux.toDouble()
                )
                    .toFloat()
                    .toString()
                    .replace(".", ",")
            }

            aguardandoOperando = true

        } catch (e: Exception) {

            visor = "Erro"
            aguardandoOperando = true
        }
    }


    enum class BotaoOperacao {

        ZERO,
        UM,
        DOIS,
        TRES,
        QUATRO,
        CINCO,
        SEIS,
        SETE,
        OITO,
        NOVE,

        VIRGULA,

        SOMA,
        SUBTRACAO,
        MULTIPLICACAO,
        DIVISAO,
        PERCENTUAL,
        POTENCIA,

        SIN,
        COS,
        TAN,
        FATORIAL,
        PI,
        INVERSO,
        SQRT,

        IGUALDADE,
        LIMPAR,
        APAGAR,
        INVERTER_SINAL
    }


    @Composable
    fun SeletorDeTemas(
        temaSelecionado: TemaDoAPP,
        onTemaselecionado: (TemaDoAPP) -> Unit
    ) {

        var expandido by remember {
            mutableStateOf(false)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {

            OutlinedButton(
                onClick = {
                    expandido = true
                }
            ) {

                Text(
                    when (temaSelecionado) {

                        TemaDoAPP.CLARO -> "Tema Claro"

                        TemaDoAPP.ESCURO -> "Tema Escuro"

                        TemaDoAPP.DINAMICO -> "Tema Dinamico"

                        TemaDoAPP.MINIMALISTA -> "Tema Minimalista"
                    }
                )
            }


            DropdownMenu(
                expanded = expandido,
                onDismissRequest = {
                    expandido = false
                }
            ) {

                DropdownMenuItem(
                    text = {
                        Text("Tema Claro")
                    },
                    onClick = {
                        onTemaselecionado(TemaDoAPP.CLARO)
                        expandido = false
                    }
                )


                DropdownMenuItem(
                    text = {
                        Text("Tema Escuro")
                    },
                    onClick = {
                        onTemaselecionado(TemaDoAPP.ESCURO)
                        expandido = false
                    }
                )


                DropdownMenuItem(
                    text = {
                        Text("Tema Dinâmico")
                    },
                    onClick = {
                        onTemaselecionado(TemaDoAPP.DINAMICO)
                        expandido = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Tema Minimalista") },
                    onClick = {
                        onTemaselecionado(TemaDoAPP.MINIMALISTA)
                        expandido = false
                    }
                )
            }
        }
    }
}