package br.com.unisal.mateus.calculadora.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


enum class TemaDoAPP{
    CLARO,
    ESCURO,
    DINAMICO,
    MINIMALISTA
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    error = Green80,
    onError = White100
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val MinimalistaColorScheme = lightColorScheme(
    background = MinimalistaFundo,
    surface = MinimalistaSuperficie,
    onSurface = MinimalistaTextoNumero,

    // Operadores principais
    primaryContainer = MinimalistaOpFundo,
    onPrimaryContainer = MinimalistaOpTexto,

    // Botão de Igualdade (Destaque Principal)
    primary = MinimalistaIgualFundo,
    onPrimary = MinimalistaIgualTexto,

    // Botão Limpar / Atenção
    errorContainer = MinimalistaErroFundo,
    onErrorContainer = MinimalistaErroTexto
)


@Composable
fun CalculadoraTheme(
    temaDoAPP: TemaDoAPP,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val eschemaDeCor = when(temaDoAPP) {
        TemaDoAPP.CLARO -> {
            LightColorScheme
        }

        TemaDoAPP.ESCURO -> {
            DarkColorScheme
        }

        TemaDoAPP.DINAMICO -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (isSystemInDarkTheme()) {
                    dynamicDarkColorScheme(context)
                } else{
                    dynamicLightColorScheme(context)
                }
            } else {
                LightColorScheme
            }
        }
        TemaDoAPP.MINIMALISTA -> {
            MinimalistaColorScheme
        }
    }

    // Seleciona a tipografia e formas de acordo com o tema
    val tipografia = if (temaDoAPP == TemaDoAPP.MINIMALISTA) MinimalistaTypography else Typography

    MaterialTheme(
        colorScheme = eschemaDeCor,
        typography = Typography,
        content = content
    )
}
