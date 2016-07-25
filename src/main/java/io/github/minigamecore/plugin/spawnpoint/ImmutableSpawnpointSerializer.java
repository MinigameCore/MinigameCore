package io.github.minigamecore.plugin.spawnpoint;

import static com.google.common.reflect.TypeToken.of;

import com.google.common.reflect.TypeToken;
import io.github.minigamecore.api.spawnpoint.ImmutableSpawnpoint;
import io.github.minigamecore.api.spawnpoint.spawnpointtype.SpawnpointType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.world.extent.Extent;

import java.util.Collection;
import java.util.UUID;

/*
 * Configurate serializer for ImmutableSpawnpoint
 */
public final class ImmutableSpawnpointSerializer implements TypeSerializer<ImmutableSpawnpoint> {

    @Override
    public ImmutableSpawnpoint deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        try {
            return new ImmutableSpawnpointImpl(value.getNode("active").getBoolean(),
                    value.getNode("spawnpointType").getValue(of(SpawnpointType.class)),
                    value.getNode("teams").getValue(new TypeToken<Collection<Team>>() {}),
                    value.getNode("transform").getValue(new TypeToken<Transform<? extends Extent>>() {}),
                    value.getNode("uuid").getValue(of(UUID.class)));
        } catch (NullPointerException e) {
            throw new ObjectMappingException(e);
        }
    }

    @Override
    public void serialize(TypeToken<?> type, ImmutableSpawnpoint obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("active").setValue(obj.isActive());
        value.getNode("spawnpointType").setValue(of(SpawnpointType.class), obj.getSpawnpointType());
        value.getNode("teams").setValue(new TypeToken<Collection<Team>>() {}, obj.getTeams());
        value.getNode("transform").setValue(of(Transform.class), obj.getTransform());
        value.getNode("uuid").setValue(of(UUID.class), obj.getUniqueId());
    }

}
