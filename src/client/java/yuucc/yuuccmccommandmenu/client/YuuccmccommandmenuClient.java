package yuucc.yuuccmccommandmenu.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class YuuccmccommandmenuClient implements ClientModInitializer {
    private static KeyBinding openMenuKey;
    private static int tickCounter = 0;

    // 锁定状态
    public static boolean timeLocked = false;
    public static String lockedTime = "day";
    public static boolean weatherLocked = false;
    public static String lockedWeather = "clear";

    @Override
    public void onInitializeClient() {
        // 注册J键绑定
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.yuuccmccommandmenu.open_menu", // 翻译键
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_J, // J键
            "category.yuuccmccommandmenu" // 类别
        ));

        // 监听按键事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openMenuKey.wasPressed()) {
                // 打开主菜单GUI
                client.setScreen(new MainMenuScreen());
            }
        });

        // 锁定检查定时器
        ClientTickEvents.END_WORLD_TICK.register(world -> {
            tickCounter++;
            if (tickCounter >= 100) { // 每5秒检查一次 (20 ticks/second * 5)
                tickCounter = 0;
                checkAndMaintainLocks();
            }
        });
    }

    private void checkAndMaintainLocks() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getNetworkHandler() == null) return;

        if (timeLocked) {
            client.getNetworkHandler().sendCommand("time set " + lockedTime);
        }

        if (weatherLocked) {
            client.getNetworkHandler().sendCommand("weather " + lockedWeather);
        }
    }

    public static void setTimeLock(boolean locked, String time) {
        timeLocked = locked;
        lockedTime = time;
    }

    public static void setWeatherLock(boolean locked, String weather) {
        weatherLocked = locked;
        lockedWeather = weather;
    }
}
