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

package cn.enaium.onekeyminer.config

/**
 * @author Enaium
 */
enum class Shape(val key: String) {
    CUBE("onekeyminer.shape.cube"),
    HORIZONTAL_PLANE("onekeyminer.shape.horizontal_plane"),
    VERTICAL_PLANE("onekeyminer.shape.vertical_plane"),
    COLUMN("onekeyminer.shape.column"),
    SPHERE("onekeyminer.shape.sphere"),
    TUNNEL("onekeyminer.shape.tunnel"),
    PYRAMID("onekeyminer.shape.pyramid"),
    DIAMOND("onekeyminer.shape.diamond"),
    CONE("onekeyminer.shape.cone")
}
