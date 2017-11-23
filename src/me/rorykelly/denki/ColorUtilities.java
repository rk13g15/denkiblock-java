package me.rorykelly.denki;

import java.awt.Color;

public final class ColorUtilities {

	protected Color getColor(Denki denki, int color) {
		switch (color) {
		case 1:
			return denki.getBaseColor();
		case 2:
			return denki.getHighlightColor();
		case 3:
			return denki.getShadowColor();
		default:
			return denki.getTransparentColor();
		}
	}

	protected Color calculateHighlight(Color base) {
		return base.brighter().brighter();
	}

	protected Color calculateShadow(Color base) {
		return base.darker().brighter();
	}
}
