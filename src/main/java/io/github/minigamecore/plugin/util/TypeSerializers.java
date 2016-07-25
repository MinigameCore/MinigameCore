package io.github.minigamecore.plugin.util;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.reflect.TypeToken.of;

import io.github.minigamecore.api.spawnpoint.ImmutableSpawnpoint;
import io.github.minigamecore.plugin.spawnpoint.ImmutableSpawnpointSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.Map;

/*
 * TypeSerializers registration helper class
 */
public final class TypeSerializers {

    private static final Map<Class, TypeSerializer> serializersMap = newHashMap();

    static {
        registerSerializer(ImmutableSpawnpoint.class, new ImmutableSpawnpointSerializer());
    }

    private static <T> void registerSerializer(Class<T> clazz, TypeSerializer<T> serializer) {
        serializersMap.put(clazz, serializer);
    }

    public static void registerSerializers() {
        serializersMap.forEach((clazz, serializer) -> ninja.leaping.configurate.objectmapping.serialize.TypeSerializers.getDefaultSerializers()
                .registerType(of(clazz), serializer));
    }

}
