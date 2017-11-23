package me.rorykelly.denki;

import java.awt.*;
import java.util.ArrayList;

public class Clump {

	private ArrayList<Denki> denkis;
	private Puzzle parent;
	private char group;
	// private boolean complete = false;

	protected Clump(Puzzle parent, char group) {
		this(parent, group, new Denki[0]);

	}

	protected Clump(Puzzle parent, char group, Denki... denkis) {
		this.parent = parent;
		this.group = group;
		this.denkis = new ArrayList<Denki>();
		for (Denki denki : denkis) {
			this.denkis.add(denki);
		}
		this.parent.addClump(this);

	}

	protected void move(Direction direction) {
		for (Denki denki : denkis) {
			if (!denki.isMovable()) {
				return;
			}
			if (!denki.checkMove(direction)) {
				return;
			}
		}

		for (Denki denki : denkis) {
			denki.move(direction);

		}
	}

	protected Denki getDenkiAtPoint(Point point) {
		for (Denki denki : denkis) {
			if (denki.getPoint().equals(point))
				return denki;
		}
		return null;
	}

	protected boolean checkMove(Direction direction) {
		for (Denki denki : denkis) {
			if (!denki.checkMove(direction))
				return false;
		}
		return true;
	}

	protected char getGroup() {
		return group;
	}

	protected void setGroup(char group) {
		this.group = group;
	}

	protected Puzzle getParent() {
		return parent;
	}

	protected int denkisInClump() {
		return denkis.size();
	}

	protected void addDenki(Denki denki) {
		denkis.add(denki);
	}

	protected void removeDenki(Denki denki) {
		denkis.remove(denki);
	}

	protected void draw(Graphics g) {
		for (Denki denki : denkis) {
			denki.draw(g);
		}
	}

	protected ArrayList<Denki> getAllDenkis() {
		return denkis;
	}

	protected void recalulateStates() {
		for (Denki denki : denkis) {
			denki.calculatePositionInClump();
		}

	}
}
