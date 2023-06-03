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

package cn.enaium.onekeyminer.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.function.Function;

/**
 * @author Enaium
 */
public interface FinishMiningCallback {
    Event<FinishMiningCallback> EVENT = EventFactory.createArrayBacked(FinishMiningCallback.class,
            (listeners) -> (world, player, pos, tryBreak) -> {
                for (FinishMiningCallback listener : listeners) {
                    ActionResult result = listener.interact(world, player, pos, tryBreak);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(ServerWorld world, ServerPlayerEntity player, BlockPos pos, Function<BlockPos, Void> tryBreak);
}
