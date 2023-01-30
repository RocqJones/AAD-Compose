# AAD-Compose
Jetpack Compose for building native Android UI

Compose apps transform data into UI by calling **composable functions**. If your data changes, Compose re-executes these functions with the new data, creating an updated UI—this is called **recomposition**. 
- Compose also looks at what data is needed by an individual composable so that it only needs to recompose components whose data has changed and skip recomposing those that are not affected.
- Composable functions can execute frequently and in any order, you must not rely on the ordering in which the code is executed, or on how many times this function will be recomposed.

### State in Compose
To add internal state to a composable, you can use the mutableStateOf function, which makes Compose recompose functions that read that State.
- **However you can't just assign `mutableStateOf` to a variable inside a composable** because recomposition can happen at any time which would call the composable again, resetting the state to a new mutable state with a value of `false`.
- To preserve state across recompositions, remember the mutable state using `remember`.
```Kotlin
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
...

@Composable
fun SomeFunctionName() {
    val expanded = remember { mutableStateOf(false) }
    ...
}

```
