package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class WaterWorkerHelmet implements Listener {
    @EventHandler
    public void onGameStart(GameStateChangeEvent e) {
        if (e.getNewState().equals(GameState.playing)) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> e.getArena().getPlayers().forEach((p) -> this.setPlayerHelmet(p, e.getArena().getTeam(p))), 1L);
        }
    }

    @EventHandler
    public void onRespawn(PlayerReSpawnEvent e) {
        Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> this.setPlayerHelmet(e.getPlayer(), e.getTeam()), 1L);
    }

    private void setPlayerHelmet(Player player, ITeam team) {
        if(BedWars.config.getBoolean(ConfigPath.WATER_WORKER_HELMET)) {
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
            meta.addEnchant(Enchantment.WATER_WORKER, 1, true);
            meta.setColor(team.getColor().bukkitColor());
            meta.spigot().setUnbreakable(true);
            helmet.setItemMeta(meta);
            player.getEquipment().setHelmet(helmet);
        }
    }
}
