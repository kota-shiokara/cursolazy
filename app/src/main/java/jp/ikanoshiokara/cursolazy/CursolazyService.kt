package jp.ikanoshiokara.cursolazy

import android.content.Intent
import android.graphics.PixelFormat
import android.view.WindowManager
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class CursolazyService : LifecycleService(), SavedStateRegistryOwner {
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    private val params by lazy {
        WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT,
        )
    }

    private val windowManager by lazy {
        getSystemService(WINDOW_SERVICE) as WindowManager
    }

    private val composeView by lazy {
        ComposeView(this).apply {
            setContent {
                Text("Hello Compose")
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        composeView.apply {
            setViewTreeLifecycleOwner(this@CursolazyService)
            setViewTreeSavedStateRegistryOwner(this@CursolazyService)
        }
        savedStateRegistryController.performRestore(null)
        windowManager.addView(composeView, params)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        windowManager.removeView(composeView)
        super.onDestroy()
    }
}