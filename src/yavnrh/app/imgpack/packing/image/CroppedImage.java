package yavnrh.app.imgpack.packing.image;

import yavnrh.app.imgpack.packing.ImageCropper;
import yavnrh.app.imgpack.packing.Rectangle;

public class CroppedImage extends Image {

	private Rectangle cropRect;
	private int originalWidth;
	private int originalHeight;
	
	private CroppedImage(String name) {
		super(name);
	}
	
	/**
	 * This method may return the same object if cropping is not necessary.
	 */
	public static Image from(Image image) {
		ImageCropper cropper = new ImageCropper(image.image);
		Rectangle cropRect = cropper.getCropRect();
		
		final boolean isSameSize =
				(image.getWidth() == cropRect.width) &&
				(image.getHeight() == cropRect.height);
		
		if (isSameSize) {
			return image;
			
		} else {
			CroppedImage croppedImage = new CroppedImage(image.name);
			croppedImage.image = image.image.getSubimage(cropRect.x, cropRect.y, cropRect.width, cropRect.height);
			croppedImage.cropRect = cropRect;
			croppedImage.originalWidth = image.image.getWidth();
			croppedImage.originalHeight = image.image.getHeight();
			
			return croppedImage;
		}		
	}
	
	@Override
	public boolean isCropped() {
		return true;
	}
		
	@Override
	public Rectangle getCropRect() {
		return cropRect;
	}

	@Override
	public int getFullWidth() {
		return originalWidth;
	}
	
	@Override
	public int getFullHeight() {
		return originalHeight;
	}
	
}
