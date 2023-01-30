package com.rocqjones.listapp

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    // var shouldShowOnBoarding by remember { mutableStateOf(true) }

    /**
     * Persisting state
     * - The remember function works only as long as the composable is kept in the Composition.
     * - When you rotate, the whole activity is restarted so all state is lost.
     * - This also happens with any configuration change and on process death.
     * - rememberSaveable will save each state surviving configuration changes (such as rotations) and process death.
     */
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }
    
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
        Text(
            "Welcome to the List App!",
            style = MaterialTheme.typography.headlineMedium,
            color =MaterialTheme.colorScheme.secondary
        )
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked // sets the sate to true
        ) {
            Text("Continue")
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Greeting(name: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    /**
     * Btn state management with remember.
     * - If you expand item number 1, you scroll away to number 20 and come back to 1, you'll notice that 1 is back to the original size.
     * - You could save this data with rememberSaveable if it were a requirement
     */
    val expanded = remember { mutableStateOf(false) }

    /**
     * additional variable that depends on our btn state
     * Animate collapsing.
     * - Let's do something more fun like adding a spring-based animation
     * - The spring spec does not take any time-related parameters. Instead it relies on physical
     * properties (damping and stiffness) to make animations more natural.
     */
    /*
    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )*/
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(12.dp) //.padding(bottom = extraPadding.coerceAtLeast(0.dp)) // due to animation we're making sure that padding is never negative, otherwise it could crash the app.
        ) {
            Text(text = "Hello")
            Text(text = "$name.",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )

            if (expanded.value) {
                Text(
                    text = ("Composem ipsum color sit lazy, padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }

        /*
        ElevatedButton(onClick = { expanded.value = !expanded.value }) {
            Text(
                if (expanded.value) stringResource(R.string.show_less) else stringResource(R.string.show_more)
            )
        }*/

        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(
                // imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                painter = if (expanded.value) painterResource(id = R.drawable.baseline_expand_less_24) else painterResource(id = R.drawable.baseline_expand_more_24),
                contentDescription = if (expanded.value) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ListAppTheme {
        Greetings()
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

@Preview
@Composable
fun MyAppPreview() {
    ListAppTheme {
        MyApp(Modifier.fillMaxSize())
    }
}