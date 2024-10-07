/*
 * Copyright 2024 Michael Neonov <two.nelonn at gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.nelonn.coprolite.paper.std.registryaccessor;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Lifecycle;
import net.minecraft.SharedConstants;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("all")
public class EntityTypeRegistry {
    public static final EntityTypeRegistry INSTANCE = new EntityTypeRegistry();
    private static final Map<Object, Type<?>> DATAFIXER_TYPES;
    private final DefaultedMappedRegistry<EntityType<?>> registry = (DefaultedMappedRegistry<EntityType<?>>) BuiltInRegistries.ENTITY_TYPE;

    private EntityTypeRegistry() {
    }

    @NotNull
    public <T extends Entity> EntityType<T> register(@NotNull String id, @NotNull EntityType.Builder type, @NotNull EntityType<? extends Entity> clientSide, @NotNull EntityType<? extends Entity> datafixerAnalog) {
        ResourceLocation dataFixerAnalogKey = registry.getKey(datafixerAnalog);
        if (!dataFixerAnalogKey.getNamespace().equals("minecraft")) {
            throw new IllegalArgumentException("Vanilla analog must be one of vanilla entities");
        }
        ResourceLocation key = new ResourceLocation(id);
        DATAFIXER_TYPES.put(key.toString(), DATAFIXER_TYPES.get(dataFixerAnalogKey.toString()));
        EntityType<T> entry = type.build(id);
        registry.registerMapping(registry.getId(clientSide), ResourceKey.create(registry.key(), new ResourceLocation(id)), entry, Lifecycle.stable());
        return entry;
    }

    @NotNull
    public <T extends Entity> EntityType<T> register(@NotNull String id, @NotNull EntityType.Builder type, @NotNull EntityType<? extends Entity> clientSide) {
        return register(id, type, clientSide, EntityType.MARKER);
    }

    @NotNull
    public <T extends Entity> EntityType<T> register(@NotNull ResourceLocation id, @NotNull EntityType.Builder type, @NotNull EntityType<? extends Entity> clientSide, @NotNull EntityType<? extends Entity> datafixerAnalog) {
        return register(id.toString(), type, clientSide, datafixerAnalog);
    }

    @NotNull
    public <T extends Entity> EntityType<T> register(@NotNull ResourceLocation id, @NotNull EntityType.Builder type, @NotNull EntityType<? extends Entity> clientSide) {
        return register(id.toString(), type, clientSide);
    }

    static {
        DATAFIXER_TYPES = (Map<Object, Type<?>>) DataFixers.getDataFixer()
                .getSchema(DataFixUtils.makeKey(SharedConstants.getCurrentVersion().getDataVersion().getVersion()))
                .findChoiceType(References.ENTITY_TREE)
                .types();
    }
}