package br.com.unisal.mateus.calculadora.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


enum class TemaDoAPP {
    CLARO,
    ESCURO,
    DINAMICO,
    TERMINAL
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
)


// Esquema de cores do tema Terminal
private val TerminalColorScheme = darkColorScheme(
    primary = TerminalGreen,
    onPrimary = TerminalBackground,

    primaryContainer = TerminalGreenDark,
    onPrimaryContainer = TerminalGreen,

    secondary = TerminalGreenLight,
    onSecondary = TerminalBackground,

    secondaryContainer = TerminalSurfaceVariant,
    onSecondaryContainer = TerminalGreenLight,

    background = TerminalBackground,
    onBackground = TerminalText,

    surface = TerminalSurface,
    onSurface = TerminalText,

    surfaceVariant = TerminalSurfaceVariant,
    onSurfaceVariant = TerminalText,

    error = TerminalError,
    onError = TerminalBackground
)


// Formas utilizadas no tema Terminal
private val TerminalShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(8.dp)
)


@Composable
fun CalculadoraTheme(
    temaDoAPP: TemaDoAPP,
    content: @Composable () -> Unit
) {

    val context = LocalContext.current

    val eschemaDeCor = when (temaDoAPP) {

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
                } else {
                    dynamicLightColorScheme(context)
                }
            } else {
                LightColorScheme
            }
        }

        TemaDoAPP.TERMINAL -> {
            TerminalColorScheme
        }
    }


    val tipografia = if (temaDoAPP == TemaDoAPP.TERMINAL) {
        TerminalTypography
    } else {
        Typography
    }


    val formas = if (temaDoAPP == TemaDoAPP.TERMINAL) {
        TerminalShapes
    } else {
        Shapes()
    }


    MaterialTheme(
        colorScheme = eschemaDeCor,
        typography = tipografia,
        shapes = formas,
        content = content
    )
}
