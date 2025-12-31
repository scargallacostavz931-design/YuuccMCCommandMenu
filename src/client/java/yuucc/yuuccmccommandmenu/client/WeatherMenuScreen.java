package yuucc.yuuccmccommandmenu.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class WeatherMenuScreen extends Screen {
    private final Screen parent;
    private boolean weatherLocked = false;
    private String lockedWeather = "clear";

    public WeatherMenuScreen(Screen parent) {
        super(Text.translatable("screen.yuuccmccommandmenu.weather_menu"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int startY = this.height / 2 - 60;

        // Clear button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.weather.clear"),
                button -> {
                    executeCommand("weather clear");
                    if (weatherLocked) {
                        YuuccmccommandmenuClient.setWeatherLock(true, "clear");
                        lockedWeather = "clear";
                    }
                }
            )
            .dimensions(centerX - 100, startY, 90, 20)
            .build());

        // Rain button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.weather.rain"),
                button -> {
                    executeCommand("weather rain");
                    if (weatherLocked) {
                        YuuccmccommandmenuClient.setWeatherLock(true, "rain");
                        lockedWeather = "rain";
                    }
                }
            )
            .dimensions(centerX + 10, startY, 90, 20)
            .build());

        // Thunder button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.weather.thunder"),
                button -> {
                    executeCommand("weather thunder");
                    if (weatherLocked) {
                        YuuccmccommandmenuClient.setWeatherLock(true, "thunder");
                        lockedWeather = "thunder";
                    }
                }
            )
            .dimensions(centerX - 100, startY + 30, 90, 20)
            .build());

        // Snow button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.weather.snow"),
                button -> {
                    executeCommand("weather snow");
                    if (weatherLocked) {
                        YuuccmccommandmenuClient.setWeatherLock(true, "snow");
                        lockedWeather = "snow";
                    }
                }
            )
            .dimensions(centerX + 10, startY + 30, 90, 20)
            .build());

        // Lock button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable(weatherLocked ? "button.yuuccmccommandmenu.locked" : "button.yuuccmccommandmenu.unlocked"),
                button -> toggleWeatherLock()
            )
            .dimensions(centerX - 50, startY + 60, 100, 20)
            .build());

        // Back button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("gui.back"),
                button -> this.client.setScreen(parent)
            )
            .dimensions(centerX - 100, startY + 90, 200, 20)
            .build());
    }

    private void executeCommand(String command) {
        if (this.client.getNetworkHandler() != null) {
            this.client.getNetworkHandler().sendCommand(command);
        }
    }

    private void toggleWeatherLock() {
        if (hasShiftDown()) {
            weatherLocked = !weatherLocked;
            YuuccmccommandmenuClient.setWeatherLock(weatherLocked, lockedWeather);
            this.clearAndInit();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
