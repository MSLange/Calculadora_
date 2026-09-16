package br.com.unisal.mateus.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Surface
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

            // Tema inicialmente utilizado pela aplicação
            var temaSelecionado by remember {
                mutableStateOf(TemaDoAPP.CLARO)
            }

            // Aplica o tema selecionado à calculadora.
            // Além dos temas originais, foi adicionado o tema Terminal
            CalculadoraTheme(temaSelecionado) {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->

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

            // Visor personalizado da calculadora.
            // As cores, tipografia, borda e formato acompanham o tema selecionado
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),

                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium,

                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.primary
                )
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.displayLarge,
                    text = visor
                )
            }


            // Operações percentuais e operadores binários
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("%", BotaoOperacao.PERCENTUAL)
                criaBotaoPequeno("/", BotaoOperacao.DIVISAO)
                criaBotaoPequeno("*", BotaoOperacao.MULTIPLICACAO)
                criaBotaoPequeno("-", BotaoOperacao.SUBTRACAO)
            }

            Spacer(modifier = Modifier.height(8.dp))


            // Operações trigonométricas e constante Pi
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("Sin", BotaoOperacao.SIN)
                criaBotaoPequeno("Cos", BotaoOperacao.COS)
                criaBotaoPequeno("Tan", BotaoOperacao.TAN)
                criaBotaoPequeno("Pi", BotaoOperacao.PI)
            }

            Spacer(modifier = Modifier.height(8.dp))


            // Raiz quadrada, potenciação, fatorial e inverso
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

        // Define a hierarquia visual dos botões

        val coresBotao = when (identificador) {

            BotaoOperacao.IGUALDADE ->
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

            BotaoOperacao.LIMPAR ->
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )

            BotaoOperacao.SOMA,
            BotaoOperacao.SUBTRACAO,
            BotaoOperacao.MULTIPLICACAO,
            BotaoOperacao.DIVISAO,
            BotaoOperacao.POTENCIA ->
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )

            BotaoOperacao.SIN,
            BotaoOperacao.COS,
            BotaoOperacao.TAN,
            BotaoOperacao.SQRT,
            BotaoOperacao.FATORIAL,
            BotaoOperacao.INVERSO,
            BotaoOperacao.PI,
            BotaoOperacao.PERCENTUAL ->
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )

            else ->
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
        }


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

            colors = coresBotao,

            // O formato do botão acompanha o tema selecionado
            shape = MaterialTheme.shapes.medium

        ) {

            Text(
                text = texto,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }


    fun numPress(identificador: BotaoOperacao) {

        if (aguardandoOperando) {
            visor = "0"
            aguardandoOperando = false
        }

        if (visor == "Erro") {
            visor = "0"
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

        if (visor.length == 1 &&
            visor == "0" &&
            identificador != BotaoOperacao.VIRGULA
        ) {
            visor = tmp
        } else {
            visor += tmp
        }
    }


    fun opPress(identificador: BotaoOperacao) {

        if (identificador == BotaoOperacao.LIMPAR) {
            pilhaOperador.clear()
            pilhaOperando.clear()
            aguardandoOperando = false
            visor = "0"
            return
        }


        // Remove o último caractere digitado no visor
        if (identificador == BotaoOperacao.APAGAR) {

            if (visor.length > 1 && visor != "Erro") {
                visor = visor.substring(0, visor.length - 1)
            } else {
                visor = "0"
            }

            return
        }


        // Inverte o sinal do valor apresentado no visor
        if (identificador == BotaoOperacao.INVERTER_SINAL) {

            if (visor != "0" && visor != "Erro") {
                val valor = visor.replace(",", ".").toDouble()
                visor = formataResultado(-valor)
            }

            return
        }


        // Insere no visor o valor aproximado da constante Pi
        if (identificador == BotaoOperacao.PI) {
            visor = "3,14"
            aguardandoOperando = false
            return
        }


        // As operações unárias atuam diretamente sobre o
        // valor apresentado atualmente no visor
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


        if (identificador == BotaoOperacao.IGUALDADE) {
            igualdade()
            return
        }


        if (visor == "Erro") {
            return
        }


        if (aguardandoOperando && pilhaOperador.isNotEmpty()) {
            pilhaOperador[pilhaOperador.lastIndex] = identificador.name
            return
        }


        if (pilhaOperador.isNotEmpty()) {
            igualdade()
        }


        if (visor != "Erro") {
            pilhaOperando.add(visor)
            pilhaOperador.add(identificador.name)
            aguardandoOperando = true
        }
    }

    fun formataResultado(valor: Double): String {

        if (valor % 1 == 0.0) {
            return valor.toLong().toString()
        }

        return valor.toString().replace(".", ",")
    }


    // Executa as operações unárias utilizando diretamente
    // o valor apresentado atualmente no visor
    fun operacaoUnaria(identificador: BotaoOperacao) {

        if (visor == "Erro") return

        try {

            val valor = visor.replace(",", ".").toFloat()


            // Calcula o seno do valor informado
            if (identificador == BotaoOperacao.SIN) {

                visor = sin(valor)
                    .toString()
                    .replace(".", ",")
            }


            // Calcula o cosseno do valor informado
            else if (identificador == BotaoOperacao.COS) {

                visor = cos(valor)
                    .toString()
                    .replace(".", ",")
            }


            // Calcula a tangente do valor informado
            else if (identificador == BotaoOperacao.TAN) {

                visor = tan(valor)
                    .toString()
                    .replace(".", ",")
            }


            // Calcula a raiz quadrada do valor
            // Não é permitido calcular raiz de número negativo
            else if (identificador == BotaoOperacao.SQRT) {

                if (valor < 0) {
                    visor = "Erro"
                } else {

                    visor = sqrt(valor)
                        .toString()
                        .replace(".", ",")
                }
            }


            // Calcula o inverso do número, equivalente a 1 / x
            // O inverso de zero é tratado como uma operação inválida
            else if (identificador == BotaoOperacao.INVERSO) {

                if (valor == 0f) {
                    visor = "Erro"
                } else {

                    visor = (1 / valor)
                        .toString()
                        .replace(".", ",")
                }
            }


            // Calcula o fatorial através de multiplicações sucessivas
            // O fatorial é aceito somente para números inteiros
            // maiores ou iguais a zero
            else if (identificador == BotaoOperacao.FATORIAL) {

                if (valor < 0 || valor % 1 != 0f) {

                    visor = "Erro"

                } else {

                    var resultado = 1.0
                    var i = 1

                    while (i <= valor.toInt()) {
                        resultado = resultado * i
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

        if (pilhaOperador.isEmpty() || pilhaOperando.isEmpty()) {
            return
        }


        if (visor == "Erro") {
            return
        }


        try {

            val operador =
                pilhaOperador.removeAt(pilhaOperador.lastIndex)


            val operando = pilhaOperando
                .removeAt(pilhaOperando.lastIndex)
                .replace(",", ".")
                .toDouble()


            val aux = visor
                .replace(",", ".")
                .toDouble()


            var resultado = 0.0


            // Soma
            if (operador == BotaoOperacao.SOMA.name) {
                resultado = operando + aux
            }


            // Subtração
            else if (operador == BotaoOperacao.SUBTRACAO.name) {
                resultado = operando - aux
            }


            // Multiplicação
            else if (operador == BotaoOperacao.MULTIPLICACAO.name) {
                resultado = operando * aux
            }


            // Divisão com tratamento para divisão por zero
            else if (operador == BotaoOperacao.DIVISAO.name) {

                if (aux == 0.0) {
                    visor = "Erro"
                    aguardandoOperando = true
                    return
                }

                resultado = operando / aux
            }


            // Calcula o percentual utilizando os dois operandos
            else if (operador == BotaoOperacao.PERCENTUAL.name) {
                resultado = (operando * aux) / 100
            }


            // Potenciação:
            // o primeiro operando é elevado ao segundo operando
            else if (operador == BotaoOperacao.POTENCIA.name) {
                resultado = Math.pow(operando, aux)
            }


            visor = formataResultado(resultado)
            aguardandoOperando = true

        } catch (e: Exception) {

            // Caso ocorra algum problema durante o cálculo apresenta erro
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


        // Operações binárias
        SOMA,
        SUBTRACAO,
        MULTIPLICACAO,
        DIVISAO,
        PERCENTUAL,
        POTENCIA,


        // Operações matemáticas unárias e constante
        SIN,
        COS,
        TAN,
        FATORIAL,
        PI,
        INVERSO,
        SQRT,


        // Controles da calculadora
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
                },

                shape = MaterialTheme.shapes.medium
            ) {

                Text(
                    when (temaSelecionado) {
                        TemaDoAPP.CLARO -> "Tema Claro"
                        TemaDoAPP.ESCURO -> "Tema Escuro"
                        TemaDoAPP.DINAMICO -> "Tema Dinâmico"
                        TemaDoAPP.TERMINAL -> "Tema Terminal"
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
                    text = {
                        Text("Tema Terminal")
                    },

                    onClick = {
                        onTemaselecionado(TemaDoAPP.TERMINAL)
                        expandido = false
                    }
                )
            }
        }
    }
}
