package com.andrei1058.bedwars.support.triton;

import com.rexcantor64.triton.api.TritonAPI;
import com.rexcantor64.triton.api.players.LanguagePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Triton {
    private static Triton.supp Triton = new Triton.noTriton();

    public interface supp {
        /**
         * Get Player Triton ISO
         * @param uuid Target Player UUID
         * @return
         */
        String getLanguage(UUID uuid);
        void setLanguage(Player player,String iso);
    }

    public static class noTriton implements Triton.supp {

        @Override
        public String getLanguage(UUID uuid) {
            return null;
        }

        @Override
        public void setLanguage(Player player, String iso) {}
    }

    public static class withTriton implements Triton.supp {
        com.rexcantor64.triton.api.Triton triton = TritonAPI.getInstance();
        @Override
        public String getLanguage(UUID uuid) {
            LanguagePlayer langPlayer = triton.getPlayerManager().get(uuid);
            return com.andrei1058.bedwars.support.triton.Triton.getBedwarsISO(langPlayer.getLang().getLanguageId());
        }

        @Override
        public void setLanguage(Player player, String iso) {

        }
    }

    public static Triton.supp getTriton() {
        return Triton;
    }

    public static void setTriton(Triton.supp s) {
        Triton = s;
    }

    public static String getBedwarsISO(String iso) {
        return iso.replace("en_GB","en").replace("zh_TW","zh_hk").replace("zh_CN","zh_cn");
    }
}
