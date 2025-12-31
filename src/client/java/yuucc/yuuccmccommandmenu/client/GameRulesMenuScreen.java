package yuucc.yuuccmccommandmenu.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameRulesMenuScreen extends Screen {
    private final Screen parent;
    private final Map<String, Boolean> gameRuleLocks = new LinkedHashMap<>();
    private final Map<String, Object> gameRuleStates = new LinkedHashMap<>();
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 6; // 每页显示6个规则对

    // 布尔类型游戏规则列表 - 现在只存储规则名，显示文本从翻译获取
    private static final String[][] BOOLEAN_GAME_RULES = {
        // 玩家相关
        {"keepInventory", "naturalRegeneration"},
        {"doImmediateRespawn", "fallDamage"},
        {"fireDamage", "drowningDamage"},
        {"freezeDamage", "mobGriefing"},
        // 生物相关
        {"doMobSpawning", "doMobLoot"},
        {"doInsomnia", "doWardenSpawning"},
        {"universalAnger", "doDaylightCycle"},
        // 世界更新
        {"doWeatherCycle", "doFireTick"},
        {"waterSourceConversion", "lavaSourceConversion"},
        // 掉落物相关
        {"doTileDrops", "doEntityDrops"},
        {"tntExplosionDropDecay", "doInsomnia"}
    };

    // 数值类型游戏规则列表 [rule, defaultValue]
    private static final Object[][] NUMERIC_GAME_RULES = {
        {"randomTickSpeed", 3},
        {"maxEntityCramming", 24},
        {"spawnRadius", 10},
        {"playersSleepingPercentage", 100}
    };

    public GameRulesMenuScreen(Screen parent) {
        super(Text.translatable("screen.yuuccmccommandmenu.game_rules_menu"));
        this.parent = parent;

        // 初始化布尔规则状态
        for (String[] pair : BOOLEAN_GAME_RULES) {
            gameRuleLocks.put(pair[0], false);
            gameRuleLocks.put(pair[1], false);
            gameRuleStates.put(pair[0], false);
            gameRuleStates.put(pair[1], false);
        }

        // 初始化数值规则状态
        for (Object[] rule : NUMERIC_GAME_RULES) {
            String ruleName = (String) rule[0];
            Object defaultValue = rule[1];
            gameRuleLocks.put(ruleName, false);
            gameRuleStates.put(ruleName, defaultValue);
        }
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int startY = 50;
        int buttonWidth = 140;
        int buttonHeight = 20;
        int spacing = 25;

        // 计算总页数
        int totalBooleanPairs = BOOLEAN_GAME_RULES.length;
        int totalNumericRules = NUMERIC_GAME_RULES.length;
        int totalItems = totalBooleanPairs + (totalNumericRules + 1) / 2; // 数值规则算作一半（因为每行放一个）
        int totalPages = (totalItems + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;

        // 确保当前页不超出范围
        if (currentPage >= totalPages) {
            currentPage = totalPages - 1;
        }
        if (currentPage < 0) {
            currentPage = 0;
        }

        int itemIndex = 0;
        int displayIndex = 0;

        // 显示当前页的布尔规则
        while (itemIndex < totalBooleanPairs && displayIndex < ITEMS_PER_PAGE) {
            if (itemIndex / ITEMS_PER_PAGE == currentPage) {
                String rule1 = BOOLEAN_GAME_RULES[itemIndex][0];
                String rule2 = BOOLEAN_GAME_RULES[itemIndex][1];

                int rowY = startY + (displayIndex % ITEMS_PER_PAGE) * spacing;

                // 左侧规则按钮
                this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("gamerule." + rule1).append(": ")
                            .append(Text.translatable((Boolean)gameRuleStates.get(rule1) ? "gamerule.state.on" : "gamerule.state.off")),
                        button -> toggleBooleanGameRule(rule1)
                    )
                    .dimensions(centerX - buttonWidth - 10, rowY, buttonWidth, buttonHeight)
                    .build());

                // 左侧锁定按钮
                this.addDrawableChild(ButtonWidget.builder(
                        Text.of(gameRuleLocks.get(rule1) ? "🔒" : "🔓"),
                        button -> toggleGameRuleLock(rule1)
                    )
                    .dimensions(centerX - buttonWidth - 35, rowY, 20, buttonHeight)
                    .build());

                // 右侧规则按钮
                this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("gamerule." + rule2).append(": ")
                            .append(Text.translatable((Boolean)gameRuleStates.get(rule2) ? "gamerule.state.on" : "gamerule.state.off")),
                        button -> toggleBooleanGameRule(rule2)
                    )
                    .dimensions(centerX + 10, rowY, buttonWidth, buttonHeight)
                    .build());

                // 右侧锁定按钮
                this.addDrawableChild(ButtonWidget.builder(
                        Text.of(gameRuleLocks.get(rule2) ? "🔒" : "🔓"),
                        button -> toggleGameRuleLock(rule2)
                    )
                    .dimensions(centerX + buttonWidth + 15, rowY, 20, buttonHeight)
                    .build());

                displayIndex++;
            }
            itemIndex++;
        }

        // 显示当前页的数值规则
        for (int i = 0; i < totalNumericRules; i++) {
            if ((itemIndex + i) / ITEMS_PER_PAGE == currentPage) {
                String rule = (String) NUMERIC_GAME_RULES[i][0];
                int rowY = startY + ((itemIndex + i) % ITEMS_PER_PAGE) * spacing;

                // 数值规则按钮
                this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("gamerule." + rule).append(": ").append(String.valueOf(gameRuleStates.get(rule))),
                        button -> toggleNumericGameRule(rule)
                    )
                    .dimensions(centerX - buttonWidth - 10, rowY, buttonWidth, buttonHeight)
                    .build());

                // 锁定按钮
                this.addDrawableChild(ButtonWidget.builder(
                        Text.of(gameRuleLocks.get(rule) ? "🔒" : "🔓"),
                        button -> toggleGameRuleLock(rule)
                    )
                    .dimensions(centerX - buttonWidth - 35, rowY, 20, buttonHeight)
                    .build());

                displayIndex++;
                if (displayIndex >= ITEMS_PER_PAGE) break;
            }
        }

        // 计算按钮区域，确保不与规则按钮重叠
        int rulesEndY = startY + ITEMS_PER_PAGE * spacing;
        int buttonAreaStartY = Math.max(rulesEndY + 20, this.height - 120);

        // 上一页按钮 - 再往左移更多
        if (currentPage > 0) {
            this.addDrawableChild(ButtonWidget.builder(
                    Text.translatable("button.yuuccmccommandmenu.previous_page"),
                    button -> {
                        currentPage--;
                        this.clearAndInit();
                    }
                )
                .dimensions(centerX - 180, buttonAreaStartY, 100, 20)
                .build());
        }

        // 下一页按钮 - 再往右移更多
        if (currentPage < totalPages - 1) {
            this.addDrawableChild(ButtonWidget.builder(
                    Text.translatable("button.yuuccmccommandmenu.next_page"),
                    button -> {
                        currentPage++;
                        this.clearAndInit();
                    }
                )
                .dimensions(centerX + 80, buttonAreaStartY, 100, 20)
                .build());
        }

        // 返回按钮 - 进一步减小宽度并调整位置
        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable("gui.back"),
                button -> this.client.setScreen(parent)
            )
            .dimensions(centerX - 60, this.height - 30, 120, 20)
            .build());
    }

    private void toggleBooleanGameRule(String rule) {
        // 切换布尔规则状态并设置
        boolean currentState = (Boolean) gameRuleStates.get(rule);
        boolean newState = !currentState;
        gameRuleStates.put(rule, newState);
        executeCommand("gamerule " + rule + " " + newState);
        this.clearAndInit(); // 刷新UI显示
    }

    private void toggleNumericGameRule(String rule) {
        // 为数值规则循环一些预设值
        Object currentValue = gameRuleStates.get(rule);
        Object newValue;

        if (rule.equals("randomTickSpeed")) {
            // 循环: 0 -> 3 -> 20 -> 0
            int val = (Integer) currentValue;
            newValue = val == 0 ? 3 : val == 3 ? 20 : 0;
        } else if (rule.equals("maxEntityCramming")) {
            // 循环: 0 -> 24 -> 100 -> 0
            int val = (Integer) currentValue;
            newValue = val == 0 ? 24 : val == 24 ? 100 : 0;
        } else if (rule.equals("spawnRadius")) {
            // 循环: 0 -> 10 -> 50 -> 0
            int val = (Integer) currentValue;
            newValue = val == 0 ? 10 : val == 10 ? 50 : 0;
        } else if (rule.equals("playersSleepingPercentage")) {
            // 循环: 0 -> 50 -> 100 -> 0
            int val = (Integer) currentValue;
            newValue = val == 0 ? 50 : val == 50 ? 100 : 0;
        } else {
            newValue = currentValue; // 默认保持不变
        }

        gameRuleStates.put(rule, newValue);
        executeCommand("gamerule " + rule + " " + newValue);
        this.clearAndInit(); // 刷新UI显示
    }

    private void toggleGameRuleLock(String rule) {
        if (hasShiftDown()) {
            gameRuleLocks.put(rule, !gameRuleLocks.get(rule));
            this.clearAndInit();
        }
    }

    private void executeCommand(String command) {
        if (this.client.getNetworkHandler() != null) {
            this.client.getNetworkHandler().sendCommand(command);
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
