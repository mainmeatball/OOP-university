package org.leti.lab1.service

import com.sun.nio.file.SensitivityWatchEventModifier
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*


class DirectoryPollingService(pathToDirectory: String) {

    private var watchService = FileSystems.getDefault().newWatchService()
    private var watchKeys = mutableMapOf<WatchKey, Path>()

    init {
        register(Paths.get(pathToDirectory))
    }

    fun startPolling(action: (Path, WatchEvent.Kind<out Any>) -> Unit) {
        GlobalScope.launch {
            processEvents(action)
        }
    }

    private fun register(directory: Path) {
        val watchKey = directory.register(watchService, arrayOf(ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY), SensitivityWatchEventModifier.HIGH)
        watchKeys[watchKey] = directory
    }

    private fun processEvents(doAction: (Path, WatchEvent.Kind<out Any>) -> Unit) {
        while (true) {
            val watchKey = receiveSignal() ?: return
            val directory = watchKeys[watchKey]
            if (directory == null) {
                println("WatchKey is not recognized")
                continue
            }
            for (watchEvent in watchKey.pollEvents()) {
                val watchEventKind = watchEvent.kind()
                if (watchEventKind === OVERFLOW) {
                    continue
                }

                val eventContext = cast<Path>(watchEvent).context()
                val file = directory.resolve(eventContext)

                doAction(file, watchEventKind)
            }

            val valid = watchKey.reset()
            if (!valid) {
                watchKeys.remove(watchKey)
                if (watchKeys.isEmpty()) {
                    break
                }
            }
        }
    }

    private fun receiveSignal(): WatchKey? = try {
        watchService.take()
    } catch (x: InterruptedException) {
        null
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> cast(event: WatchEvent<*>) = event as WatchEvent<T>
}