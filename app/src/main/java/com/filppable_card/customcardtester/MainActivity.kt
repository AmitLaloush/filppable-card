package com.filppable_card.customcardtester

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.filppable_card.FlippableCard
import com.filppable_card.customcardtester.ui.theme.CustomCardTesterTheme
import java.util.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomCardTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var initialOffsetX = 0
                    var initialOffsetY = 0
                    var cards by remember {
                        mutableStateOf(
                            mutableListOf(
                                Pair("A1","A2"),
                                Pair("B1","B2"),
                                Pair("C1","C2"),
                                Pair("D1","D2"),
                                Pair("E1","E2"),
                                Pair("A1","A2"),
                                Pair("B1","B2"),
                                Pair("C1","C2"),
                                Pair("D1","D2"),
                                Pair("E1","E2"),
                                Pair("A1","A2"),
                                Pair("B1","B2"),
                                Pair("C1","C2"),
                                Pair("D1","D2"),
                                Pair("E1","E2"),
                                Pair("A1","A2"),
                                Pair("B1","B2"),
                                Pair("C1","C2"),
                                Pair("D1","D2"),
                                Pair("E1","E2"),

                            ))
                    }

                    Box() {

                        for (i in 0 until cards.size) {
                            val item = cards[i]
                            println("Index: $i, $item" )
                            Log.d("AMIT555", "onCreate: $i $item")
                            FlippableCard(
                                modifier = Modifier
                                    .offset { IntOffset(i * 10, i * -10) }
                                    .fillMaxWidth(),
                                frontContent = {
                                    Text(text = item.first)
                                },
                                backContent = {
                                    Text(text = item.second)
                                },
                                onDismiss = {
                                    Log.d("AMIT555", "onCreate: ${cards.size}")
                                    cards = cards.dropLast(1).toMutableList()

                                },
                                onConfirm = {
                                    Log.d("AMIT555", "onCreate: ${cards.size}")
                                    cards = cards.dropLast(1).toMutableList()

                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun generateRandomCard(): Pair<String, String> {
        val word = Random().nextInt().toString()
        val translation = Random().nextInt().toString()
        return Pair(word, translation)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomCardTesterTheme {
        Greeting("Android")
    }
}

