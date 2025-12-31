package yuucc.yuuccmccommandmenu.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class MainMenuScreen extends Screen {
    public MainMenuScreen() {
        super(Text.translatable("screen.yuuccmccommandmenu.main_menu"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // 时间按钮
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.time"),
                button -> this.client.setScreen(new TimeMenuScreen(this))
            )
            .dimensions(centerX - 100, centerY - 40, 200, 20)
            .build());

        // 天气按钮
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.weather"),
                button -> this.client.setScreen(new WeatherMenuScreen(this))
            )
            .dimensions(centerX - 100, centerY - 10, 200, 20)
            .build());

        // 游戏规则按钮
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("button.yuuccmccommandmenu.game_rules"),
                button -> this.client.setScreen(new GameRulesMenuScreen(this))
            )
            .dimensions(centerX - 100, centerY + 20, 200, 20)
            .build());

        // 关闭按钮
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("gui.cancel"),
                button -> this.close()
            )
            .dimensions(centerX - 100, centerY + 50, 200, 20)
            .build());
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
