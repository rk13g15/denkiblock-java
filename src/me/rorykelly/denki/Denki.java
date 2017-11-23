package me.rorykelly.denki;

import java.awt.*;

public abstract class Denki {

	protected enum JoinedState {
		NORTHWEST(0), NORTH(1), NORTHEAST(2), WEST(3), CENTER(4), EAST(5), SOUTHWEST(6), SOUTH(7), SOUTHEAST(
				8), NORTHSOUTH(9), WESTEAST(10), TOP(11), RIGHT(12), BOTTOM(13), LEFT(14), SINGLE(null);

		Integer value;

		private JoinedState(Integer value) {
			this.value = value;
		}
	}

	protected JoinedState state;
	protected boolean movable;
	protected boolean joinable;
	protected Color base;
	protected Color highlight;
	protected Color shadow;
	protected Point point;
	protected Clump clump;

	protected Denki(JoinedState state, boolean movable, boolean joinable, Color base, Color highlight, Color shadow,
			Point point, Clump clump) {
		this.state = state;
		this.movable = movable;
		this.joinable = joinable;
		this.base = base;
		this.highlight = highlight;
		this.shadow = shadow;
		this.point = point;
		this.clump = clump;
	}

	protected abstract void move(Direction direction);

	protected abstract boolean checkMove(Direction direction);

	protected abstract void join(Clump clump);

	protected abstract Clump checkJoin();

	protected JoinedState getStateWithinClump() {
		return state;
	};

	protected boolean isMovable() {
		return movable;
	}

	protected boolean isJoinable() {
		return joinable;
	}

	protected Color getTransparentColor() {
		return ((getPoint().x + getPoint().y) % 2) == 0 ? getClump().getParent().getBackground().getDark()
				: getClump().getParent().getBackground().getLight();
	}

	protected Color getBaseColor() {
		return base;
	}

	protected Color getHighlightColor() {
		return highlight;

	}

	protected Color getShadowColor() {
		return shadow;
	}

	protected Point getPoint() {
		return point;
	}

	protected Clump getClump() {
		return clump;
	}

	protected void setClump(Clump clump) {
		this.clump = clump;
	}

	protected void calculatePositionInClump() {
		if (getClump().denkisInClump() <= 1) {
			this.state = JoinedState.CENTER;
		} else {
			boolean north = getClump().getDenkiAtPoint(new Point(getPoint().x, getPoint().y - 1)) != null;
			boolean east = getClump().getDenkiAtPoint(new Point(getPoint().x + 1, getPoint().y)) != null;
			boolean south = getClump().getDenkiAtPoint(new Point(getPoint().x, getPoint().y + 1)) != null;
			boolean west = getClump().getDenkiAtPoint(new Point(getPoint().x - 1, getPoint().y)) != null;

			if (north) {
				if (east) {
					if (south) {
						if (west) {
							this.state = JoinedState.CENTER;
						} else {
							this.state = JoinedState.WEST;
						}
					} else if (west) {
						this.state = JoinedState.SOUTH;
					} else {
						this.state = JoinedState.SOUTHWEST;
					}
				} else if (south) {
					if (west) {
						this.state = JoinedState.EAST;
					} else {
						this.state = JoinedState.WESTEAST;
					}
				} else if (west) {
					this.state = JoinedState.SOUTHEAST;
				} else {
					this.state = JoinedState.BOTTOM;
				}
			} else if (east) {
				if (south) {
					if (west) {
						this.state = JoinedState.NORTH;
					} else {
						this.state = JoinedState.NORTHWEST;
					}
				} else if (west) {
					this.state = JoinedState.NORTHSOUTH;
				} else {
					this.state = JoinedState.LEFT;
				}
			} else if (south) {
				if (west) {
					this.state = JoinedState.NORTHEAST;
				} else {
					this.state = JoinedState.TOP;
				}
			} else if (west) {
				this.state = JoinedState.RIGHT;
			} else {
				this.state = JoinedState.SINGLE;
			}

		}

	};

	protected abstract void setMovable(boolean movable);

	protected abstract void setJoinable(boolean joinable);

	protected abstract void setBase(Color base);

	protected abstract void setHighlight(Color highlight);

	protected abstract void setShadow(Color shadow);

