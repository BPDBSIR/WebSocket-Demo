package cn.xiaoyes.composedemo

import android.os.Bundle
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.xiaoyes.composedemo.ui.theme.ComposeDemoTheme
import androidx.compose.ui.Alignment
import androidx.core.view.WindowCompat
import okhttp3.*


class MainActivity : ComponentActivity() {

    private var client: OkHttpClient? = null

    private var socket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client = OkHttpClient.Builder()
            .build()

        setContent {
            ComposeDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Button(onClick = {
                            val request = Request.Builder()
                                .url("ws://192.168.0.104:8080/socket")
                                .build()

                            socket = client!!.newWebSocket(request = request,listener = object : WebSocketListener(){
                                override fun onOpen(webSocket: WebSocket, response: Response) {
                                    Log.i("","连接成功")
                                }

                                override fun onMessage(webSocket: WebSocket, text: String) {
                                    Log.i("","接受消息 => $text")
                                }

                                override fun onClosed(
                                    webSocket: WebSocket,
                                    code: Int,
                                    reason: String
                                ) {
                                    Log.e("","连接关闭")
                                }

                                override fun onFailure(
                                    webSocket: WebSocket,
                                    t: Throwable,
                                    response: Response?
                                ) {
                                    Log.e("","连接失败 => ${t.message}")
                                }
                            })
                        }) {
                            Text(text = "连接")
                        }
                        Button(onClick = { socket!!.send("我是发送的信息") }) {
                            Text(text = "发送信息")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDemoTheme {
        Greeting("Android")
    }
}
