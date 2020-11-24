package mchorse.chameleon.geckolib;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.file.AnimationFile;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ChameleonModel
{
	public GeoModel model;
	public AnimationFile animation;
	public long lastUpdate;

	private List<String> boneNames;

	public ChameleonModel(GeoModel model, AnimationFile file, long lastUpdate)
	{
		this.model = model;
		this.animation = file;
		this.lastUpdate = lastUpdate;

		/* This is VERY IMPORTANT, if initial bone snapshots won't be saved,
		 * there will be rotation inconsistencies! */
		this.saveInitialBoneSnapshots(model.topLevelBones);
	}

	private void saveInitialBoneSnapshots(List<GeoBone> bones)
	{
		for (GeoBone bone : bones)
		{
			bone.saveInitialSnapshot();

			this.saveInitialBoneSnapshots(bone.childBones);
		}
	}

	public List<String> getBoneNames()
	{
		if (this.boneNames != null)
		{
			return this.boneNames;
		}

		return this.boneNames = this.getBoneNames(new ArrayList<String>(), this.model.topLevelBones);
	}

	private List<String> getBoneNames(List<String> boneNames, List<GeoBone> bones)
	{
		for (GeoBone bone : bones)
		{
			boneNames.add(bone.name);

			this.getBoneNames(boneNames, bone.childBones);
		}

		return boneNames;
	}
}
