package me.rorykelly.denki;

import java.awt.*;
import java.awt.Graphics;

public final class Background {

	private static final Color LIGHT_BG_COLOR = Color.LIGHT_GRAY;
	private static final Color DARK_BG_COLOR = Color.DARK_GRAY;

	private Color light;
	private Color dark;

	protected Background() {
		light = LIGHT_BG_COLOR;
		dark = DARK_BG_COLOR;
	}

	protected Background(Color light, Color dark) {
		this.light = light;
		this.dark = dark;
	}

	protected Color getLight() {
		return light;
	}

	protected void setLight(Color light) {
		this.light = light;
	}

	protected Color getDark() {
		return dark;
	}

	protected void setDark(Color dark) {
		this.dark = dark;
	}

	public void draw(Graphics g) {
		int size = 8 * Program.SCALE_FACTOR;
		for (int r = 0; r < 16; r++) {
			for (int c = 0; c < 16; c++) {
				g.setColor((r + c) % 2 == 0 ? dark : light);
				g.fillRect(c * size, r * size, size, size);

			}
		}

	}

}
