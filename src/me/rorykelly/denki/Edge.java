package me.rorykelly.denki;

import java.awt.*;

public final class Edge extends Denki {

	protected Edge(JoinedState state, Color base, Color highlight, Color shadow, Point point, Clump clump) {
		super(state, false, false, base, highlight, shadow, point, clump);

	}

	@Override
	protected void move(Direction direction) {
		return;

	}

	@Override
	protected boolean checkMove(Direction direction) {
		return false;
	}

	@Override
	protected void join(Clump clump) {
		return;

	}

	@Override
	protected Clump checkJoin() {
		return null;

	}

	@Override
	protected void setMovable(boolean movable) {
		return;

	}

	@Override
	protected void setJoinable(boolean joinable) {
		return;

	}

	@Override
	protected void setBase(Color base) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setHighlight(Color highlight) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setShadow(Color shadow) {
		// TODO Auto-generated method stub

	}

}
