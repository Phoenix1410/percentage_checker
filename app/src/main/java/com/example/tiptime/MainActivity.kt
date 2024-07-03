package com.example.tiptime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeAction.Companion.Next
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTImeTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipTImeTheme {
                Surface(modifier=Modifier.fillMaxSize())
                {
                    TipTimeLayout()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout()
{
    var amountInput by remember{ mutableStateOf(" ")}
    var tipInput by remember { mutableStateOf(" ") }
    val amount = amountInput.toDoubleOrNull()?:0.0// elvis operator
    val tipPercent = tipInput.toDoubleOrNull()?:0.0
    val tip = calculateTip(amount, tipPercent)
    Column(modifier = Modifier
        .statusBarsPadding()
        .padding(horizontal = 40.dp)
        .safeDrawingPadding(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        Text(text = stringResource(R.string.calculate_tip),
            modifier= Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(label = R.string.bill_amount,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            value = amountInput,
            onValueChange = { amountInput =it}, modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        EditNumberField(label = R.string.how_was_the_service,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done),
            value = tipInput,
            onValueChange = { tipInput =it}, modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        Text( text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(150.dp))
    }
}



@SuppressLint("UnrememberedMutableState")
@Composable
fun EditNumberField(@StringRes label : Int,keyboardOptions: KeyboardOptions,
                    value : String , onValueChange : (String)-> Unit,
                    modifier: Modifier = Modifier)
{

    var amountInput by remember{ mutableStateOf(" ")}
    val amount = amountInput.toDoubleOrNull()?:0.0// elvis operator
    val tip = calculateTip(amount)
    TextField(value = value,
        onValueChange = onValueChange,
         label = { Text(stringResource(label)) },
         singleLine = true,
        keyboardOptions = keyboardOptions

        )

}
private fun calculateTip(amount: Double,tipPercent: Double=15.0): String
{
    val tip = tipPercent/100*amount
    return NumberFormat.getNumberInstance().format(tip)
}
@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTImeTheme {
        TipTimeLayout()
    }
}