	protected void draw(Graphics g) {
		Color col = null;
		int blockSize = 8 * Program.SCALE_FACTOR;
		if (getStateWithinClump() == JoinedState.SOUTH || getStateWithinClump() == JoinedState.SOUTHEAST
				|| getStateWithinClump() == JoinedState.EAST) {
			int o = blockSize - 1;
			for (int r = 0; r < blockSize; r++) {
				for (int c = 0; c < blockSize; c++) {
					switch (getStateWithinClump()) {
					case SOUTH:
						col = DenkiGraphics.NS[(int) Math.floor((o - r) / Program.SCALE_FACTOR)][(int) Math
								.floor((o - c) / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.NS[(int) Math.floor((o - r) / Program.SCALE_FACTOR)][(int) Math
												.floor((o - c) / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.NS[(int) Math
																.floor((o - r) / Program.SCALE_FACTOR)][(int) Math
																		.floor((o - c) / Program.SCALE_FACTOR)] == 2
																				? getShadowColor()
																				: getHighlightColor()));
						break;
					case SOUTHEAST:
						col = DenkiGraphics.NWSE[(int) Math.floor((o - r) / Program.SCALE_FACTOR)][(int) Math
								.floor((o - c) / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.NWSE[(int) Math
												.floor((o - r) / Program.SCALE_FACTOR)][(int) Math
														.floor((o - c) / Program.SCALE_FACTOR)] == 1
																? getBaseColor()
																: (DenkiGraphics.NWSE[(int) Math.floor(
																		(o - r) / Program.SCALE_FACTOR)][(int) Math
																				.floor((o - c)
																						/ Program.SCALE_FACTOR)] == 2
																								? getShadowColor()
																								: getHighlightColor()));
						break;
					case EAST:
						col = DenkiGraphics.WE[(int) Math.floor((o - r) / Program.SCALE_FACTOR)][(int) Math
								.floor((o - c) / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.WE[(int) Math.floor((o - r) / Program.SCALE_FACTOR)][(int) Math
												.floor((o - c) / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.WE[(int) Math
																.floor((o - r) / Program.SCALE_FACTOR)][(int) Math
																		.floor((o - c) / Program.SCALE_FACTOR)] == 2
																				? getShadowColor()
																				: getHighlightColor()));
						break;
					default:
						break;
					}
					g.setColor(col);
					g.fillRect(c + (getPoint().x * blockSize), r + (getPoint().y * blockSize), 1, 1);
				}
			}
		} else {
			for (int r = 0; r < blockSize; r++) {
				for (int c = 0; c < blockSize; c++) {
					switch (getStateWithinClump()) {
					case CENTER:
						col = DenkiGraphics.CEN[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.CEN[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.CEN[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case WEST:
						col = DenkiGraphics.WE[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.WE[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.WE[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case NORTH:
						col = DenkiGraphics.NS[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.NS[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.NS[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case NORTHWEST:
						col = DenkiGraphics.NWSE[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.NWSE[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.NWSE[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case NORTHSOUTH:
						col = DenkiGraphics.HOR[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.HOR[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.HOR[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case WESTEAST:
						col = DenkiGraphics.VER[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.VER[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.VER[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case NORTHEAST:
						col = DenkiGraphics.NE[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.NE[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.NE[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case SOUTHWEST:
						col = DenkiGraphics.SW[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.SW[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.SW[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case LEFT:
						col = DenkiGraphics.LEFT[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.LEFT[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.LEFT[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case RIGHT:
						col = DenkiGraphics.RIGHT[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.RIGHT[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.RIGHT[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case TOP:
						col = DenkiGraphics.TOP[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.TOP[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.TOP[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case BOTTOM:
						col = DenkiGraphics.BOT[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.BOT[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.BOT[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;
					case SINGLE:
						col = DenkiGraphics.SING[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
								.floor(c / Program.SCALE_FACTOR)] == 0
										? getTransparentColor()
										: (DenkiGraphics.SING[(int) Math.floor(r / Program.SCALE_FACTOR)][(int) Math
												.floor(c / Program.SCALE_FACTOR)] == 1
														? getBaseColor()
														: (DenkiGraphics.SING[(int) Math
																.floor(r / Program.SCALE_FACTOR)][(int) Math
																		.floor(c / Program.SCALE_FACTOR)] == 2
																				? getHighlightColor()
																				: getShadowColor()));
						break;

					default:
						break;

					}
					g.setColor(col);
					g.fillRect(c + (getPoint().x * blockSize), r + (getPoint().y * blockSize), 1, 1);
					// g.setRGB(c + (getPoint().x * blockSize), r +
					// (getPoint().y * blockSize), col.getRGB());
				}
			}
		}

	}

}
