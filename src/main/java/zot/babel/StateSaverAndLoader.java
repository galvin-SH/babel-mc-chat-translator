package zot.babel;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.UUID;

public class StateSaverAndLoader extends PersistentState {

    // create a HashMap to store the data
    public HashMap<UUID, PlayerData> players = new HashMap<>();

    @Override
    // save data to the hashmap
    public NbtCompound writeNbt(NbtCompound nbt) {

        NbtCompound playersNbt = new NbtCompound();

        players.forEach((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();
            playerNbt.putString("name", playerData.playerName);
            playerNbt.putString("locale", playerData.playerLocale);
            playersNbt.put(uuid.toString(), playerNbt);
        });
        nbt.put("players", playersNbt);
        return nbt;
    }

    // load data from the hashmap
    public static StateSaverAndLoader createFromNbt(NbtCompound tag) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        NbtCompound playersNbt = tag.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();

            playerData.playerName = playersNbt.getCompound(key).getString("name");
            playerData.playerLocale = playersNbt.getCompound(key).getString("locale");
            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });
        return state;
    }

    // get the persistent state manager for the server
    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager manager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = manager.getOrCreate(StateSaverAndLoader::createFromNbt, StateSaverAndLoader::new,
                Babel.MOD_ID);
        state.markDirty();
        return state;
    }

    // get the player's data from the hashmap or create a new one if it doesn't exist
    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(player.getWorld().getServer());
        PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
        return playerState;
    }
}