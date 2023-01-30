package com.rocqjones.listapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rocqjones.listapp.ui.theme.ListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListAppTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    /**
     * This state should be hoisted
     * Using a by keyword instead of the =.
     * - This is a property delegate that saves you from typing .value every time.
     * logic to show the different screens in MyApp, and hoist the state.
     */
    var shouldShowOnBoarding by remember { mutableStateOf(true) }
    
    Surface(modifier) {
        when {
            shouldShowOnBoarding -> {
                OnBoardingScreen(onContinueClicked = { shouldShowOnBoarding = false} )
            }
            else -> {
                Greetings()
            }
        }
    }
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    /**
     * When the button is clicked, shouldShowOnBoarding is set to false
     * We want to show this on launch and then hide it when the user presses "Continue".
     */
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the List App!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked // sets the sate to true
        ) {
            Text("Continue")
        }
    }
}

// Added a fixed height to verify that the content is aligned correctly.
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnBoardingPreview() {
    ListAppTheme {
        OnBoardingScreen(onContinueClicked = {}) // empty lambda expression means "Do nothing" on click
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    /**
     * reusable fun to avoiding code duplication
     * Creating a performant lazy list
     */
    /*
    Column(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        for (name in names) {
            Greeting(name = name)
        }
    }*/

    // for loop threads too long, alternative is LazyColumn and items
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { n ->
            Greeting(name = n)
        }
    }
}

@Composable
fun Greeting(name: String) {
    // Btn state management with remember.
    val expanded = remember { mutableStateOf(false) }

    // additional variable that depends on our btn state
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello")
                Text(text = "$name.")
            }

            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ListAppTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    ListAppTheme {
        MyApp(Modifier.fillMaxSize())
    }
}