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
- `remember` is used to guard against recomposition, so the state is not reset.

### State hoisting
In Composable functions, state that is read or modified by multiple functions should live in a common ancestor—this process is called state **hoisting**. To hoist means to *lift or elevate*.

Making state hoistable avoids duplicating state and introducing bugs, helps reuse composables, and makes composables substantially easier to test. Contrarily, state that doesn't need to be controlled by a composable's parent should not be hoisted. The **source of truth** belongs to whoever creates and controls that state.

In Compose **you don't hide UI elements**. Instead, you simply don't add them to the composition, so they're not added to the UI tree that Compose generates. 
