package com.diyigemt.arona

import com.diyigemt.arona.command.BuiltInCommands
import com.diyigemt.arona.command.initExecutorMap
import com.diyigemt.arona.communication.TencentBotClient
import com.diyigemt.arona.console.launchConsole
import com.diyigemt.arona.plugins.PluginManager
import com.diyigemt.arona.utils.aronaConfig
import com.diyigemt.arona.utils.runSuspend
import com.diyigemt.arona.webui.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object AronaApplication : CoroutineScope {
  fun run() {
    System.getProperty("java.awt.headless", "true")
    BuiltInCommands.registerAll()
    BuiltInCommands.registerListeners()
    runSuspend {
      launchConsole()
    }
    PluginManager.loadPluginFromPluginDirectory()
    PluginManager.initPlugin()
    initExecutorMap()
    TencentBotClient.invoke(aronaConfig.bot).auth()
    val environment = applicationEngineEnvironment {
      connector {
        port = aronaConfig.web.port
        host = "127.0.0.1"
      }
      rootPath = "/api/v1"
      module(Application::module)
    }
    embeddedServer(Netty, environment).start(wait = true)
  }

  override val coroutineContext: CoroutineContext = EmptyCoroutineContext
}

fun main() {
  AronaApplication.run()
}

fun Application.module() {
  configureHTTP()
  configureRouting()
  configureSerialize()
  configureDoubleReceive()
  configureErrorHandler()
}
