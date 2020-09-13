package thebetweenlands.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.AxisAlignedBB;
import thebetweenlands.client.handler.DebugHandlerClient;
import thebetweenlands.common.entity.mobs.EntityClimberBase;

public abstract class RenderClimberBase<T extends EntityClimberBase> extends RenderLiving<T> {
	public RenderClimberBase(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		float rox = (float) (entity.prevRenderOffsetX + (entity.renderOffsetX - entity.prevRenderOffsetX) * partialTicks);
		float roy = (float) (entity.prevRenderOffsetY + (entity.renderOffsetY - entity.prevRenderOffsetY) * partialTicks);
		float roz = (float) (entity.prevRenderOffsetZ + (entity.renderOffsetZ - entity.prevRenderOffsetZ) * partialTicks);

		EntityClimberBase.Orientation orientation = entity.getOrientation(partialTicks);

		x += rox - orientation.normal.x * 0.55f;
		y += roy - orientation.normal.y * 0.55f;
		z += roz - orientation.normal.z * 0.55f;

		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		if(this.getRenderManager().isDebugBoundingBox()) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);

			GlStateManager.disableTexture2D();
			GlStateManager.color(1, 1, 1, 1);

			DebugHandlerClient.drawBoundingBox(new AxisAlignedBB(0, 0, 0, 0, 0, 0).grow(0.2f));

			GlStateManager.color(1, 0, 0, 1);

			DebugHandlerClient.drawBoundingBox(new AxisAlignedBB(0, 0, 0, 0, 0, 0).grow(0.1f).offset(entity.orientationNormal));

			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableTexture2D();

			GlStateManager.popMatrix();
		}
	}

	@Override
	protected void applyRotations(T entity, float ageInTicks, float rotationYaw, float partialTicks) {
		EntityClimberBase.Orientation orientation = entity.getOrientation(partialTicks);

		GlStateManager.rotate(orientation.yaw, 0, 1, 0);
		GlStateManager.rotate(orientation.pitch, 1, 0, 0);

		GlStateManager.rotate((float)Math.signum(0.1f - orientation.upComponent) * orientation.yaw, 0, 1, 0);

		this.applyLocalRotations(entity, ageInTicks, rotationYaw, partialTicks);
	}

	protected void applyLocalRotations(T entity, float ageInTicks, float rotationYaw, float partialTicks) {
		super.applyRotations(entity, ageInTicks, rotationYaw, partialTicks);
	}
}
