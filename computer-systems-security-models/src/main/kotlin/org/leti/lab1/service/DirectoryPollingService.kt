package org.leti.lab1.service

import com.sun.nio.file.SensitivityWatchEventModifier
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*


class DirectoryPollingService(dir: String) {

    private var watcher = FileSystems.getDefault().newWatchService()
    private var keys = mutableMapOf<WatchKey, Path>()

    init {
        register(Paths.get(dir))
    }

    fun startPolling(action: (Path, WatchEvent.Kind<out Any>) -> Unit) {
        GlobalScope.launch {
            processEvents(action)
        }
    }

    private fun register(dir: Path) {
        val key = dir.register(watcher, arrayOf(ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY), SensitivityWatchEventModifier.HIGH)
        keys[key] = dir
    }

    private fun processEvents(action: (Path, WatchEvent.Kind<out Any>) -> Unit) {
        while (true) {
            val key = try {
                watcher.take()
            } catch (x: InterruptedException) {
                return
            }
            val directory = keys[key]
            if (directory == null) {
                println("WatchKey is not recognized")
                continue
            }
            for (event in key.pollEvents()) {
                val kind = event.kind()
                if (kind === OVERFLOW) {
                    continue
                }

                val eventContext = cast<Path>(event).context()
                val file = directory.resolve(eventContext)

                action(file, kind)
            }

            val valid = key.reset()
            if (!valid) {
                keys.remove(key)
                if (keys.isEmpty()) {
                    break
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> cast(event: WatchEvent<*>) = event as WatchEvent<T>
}