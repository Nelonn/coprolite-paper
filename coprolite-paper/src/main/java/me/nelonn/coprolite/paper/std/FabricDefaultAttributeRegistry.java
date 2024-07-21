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

package me.nelonn.coprolite.paper.std;

import me.nelonn.coprolite.paper.std.mixin.DefaultAttributesAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FabricDefaultAttributeRegistry {
	/**
	 * Private logger, not exposed.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FabricDefaultAttributeRegistry.class);

	private FabricDefaultAttributeRegistry() {
	}

	public static void register(EntityType<? extends LivingEntity> type, AttributeSupplier.Builder builder) {
		register(type, builder.build());
	}

	public static void register(EntityType<? extends LivingEntity> type, AttributeSupplier container) {
		if (DefaultAttributesAccessor.getRegistry().put(type, container) != null) {
			LOGGER.debug("Overriding existing registration for entity type {}", BuiltInRegistries.ENTITY_TYPE.getId(type));
		}
	}
}