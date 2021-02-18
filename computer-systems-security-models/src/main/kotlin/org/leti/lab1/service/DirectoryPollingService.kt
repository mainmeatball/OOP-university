package org.leti.lab1.service

import com.sun.nio.file.SensitivityWatchEventModifier
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.WatchKey


class DirectoryPollingService(dir: String) {

    private var watcher = FileSystems.getDefault().newWatchService()
    private var keys = mutableMapOf<WatchKey, Path>()

    init {
        register(Paths.get(dir))
    }

    fun startPolling(action: () -> Unit) {
        GlobalScope.launch {
            processEvents {
                action()
            }
        }
    }

    private fun register(dir: Path) {
        val key = dir.register(watcher, arrayOf(ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY), SensitivityWatchEventModifier.HIGH)
        keys[key] = dir
    }

    private fun processEvents(action: () -> Unit) {
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
                action()
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
}