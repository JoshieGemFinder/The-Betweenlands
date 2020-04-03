package thebetweenlands.client.gui.inventory;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.entity.draeton.EntityDraeton;
import thebetweenlands.common.inventory.container.ContainerDraetonBurner;
import thebetweenlands.common.network.serverbound.MessagePurgeDraetonBurner;

@SideOnly(Side.CLIENT)
public class GuiDraetonBurner extends GuiContainer {
	private static final ResourceLocation DRAETON_BURNER = new ResourceLocation("thebetweenlands:textures/gui/draeton_burner.png");

	private final EntityDraeton draeton;

	public GuiDraetonBurner(InventoryPlayer playerInventory, IInventory inventory, EntityDraeton draeton) {
		super(new ContainerDraetonBurner(playerInventory, inventory, draeton));
		this.draeton = draeton;

		xSize = 176;
		ySize = 168;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		
		if(mouseX >= this.guiLeft + 82 && mouseY >= this.guiTop + 32 && mouseX < this.guiLeft + 94 && mouseY < this.guiTop + 65) {
			this.drawHoveringText(MathHelper.ceil(this.draeton.getBurnerFuel() / (float)this.draeton.getMaxBurnerFuel() * 100) + "%", mouseX, mouseY);
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.addButton(new GuiButton(0, this.guiLeft + xSize / 2 + 25, this.guiTop + 37, 40, 20, "Purge"));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		this.fontRenderer.drawString(I18n.format(new TextComponentTranslation("container.bl.draeton_burner").getFormattedText()), xSize / 2 - fontRenderer.getStringWidth(I18n.format(new TextComponentTranslation("container.bl.draeton_burner").getFormattedText())) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format(new TextComponentTranslation("container.inventory").getFormattedText()), xSize - 170, ySize - 93, 4210752);

		GlStateManager.color(1, 1, 1, 1);

		this.mc.getTextureManager().bindTexture(DRAETON_BURNER);

		if(this.draeton.isBurnerRunning()) {
			this.drawTexturedModalRect(81, 18, 176, 0, 14, 14);
		}

		int barHeight = MathHelper.ceil(this.draeton.getBurnerFuel() / (float)this.draeton.getMaxBurnerFuel() * 31);
		this.drawTexturedModalRect(82, 33 + 31 - barHeight, 197, 32 - barHeight, 12, barHeight);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int x, int y) {
		this.drawDefaultBackground();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(DRAETON_BURNER);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if(button.id == 0) {
			TheBetweenlands.networkWrapper.sendToServer(new MessagePurgeDraetonBurner(this.draeton));
		}
	}
}