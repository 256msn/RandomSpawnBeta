package net.utils.randomspawnfixed;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomSpawnFixed extends JavaPlugin implements Listener {
    private static final int SPAWN_RANGE = 150;
    private Map<Player, Location> playerLocations = new HashMap();

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        System.out.println("RandomSpawn enabled!");
    }

    public void onDisable() {
        System.out.println("RandomSpawn disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location currentLocation = player.getLocation();
        this.playerLocations.put(player, currentLocation);
        System.out.println("Player " + player.getName() + " has joined and their location has been saved.");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location currentLocation = player.getLocation();
        if (!currentLocation.equals(this.playerLocations.get(player))) {
            Location spawnLocation = this.getRandomSpawnLocation(player.getWorld());
            event.setRespawnLocation(spawnLocation);
            System.out.println("Player " + player.getName() + " has been randomly respawned at (" + spawnLocation.getBlockX() + ", " + spawnLocation.getBlockY() + ", " + spawnLocation.getBlockZ() + ")");
        }

    }

    private Location getRandomSpawnLocation(World world) {
        Random random = new Random();
        int spawnX = random.nextInt(300) - 150;
        int spawnZ = random.nextInt(300) - 150;
        int spawnY = world.getHighestBlockYAt(spawnX, spawnZ);
        return new Location(world, (double)spawnX, (double)spawnY, (double)spawnZ);
    }
}
