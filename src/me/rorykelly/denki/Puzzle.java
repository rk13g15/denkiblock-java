package me.rorykelly.denki;

import java.awt.*;
import java.util.ArrayList;

public final class Puzzle {

	private Background bg = new Background(new Color(240, 184, 184), new Color(216, 152, 168));
	private ArrayList<Clump> clumps = new ArrayList<Clump>();
	// private int noOfMoves;
	// private Puzzle previous = null;

	protected Clump getClumpAtPoint(Point point) {
		for (Clump clump : clumps) {
			Denki denki = clump.getDenkiAtPoint(point);
			if (denki != null)
				return clump;
		}
		return null;
	}

	protected Denki getDenkiAtPoint(Point point) {
		for (Clump clump : clumps) {
			Denki denki = clump.getDenkiAtPoint(point);
			if (denki != null)
				return denki;
		}
		return null;
	}

	void addClump(Clump clump) {
		clumps.add(clump);
	}

	void removeClump(Clump clump) {
		clumps.remove(clump);
	}

	protected void move(Direction direction) {
		for (Clump clump : clumps) {
			clump.move(direction);

		}
		for (Clump clump : clumps) {
			for (Denki denki : clump.getAllDenkis()) {
				Clump join = denki.checkJoin();
				if (join != null) {
					denki.join(join);
					break;
				}
			}
		}
		// noOfMoves++;
	}

	protected Background getBackground() {
		return bg;
	}

	public void draw(Graphics g) {
		bg.draw(g);
		for (Clump clump : clumps) {
			clump.draw(g);
		}
	}

}
