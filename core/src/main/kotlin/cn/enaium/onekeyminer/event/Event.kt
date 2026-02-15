/*
 * Copyright 2025 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.onekeyminer.event

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * @author Enaium
 */
class Event<T>(
    private val invokerFactory: (List<T>) -> T
) {
    private val lock = ReentrantLock()
    private val handlers = mutableListOf<T>()
    var invoker: T = invokerFactory(emptyList())
        private set

    private fun update() {
        invoker = when {
            handlers.isEmpty() -> invokerFactory(emptyList())
            handlers.size == 1 -> handlers[0]
            else -> invokerFactory(handlers)
        }
    }

    fun register(listener: T) {
        lock.withLock {
            handlers.add(listener)
            update()
        }
    }
}