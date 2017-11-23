package me.rorykelly.denki;

import java.awt.*;
import java.util.ArrayList;

public final class Gumblock extends Denki {

	protected Gumblock(JoinedState state, boolean movable, boolean joinable, Color base, Color highlight, Color shadow,
			Point point, Clump clump) {
		super(state, movable, joinable, base, highlight, shadow, point, clump);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void move(Direction direction) {
		switch (direction) {
		case DOWN:
			getPoint().setLocation(new Point(getPoint().x, getPoint().y + 1));
			break;
		case LEFT:
			getPoint().setLocation(new Point(getPoint().x - 1, getPoint().y));
			break;
		case RIGHT:
			getPoint().setLocation(new Point(getPoint().x + 1, getPoint().y));
			break;
		case UP:
			getPoint().setLocation(new Point(getPoint().x, getPoint().y - 1));
			break;

		}

	}

	@Override
	protected boolean checkMove(Direction direction) {
		Denki denki = null;
		switch (direction) {
		case DOWN: {
			if (getPoint().y == 15)
				return false;
			Point down = new Point(getPoint().x, getPoint().y + 1);
			try {
				denki = getClump().getParent().getClumpAtPoint(down).getDenkiAtPoint(down);
			} catch (NullPointerException npe) {
				return true;
			}
			break;
		}
		case LEFT: {
			if (getPoint().x == 0)
				return false;
			Point left = new Point(getPoint().x - 1, getPoint().y);
			try {
				denki = getClump().getParent().getClumpAtPoint(left).getDenkiAtPoint(left);
			} catch (NullPointerException npe) {
				return true;
			}
			break;
		}
		case RIGHT: {
			if (getPoint().x == 15)
				return false;
			Point right = new Point(getPoint().x + 1, getPoint().y);
			try {
				denki = getClump().getParent().getClumpAtPoint(right).getDenkiAtPoint(right);
			} catch (NullPointerException npe) {
				return true;
			}
			break;
		}
		case UP: {
			if (getPoint().y == 0)
				return false;
			Point up = new Point(getPoint().x, getPoint().y - 1);
			try {
				denki = getClump().getParent().getClumpAtPoint(up).getDenkiAtPoint(up);
			} catch (NullPointerException npe) {
				return true;
			}
			break;
		}

		}

		return denki != null && denki.isMovable()
				&& (denki.getClump() == getClump() ? true
						: denki.getClump().denkisInClump() > 1 ? denki.getClump().checkMove(direction)
								: denki.checkMove(direction));

	}

	@Override
	protected void join(Clump clump) {
		ArrayList<Denki> toRemove = new ArrayList<Denki>();
		Clump removeFrom = getClump();
		for (Denki denki : getClump().getAllDenkis()) {
			toRemove.add(denki);
			denki.setClump(clump);
			clump.addDenki(denki);
		}
		for (Denki denki : toRemove) {
			removeFrom.removeDenki(denki);

		}
		clump.recalulateStates();

	}

	protected void separate(char group) {
		join(new Clump(getClump().getParent(), group));

	}

	@Override
	protected Clump checkJoin() {
		Denki above = getClump().getParent().getDenkiAtPoint(new Point(getPoint().x, getPoint().y + 1));
		Denki left = getClump().getParent().getDenkiAtPoint(new Point(getPoint().x - 1, getPoint().y));
		Denki right = getClump().getParent().getDenkiAtPoint(new Point(getPoint().x + 1, getPoint().y));
		Denki below = getClump().getParent().getDenkiAtPoint(new Point(getPoint().x, getPoint().y - 1));

		if (above != null && above.isJoinable() && getClump() != above.getClump()
				&& getClump().getGroup() == above.getClump().getGroup())
			return above.getClump();
		if (left != null && left.isJoinable() && getClump() != left.getClump()
				&& getClump().getGroup() == left.getClump().getGroup())
			return left.getClump();
		if (right != null && right.isJoinable() && getClump() != right.getClump()
				&& getClump().getGroup() == right.getClump().getGroup())
			return right.getClump();
		if (below != null && below.isJoinable() && getClump() != below.getClump()
				&& getClump().getGroup() == below.getClump().getGroup())
			return below.getClump();
		return null;

	}

	@Override
	protected void setMovable(boolean movable) {
		this.movable = movable;

	}

	@Override
	protected void setJoinable(boolean joinable) {
		this.joinable = joinable;

	}

	@Override
	protected void setBase(Color base) {
		this.base = base;

	}

	@Override
	protected void setHighlight(Color highlight) {
		this.highlight = highlight;

	}

	@Override
	protected void setShadow(Color shadow) {
		this.shadow = shadow;

	}

}
