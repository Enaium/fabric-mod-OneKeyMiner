/*
 * Copyright 2026 Enaium
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

import cn.enaium.onekeyminer.common.Action
import cn.enaium.onekeyminer.common.Tool

/**
 * @author Enaium
 */
class ServerCommandCallbacks {
    fun interface ReloadCallback {
        companion object {
            val EVENT = Event { listeners: List<ReloadCallback> ->
                ReloadCallback {
                    for (listener in listeners) {
                        listener.execute()
                    }
                }
            }
        }

        fun execute()
    }

    fun interface LimitCallback {
        companion object {
            val EVENT = Event { listeners: List<LimitCallback> ->
                LimitCallback { limit ->
                    for (listener in listeners) {
                        listener.execute(limit)
                    }
                }
            }
        }

        fun execute(limit: Int)
    }

    fun interface InteractCallback {
        companion object {
            val EVENT = Event { listeners: List<InteractCallback> ->
                InteractCallback { limit ->
                    for (listener in listeners) {
                        listener.execute(limit)
                    }
                }
            }
        }

        fun execute(interact: Boolean)
    }

    fun interface HotkeyCallback {
        companion object {
            val EVENT = Event { listeners: List<HotkeyCallback> ->
                HotkeyCallback { hotkey ->
                    for (listener in listeners) {
                        listener.execute(hotkey)
                    }
                }
            }
        }

        fun execute(hotkey: Boolean)
    }

    fun interface ActionCallback {
        companion object {
            val EVENT = Event { listeners: List<ActionCallback> ->
                ActionCallback { tool, action, block ->
                    for (listener in listeners) {
                        listener.execute(tool, action, block)
                    }
                }
            }
        }

        fun execute(tool: Tool, action: Action, block: String)
    }
}