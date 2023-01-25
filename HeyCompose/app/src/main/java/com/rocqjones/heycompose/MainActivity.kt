package com.rocqjones.heycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rocqjones.heycompose.model.Message
import com.rocqjones.heycompose.ui.theme.HeyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MessageCard(Message("Jones Mbindyo", "Hey, take a look at Jetpack Compose, it's great!"))
                }
            }
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_image),
            contentDescription = stringResource(R.string.profile_pic),
            // Set image size to 40 dp and Clip image to be shaped as a circle
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)

        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = msg.author)

            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(text = msg.body)
        }
    }
}

/**
 * This window shows a preview of the UI elements created by composable functions marked with the
 * @Preview annotation.
 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HeyComposeTheme {
        MessageCard(
            msg = Message("Colleague", "Jetpack compose!")
        )
    }
}