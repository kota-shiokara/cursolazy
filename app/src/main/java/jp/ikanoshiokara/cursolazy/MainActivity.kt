package jp.ikanoshiokara.cursolazy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import jp.ikanoshiokara.cursolazy.ui.theme.CursolazyPreview
import jp.ikanoshiokara.cursolazy.ui.theme.CursolazyTheme
import androidx.core.net.toUri

class MainActivity : ComponentActivity() {
    private lateinit var intent: Intent
    private var launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if(Settings.canDrawOverlays(this)){
                startForegroundService(intent)
            }
            else{
                Toast.makeText(applicationContext, "Require is Display Overlay", Toast.LENGTH_LONG)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent = Intent(application, CursolazyService::class.java)
        setContent {
            val context = LocalContext.current
            CursolazyTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                if (Settings.canDrawOverlays(context)){
                                    startForegroundService(intent)
                                } else{
                                    val intent = Intent(
                                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                        "package:$packageName".toUri()
                                    )
                                    startActivity(intent)
                                    launcher.launch(intent)
                                }
                            }
                        ) {
                            Text("Click!")
                        }
                    }
                }
            }
        }
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
fun GreetingPreview() = CursolazyPreview {
    Greeting("Android")
}