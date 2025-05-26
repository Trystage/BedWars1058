package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.BedWars;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpongeAnimationTask extends BukkitRunnable {
    private final Block block;
    private final Location loc;
    private int radius = 1;
    private int pitch = 17;
    private final static String splash = BedWars.getForCurrentVersion("SPLASH", "ENTITY_PLAYER_SPLASH", "ENTITY_PLAYER_SPLASH");
    private final static String woodClick = BedWars.getForCurrentVersion("WOOD_CLICK", "BLOCK_WOOD_BUTTON_CLICK_ON", "BLOCK_WOODEN_BUTTON_CLICK_ON");

    public SpongeAnimationTask(Block block) {
        this.block = block;
        this.loc = block.getLocation();
    }

    public void run() {
        if (this.radius > 4) {
            this.cancel();
            this.block.setType(Material.AIR);
            this.loc.getWorld().playSound(this.loc, Sound.valueOf(splash), 1.0F, 1.0F);
        } else {
            this.getParticles(this.loc, this.radius).forEach((loc) -> {
                PacketPlayOutWorldParticles pwp = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0.0F, 0.0F, 0.0F, 0.1F, 1);
                Bukkit.getOnlinePlayers().forEach((p) -> ((CraftPlayer) p).getHandle().playerConnection.sendPacket(pwp));
            });
            this.loc.getWorld().playSound(this.loc, Sound.valueOf(woodClick), 1.0F, (float) this.pitch / 10.0F);
            ++this.radius;
            ++this.pitch;
        }
    }

    public List<Location> getParticles(Location loc, int radius) {
        List<Location> result = new ArrayList<>();
        Block start = loc.getWorld().getBlockAt(loc);
        int iterations = radius * 2 + 1;
        List<Block> blocks = new ArrayList<>(iterations * iterations * iterations);

        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    blocks.add(start.getRelative(x, y, z));
                }
            }
        }

        blocks.stream().filter((b) -> b.getType().equals(Material.AIR)).forEach((b) -> result.add(b.getLocation()));
        return result;
    }
}

