package com.example.calculadordegorjeta

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadordegorjeta.ui.theme.CalculadorDeGorjetaTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadorDeGorjetaTheme {
                AppCalculadorDeGorjeta()
            }
        }
    }
}

@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCalculadorDeGorjeta() {
    var totalConta by remember { mutableStateOf("") }
    var porcentagem by remember { mutableStateOf("") }
    var arredondar by remember { mutableStateOf(false) }
    val valorGorjeta = calcularGorjeta(
        totalConta.toDoubleOrNull() ?: 0.0,
        porcentagem.toDoubleOrNull() ?: 15.0,
        arredondar
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.calculador_de_gorjeta)
        )
        campoDeTexto(
            value = totalConta,
            onValueChange = { totalConta = it },
            idText = R.string.valor_da_conta,
            imeAction = ImeAction.Next
        )

        campoDeTexto(
            value = porcentagem,
            onValueChange = { porcentagem = it },
            idText = R.string.gorjeta,
            imeAction = ImeAction.Done
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

        ){
            Text(
                text = "Arredondar Gorjeta",
            )
            Switch(
                checked = arredondar,
                onCheckedChange = { arredondar = it }
            )
        }
        Text(
            text = stringResource(R.string.valor_gorjeta, valorGorjeta)
        )
    }
}

fun calcularGorjeta(
    totalConta: Double,
    porcentagemGorjeta: Double = 15.0 ,
    arredondar :Boolean= false
): String {
    var gorjeta = porcentagemGorjeta / 100 * totalConta
    if (arredondar){
        gorjeta= kotlin.math.ceil(gorjeta)
    }
    return NumberFormat.getCurrencyInstance().format(gorjeta)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun campoDeTexto(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes idText: Int,
    imeAction: ImeAction,
    @DrawableRes iconeCAmpoTexto : Int
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(painter = painterResource(id = iconeCAmpoTexto)) },
        label = {
            Text(text = stringResource(id = idText))
        },
        isError = false,
        shape = RectangleShape,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction""
        ),
    )
    Spacer(
        modifier = Modifier.size(10.dp)
    )

}