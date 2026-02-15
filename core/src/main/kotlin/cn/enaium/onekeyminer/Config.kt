/*
 * Copyright 2022 Enaium
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
package cn.enaium.onekeyminer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.IOException
import kotlin.io.path.*

/**
 * @author Enaium
 */
object Config {
    private val configFile = Path(System.getProperty("user.dir")).resolve("config").resolve("OneKeyMiner.json")

    var model = Model()
        private set

    fun load() {
        if (configFile.exists()) {
            try {
                model =
                    Gson().fromJson(configFile.readText(Charsets.UTF_8), Model::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            save()
        }
    }

    fun save() {
        try {
            configFile.createParentDirectories()
            configFile.writeText(GsonBuilder().setPrettyPrinting().create().toJson(model), Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    class Model {
        var limit = 64
        var interact = false
        var hotkey = true
        var axe: MutableList<String> = mutableListOf()

        var hoe: MutableList<String> = mutableListOf()

        var pickaxe: MutableList<String> = mutableListOf()

        var shovel: MutableList<String> = mutableListOf()

        var shears: MutableList<String> = mutableListOf()

        var any: MutableList<String> = mutableListOf()
    }
}
