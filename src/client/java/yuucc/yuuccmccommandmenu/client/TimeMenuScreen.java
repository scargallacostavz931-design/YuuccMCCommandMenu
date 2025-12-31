package yuucc.yuuccmccommandmenu.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TimeMenuScreen extends Screen {
    private final Screen parent;
    private boolean timeLocked = false;
    private String lockedTime = "day";

    public TimeMenuScreen(Screen parent) {
        super(Text.translatable("screen.yuuccmccommandmenu.time_menu"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int startY = this.height / 2 - 60;

        // Day button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.time.day"),
                button -> {
                    executeCommand("time set day");
                    if (timeLocked) {
                        YuuccmccommandmenuClient.setTimeLock(true, "day");
                        lockedTime = "day";
                    }
                }
            )
            .dimensions(centerX - 100, startY, 90, 20)
            .build());

        // Noon button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.time.noon"),
                button -> {
                    executeCommand("time set noon");
                    if (timeLocked) {
                        YuuccmccommandmenuClient.setTimeLock(true, "noon");
                        lockedTime = "noon";
                    }
                }
            )
            .dimensions(centerX + 10, startY, 90, 20)
            .build());

        // Night button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.time.night"),
                button -> {
                    executeCommand("time set night");
                    if (timeLocked) {
                        YuuccmccommandmenuClient.setTimeLock(true, "night");
                        lockedTime = "night";
                    }
                }
            )
            .dimensions(centerX - 100, startY + 30, 90, 20)
            .build());

        // Midnight button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.time.midnight"),
                button -> {
                    executeCommand("time set midnight");
                    if (timeLocked) {
                        YuuccmccommandmenuClient.setTimeLock(true, "midnight");
                        lockedTime = "midnight";
                    }
                }
            )
            .dimensions(centerX + 10, startY + 30, 90, 20)
            .build());

        // Lock button
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable(timeLocked ? "button.yuuccmccommandmenu.locked" : "button.yuuccmccommandmenu.unlocked"),
                button -> toggleTimeLock()
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

    private void toggleTimeLock() {
        if (hasShiftDown()) {
            timeLocked = !timeLocked;
            YuuccmccommandmenuClient.setTimeLock(timeLocked, lockedTime);
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
